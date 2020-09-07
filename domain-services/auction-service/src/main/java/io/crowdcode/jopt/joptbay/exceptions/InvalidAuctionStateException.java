package io.crowdcode.jopt.joptbay.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAuctionStateException extends Exception{

    public InvalidAuctionStateException() {
    }

    public InvalidAuctionStateException(String message) {
        super(message);
    }

    public InvalidAuctionStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAuctionStateException(Throwable cause) {
        super(cause);
    }

    public InvalidAuctionStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
