package org.wigm4n.cryptowallet.application.port.out.auth;

import org.wigm4n.cryptowallet.domain.auth.Jwt;

public interface RefreshJwtPort {

    Jwt refreshJwt(String refreshToken);
}
