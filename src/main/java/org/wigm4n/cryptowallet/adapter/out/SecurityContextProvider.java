package org.wigm4n.cryptowallet.adapter.out;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.wigm4n.cryptowallet.application.port.out.auth.GetPrincipalPort;
import org.wigm4n.cryptowallet.domain.auth.Principal;

@Component
public class SecurityContextProvider implements GetPrincipalPort {

    private static final String USERNAME_CLAIM_NAME = "preferred_username";

    @Override
    public Principal getPrincipal() {
        var jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return new Principal(
                jwt.getName(),
                jwt.getTokenAttributes().get(USERNAME_CLAIM_NAME).toString()
        );
    }
}
