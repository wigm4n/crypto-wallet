package org.wigm4n.cryptowallet.adapter.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.wigm4n.cryptowallet.application.port.out.auth.ExchangeJwtPort;
import org.wigm4n.cryptowallet.application.port.out.auth.RefreshJwtPort;
import org.wigm4n.cryptowallet.application.port.out.auth.RegisterNewUserPort;
import org.wigm4n.cryptowallet.config.properties.ResourceServerProperties;
import org.wigm4n.cryptowallet.config.properties.ResourceServerProperties.ResourceServerConnectionParameters;
import org.wigm4n.cryptowallet.domain.auth.Jwt;
import org.wigm4n.cryptowallet.domain.auth.UserCredentials;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceServerAdapter implements RegisterNewUserPort, ExchangeJwtPort, RefreshJwtPort {

    private final RestTemplate keycloakRestTemplate;
    private final ResourceServerProperties resourceServerProperties;

    private static final String PASSWORD_GRANT_TYPE = "password";
    private static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
    private static final String RESPONSE_USER_ID_HEADER_NAME = "Location";

    @Override
    public String registerUser(UserCredentials userCredentials) {
        var userConnectionParams = resourceServerProperties.getUserConnectionParameters();
        var adminConnectionParams = resourceServerProperties.getAdminConnectionParameters();
        var adminToken = exchangeJwt(adminConnectionParams, getAdminCredentials(adminConnectionParams), null);
        return registerUser(userCredentials, userConnectionParams.getRealm(), adminToken.getAccessToken());
    }

    @Override
    public Jwt exchangeJwt(UserCredentials userCredentials) {
        var userConnectionParams = resourceServerProperties.getUserConnectionParameters();
        return exchangeJwt(userConnectionParams, userCredentials, null);
    }

    @Override
    public Jwt refreshJwt(String refreshToken) {
        var userConnectionParams = resourceServerProperties.getUserConnectionParameters();
        return exchangeJwt(userConnectionParams, null, refreshToken);
    }

    private Jwt exchangeJwt(
            ResourceServerConnectionParameters connectionParams,
            UserCredentials userCredentials,
            String refreshToken
    ) {
        var endpoint = String.format(
                resourceServerProperties.getEndpointTemplates().getTokenIssueUrlTemplate(),
                connectionParams.getRealm()
        );
        var grantType = userCredentials == null ? REFRESH_TOKEN_GRANT_TYPE : PASSWORD_GRANT_TYPE;
        var requestEntity = RequestEntity
                .post(endpoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(
                        buildRequestFormData(
                                new ExchangeJwtCommand(
                                        connectionParams.getClientId(),
                                        grantType,
                                        userCredentials,
                                        refreshToken
                                )
                        )
                );
        log.debug("Отправляем запрос в Keycloak c параметрами {}", requestEntity);
        return keycloakRestTemplate.exchange(requestEntity, Jwt.class).getBody();
    }

    private String registerUser(UserCredentials userCredentials, String realm, String accessToken) {
        var endpoint = String.format(
                resourceServerProperties.getEndpointTemplates().getUsersManagementUrlTemplate(),
                realm
        );
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        var requestEntity = RequestEntity
                .post(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .body(
                        new ResourceServerAdapter.CreateUserDto(
                                userCredentials.getUsername(),
                                new ResourceServerAdapter.Credentials(userCredentials.getPassword())
                        )
                );
        log.debug("Отправляем запрос в Keycloak c параметрами {}", requestEntity);
        var response = keycloakRestTemplate.exchange(requestEntity, Void.class);
        return retrieveGeneratedUserId(response.getHeaders());
    }

    private String buildRequestFormData(ExchangeJwtCommand command) {
        StringBuilder builder = new StringBuilder();

        builder.append("grant_type=")
                .append(encode(command.getGrantType()))
                .append("&client_id=")
                .append(encode(command.getClientId()));

        if (command.getUserCredentials() != null &&
                StringUtils.hasText(command.getUserCredentials().getUsername()) &&
                StringUtils.hasText(command.getUserCredentials().getPassword())) {
            builder.append("&username=")
                    .append(encode(command.getUserCredentials().getUsername()))
                    .append("&password=")
                    .append(encode(command.getUserCredentials().getPassword()));
        } else {
            if (StringUtils.hasText(command.getRefreshToken())) {
                builder.append("&refresh_token=")
                        .append(encode(command.getRefreshToken()));
            }
        }

        return builder.toString();
    }

    private static String encode(String param) {
        try {
            return param != null ? URLEncoder.encode(param, StandardCharsets.UTF_8.name()) : null;
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unable to encode request parameter", e);
        }
    }

    private UserCredentials getAdminCredentials(ResourceServerConnectionParameters adminConnectionParams) {
        return new UserCredentials(adminConnectionParams.getUsername(), adminConnectionParams.getPassword());
    }

    private String retrieveGeneratedUserId(HttpHeaders headers) {
        var splittedElements = Optional.ofNullable(headers.get(RESPONSE_USER_ID_HEADER_NAME))
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "В ответ на создание пользователя в Keycloak не вернулся userId в хедерах")
                )
                .stream()
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "В ответ на создание пользователя в Keycloak не вернулся userId в хедерах")
                )
                .split("/");
        return splittedElements[splittedElements.length - 1];
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExchangeJwtCommand {
        private String clientId;
        private String grantType;
        private UserCredentials userCredentials;
        private String refreshToken;
    }

    @Data
    @NoArgsConstructor
    public static class CreateUserDto {
        private boolean enabled;
        private String username;
        private List<Credentials> credentials;

        public CreateUserDto(String username, Credentials credentials) {
            this.enabled = true;
            this.username = username;
            this.credentials = List.of(credentials);
        }
    }

    @Data
    @NoArgsConstructor
    public static class Credentials {
        private String type;
        private String value;
        private boolean temporary;

        public Credentials(String value) {
            this.type = PASSWORD_GRANT_TYPE;
            this.value = value;
            this.temporary = false;
        }
    }
}
