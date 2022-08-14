package org.wigm4n.cryptowallet.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.wigm4n.cryptowallet.application.exception.InvalidUserCredentialsException;
import org.wigm4n.cryptowallet.application.exception.KeycloakExchangeException;
import org.wigm4n.cryptowallet.application.exception.UserAlreadyExistsException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate keycloakRestTemplate() {
        var restTemplate = restTemplateBuilder.build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setErrorHandler(new KeycloakErrorHandler());
        return restTemplate;
    }

    public static class KeycloakErrorHandler extends DefaultResponseErrorHandler {
        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            switch (response.getStatusCode()) {
                case CONFLICT:
                    log.debug("Попытка создать существующего пользователя заново");
                    throw new UserAlreadyExistsException("Пользователь с таким логином уже существует");
                case UNAUTHORIZED:
                    log.debug("Переданы неверные учетные данные пользователя");
                    throw new InvalidUserCredentialsException("Пользователь не авторизован");
                default:
                    log.error(
                            "Ошибка при обращении к Keycloak, код ответа: {}, тело ответа: {}",
                            response.getStatusCode(),
                            new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8)
                    );
                    throw new KeycloakExchangeException("Внутренняя ошибка при обращении к Keycloak");
            }
        }
    }
}
