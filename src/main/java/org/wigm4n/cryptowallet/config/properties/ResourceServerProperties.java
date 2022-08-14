package org.wigm4n.cryptowallet.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Validated
@Data
@Component
@ConfigurationProperties("resource-server.keycloak")
public class ResourceServerProperties {

    @NotNull
    private Map<String, ResourceServerConnectionParameters> clients;
    private EndpointTemplates endpointTemplates;

    private static final String USER_CONNECTION_NAME = "user";
    private static final String ADMIN_CONNECTION_NAME = "admin";

    public ResourceServerConnectionParameters getUserConnectionParameters() {
        return this.clients.get(USER_CONNECTION_NAME);
    }

    public ResourceServerConnectionParameters getAdminConnectionParameters() {
        return this.clients.get(ADMIN_CONNECTION_NAME);
    }

    @Data
    public static class EndpointTemplates {
        @NotNull
        @NotEmpty
        private String tokenIssueUrlTemplate;
        @NotNull
        @NotEmpty
        private String usersManagementUrlTemplate;
    }

    @Data
    public static class ResourceServerConnectionParameters {
        @NotNull
        @NotEmpty
        private String realm;
        @NotNull
        @NotEmpty
        private String clientId;
        private String username;
        private String password;
    }
}
