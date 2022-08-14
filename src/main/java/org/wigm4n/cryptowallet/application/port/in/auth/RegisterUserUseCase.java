package org.wigm4n.cryptowallet.application.port.in.auth;

import org.wigm4n.cryptowallet.domain.auth.UserCredentials;

public interface RegisterUserUseCase {

    void register(UserCredentials userCredentials);
}
