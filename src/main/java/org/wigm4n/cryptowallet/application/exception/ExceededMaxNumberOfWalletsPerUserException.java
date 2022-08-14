package org.wigm4n.cryptowallet.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class ExceededMaxNumberOfWalletsPerUserException extends RuntimeException {

    public ExceededMaxNumberOfWalletsPerUserException(String message) {
        super(message);
    }
}
