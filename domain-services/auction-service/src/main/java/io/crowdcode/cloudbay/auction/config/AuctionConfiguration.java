package io.crowdcode.cloudbay.auction.config;

import io.crowdcode.cloudbay.auction.effects.DeadLock;
import io.crowdcode.cloudbay.auction.effects.MemoryGuzzler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author Ingo Düppe (CROWCODE)
 */
@Slf4j
@Configuration
public class AuctionConfiguration {

    @Bean
    public MemoryGuzzler memoryGuzzler() throws IOException {
        return new MemoryGuzzler();
    }

    @Bean
    public DeadLock deadLock() {
        return new DeadLock().buildDeadlock();
    }
}
