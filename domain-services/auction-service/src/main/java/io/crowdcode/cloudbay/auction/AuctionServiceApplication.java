package io.crowdcode.cloudbay.auction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Ingo Düppe (CROWDCOCDE)
 */
@EnableScheduling
@SpringBootApplication
public class AuctionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionServiceApplication.class, args);
	}

}
