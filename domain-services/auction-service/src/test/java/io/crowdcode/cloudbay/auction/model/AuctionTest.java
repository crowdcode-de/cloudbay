package io.crowdcode.cloudbay.auction.model;

import io.crowdcode.cloudbay.auction.exceptions.AuctionExpiredException;
import io.crowdcode.cloudbay.auction.exceptions.AuctionNotStartedException;
import io.crowdcode.cloudbay.auction.exceptions.BidTooLowException;
import io.crowdcode.cloudbay.auction.fixture.AuctionFixture;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        assertThatThrownBy(() -> auction.addBid(AuctionFixture.lowBid()))
                .isInstanceOf(BidTooLowException.class);
    }

    @Test
    void testAddBidToExpiredAuction() {
        Auction auction = AuctionFixture.defaultAuction()
                .setBeginDateTime(LocalDateTime.now().minusMinutes(3))
                .setExpireDateTime(LocalDateTime.now().minusMinutes(2));

        assertThatThrownBy(() -> auction.addBid(AuctionFixture.highBid()))
                .isInstanceOf(AuctionExpiredException.class);
    }


    @Test
    void testAddBidToNotStartedAuction() {
        Auction auction = AuctionFixture.defaultAuction()
                .setBeginDateTime(LocalDateTime.now().plusMinutes(2))
                .setExpireDateTime(LocalDateTime.now().plusMinutes(3));

        assertThatThrownBy(() -> auction.addBid(AuctionFixture.highBid()))
                .isInstanceOf(AuctionNotStartedException.class);
    }

    @Test
    void testExpiredAuctionIsNotRunning() {
        Auction auction = AuctionFixture.defaultAuction()
                .setBeginDateTime(LocalDateTime.now().minusMinutes(10))
                .setExpireDateTime(LocalDateTime.now().minusMinutes(5));

        assertThat(auction.isRunning()).isFalse();
    }

    @Test
    void testExpiredAuctionIsExpired() {
        Auction auction = AuctionFixture.defaultAuction()
                .setBeginDateTime(LocalDateTime.now().minusMinutes(10))
                .setExpireDateTime(LocalDateTime.now().minusMinutes(5));

        assertThat(auction.isExpired()).isTrue();
    }

    @Test
    void testAddBitToExpiredAuctionCauseException() {
        Auction auction = AuctionFixture.defaultAuction()
                .setBeginDateTime(LocalDateTime.now().minusMinutes(10))
                .setExpireDateTime(LocalDateTime.now().minusMinutes(5));

        assertThatThrownBy(() -> auction.addBid(AuctionFixture.highBid()))
                .isInstanceOf(AuctionExpiredException.class);
    }
}
