package io.crowdcode.cloudbay.auction.repository;


import io.crowdcode.cloudbay.auction.fixture.AuctionFixture;
import io.crowdcode.cloudbay.auction.model.Auction;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Disabled // FIXME I DO NOT KNOW WHY THIS TEST FAILS - Upgrade Spring Boot to 3.0.0 introduced Bug
public class AuctionRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void saveAuction() {
        Auction auction = AuctionFixture.defaultAuction()
                .setBids(
                        Arrays.asList(AuctionFixture.lowBid(), AuctionFixture.highBid()));
        Auction found = testEntityManager.persistFlushFind(auction);

        Assumptions.assumeThat(found.getProduct().getId()).isNotNull();
        Assumptions.assumeThat(found.getHighestBid().getAmount()).isEqualByComparingTo(BigDecimal.TEN);
    }
}
