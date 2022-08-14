package org.wigm4n.cryptowallet.application.port.out.auth;

import org.wigm4n.cryptowallet.domain.auth.Jwt;
import org.wigm4n.cryptowallet.domain.auth.UserCredentials;

public interface ExchangeJwtPort {

    Jwt exchangeJwt(UserCredentials userCredentials);
}
