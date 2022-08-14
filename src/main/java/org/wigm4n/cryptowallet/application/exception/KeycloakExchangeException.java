package org.wigm4n.cryptowallet.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class KeycloakExchangeException extends RuntimeException {

    public KeycloakExchangeException(String message) {
        super(message);
    }
}
