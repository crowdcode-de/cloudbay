package io.crowdcode.jopt.joptbay.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class AuctionNotStartedException extends InvalidAuctionStateException {

    public AuctionNotStartedException() {
        super("Auction hasn't started yet!");
    }

}
