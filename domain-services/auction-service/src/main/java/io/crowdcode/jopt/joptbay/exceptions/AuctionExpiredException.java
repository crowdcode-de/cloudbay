package io.crowdcode.jopt.joptbay.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class AuctionExpiredException extends InvalidAuctionStateException {

    public AuctionExpiredException() {
        super("Product already expired");
    }
}
