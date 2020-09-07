package io.crowdcode.jopt.joptbay.model;

import io.crowdcode.jopt.joptbay.exceptions.AuctionExpiredException;
import io.crowdcode.jopt.joptbay.exceptions.AuctionNotStartedException;
import io.crowdcode.jopt.joptbay.exceptions.BidTooLowException;
import io.crowdcode.jopt.joptbay.fixture.AuctionFixture;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class AuctionTest {

    @Test
    void testAddValidBid() throws Exception {
        Auction auction = AuctionFixture.defaultAuction()
                .addBid(AuctionFixture.lowBid())
                .addBid(AuctionFixture.highBid())
                .addBid(AuctionFixture.buildBid(11.0));
        Assumptions.assumeThat(auction.getHighestBid().getAmount()).isGreaterThan(BigDecimal.TEN);
    }

    @Test
    void testAddTooLowBid() throws Exception {
        Auction auction = AuctionFixture.defaultAuction()
                .addBid(AuctionFixture.highBid());
        Assertions.assertThrows(BidTooLowException.class, () -> auction.addBid(AuctionFixture.lowBid()));
    }

    @Test
    void testAddBidToExpiredAuction() throws Exception {
        Auction auction = AuctionFixture.defaultAuction()
                .setBeginDateTime(LocalDateTime.now().minusMinutes(3))
                .setExpireDateTime(LocalDateTime.now().minusMinutes(2));

        Assertions.assertThrows(AuctionExpiredException.class, () -> auction.addBid(AuctionFixture.highBid()));
    }


    @Test
    void testAddBidToNotStartedAuction() throws Exception {
        Auction auction = AuctionFixture.defaultAuction()
                .setBeginDateTime(LocalDateTime.now().plusMinutes(2))
                .setExpireDateTime(LocalDateTime.now().plusMinutes(3));

        Assertions.assertThrows(AuctionNotStartedException.class, () -> auction.addBid(AuctionFixture.highBid()));
    }




}