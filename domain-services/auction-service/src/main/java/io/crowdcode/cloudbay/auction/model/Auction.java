package io.crowdcode.cloudbay.auction.model;

import io.crowdcode.cloudbay.auction.exceptions.AuctionExpiredException;
import io.crowdcode.cloudbay.auction.exceptions.AuctionNotStartedException;
import io.crowdcode.cloudbay.auction.exceptions.BidTooLowException;
import io.crowdcode.cloudbay.auction.exceptions.InvalidAuctionStateException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Accessors(chain = true)
public class Auction extends AbstractEntity<Long> {

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    private LocalDateTime beginDateTime;

    private LocalDateTime expireDateTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
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
                || beginDateTime.isEqual(LocalDateTime.now()))
                && expireDateTime.isAfter(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Auction auction = (Auction) o;
        return getId() != null && Objects.equals(getId(), auction.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}


