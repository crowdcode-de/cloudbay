package io.crowdcode.jopt.joptbay.repository;


import io.crowdcode.jopt.joptbay.fixture.AuctionFixture;
import io.crowdcode.jopt.joptbay.model.Auction;
import org.assertj.core.api.Assumptions;
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