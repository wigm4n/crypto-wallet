package org.wigm4n.cryptowallet.application.port.in.auth;

import org.wigm4n.cryptowallet.domain.auth.Jwt;

public interface RefreshJwtUseCase {

    Jwt refreshJwt(String refreshToken);
}
