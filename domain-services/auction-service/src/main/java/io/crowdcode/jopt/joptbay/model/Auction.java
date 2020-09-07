package io.crowdcode.jopt.joptbay.model;

import io.crowdcode.jopt.joptbay.exceptions.AuctionExpiredException;
import io.crowdcode.jopt.joptbay.exceptions.AuctionNotStartedException;
import io.crowdcode.jopt.joptbay.exceptions.BidTooLowException;
import io.crowdcode.jopt.joptbay.exceptions.InvalidAuctionStateException;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Data
@Entity
@Accessors(chain = true)
public class Auction extends AbstractEntity<Long> {

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    private LocalDateTime beginDateTime;

    private LocalDateTime expireDateTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids = new ArrayList<>();

    public Auction addBid(Bid bid) throws InvalidAuctionStateException, BidTooLowException {
        Objects.requireNonNull(bid);

        if (isExpired()) {
            throw new AuctionExpiredException();
        }

        if (!isRunning()) {
            throw new AuctionNotStartedException();
        }

        if (!isGreaterThanHighest(bid)) {
            throw new BidTooLowException();
        }

        bids.add(0, bid);
        return this;
    }

    private boolean isGreaterThanHighest(Bid bid) {
        return getHighestBid().getAmount().compareTo(bid.getAmount()) < 0;
    }

    public Bid getHighestBid() {
        return bids
                .stream()
                .parallel()
                .max(Comparator.comparing(Bid::getAmount))
                .orElse(Bid.ZERO);
    }

    public boolean isExpired() {
        return !expireDateTime.isAfter(LocalDateTime.now());
    }

    public boolean isRunning() {
        return (beginDateTime.isBefore(LocalDateTime.now())
                || beginDateTime.isEqual(LocalDateTime.now())
                && expireDateTime.isAfter(LocalDateTime.now()));
    }

}


