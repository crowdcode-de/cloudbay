package io.crowdcode.cloudbay.auction.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Aspect
@ComponentScan
@RequiredArgsConstructor
@Profile("with-actuator")
@Component
public class AuctionMetricAdvice {

    private final MeterRegistry meterRegistry;

    private Counter auctionCounter;

    private Counter bidCounter;

    @PostConstruct
    public void init() {
        auctionCounter = Counter.builder("auctions.auction_total_count")
                .tag("type", "total")
                .description("Total number of received auctions")
                .register(meterRegistry);

        bidCounter = Counter.builder("auctions.bid_total_count")
                .tag("type", "total")
                .description("Total number of received bids")
                .register(meterRegistry);
    }

    @Before("execution(* *..*.*.placeBidOnProduct(..))")
    public void countPlaceBidEvent() {
        bidCounter.increment();
    }

    @Before("execution(* *..*.*.startAuction(..))")
    public void countAuctions() {
        auctionCounter.increment();
    }

}
