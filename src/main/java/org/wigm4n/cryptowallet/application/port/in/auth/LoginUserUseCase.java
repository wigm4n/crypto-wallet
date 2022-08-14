package org.wigm4n.cryptowallet.application.port.in.auth;

import org.wigm4n.cryptowallet.domain.auth.Jwt;
import org.wigm4n.cryptowallet.domain.auth.UserCredentials;

public interface LoginUserUseCase {

    Jwt login(UserCredentials userCredentials);
}
