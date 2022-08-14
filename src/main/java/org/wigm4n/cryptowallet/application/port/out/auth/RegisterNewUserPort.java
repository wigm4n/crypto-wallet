package org.wigm4n.cryptowallet.application.port.out.auth;

import org.wigm4n.cryptowallet.domain.auth.UserCredentials;

public interface RegisterNewUserPort {

    String registerUser(UserCredentials userCredentials);
}
