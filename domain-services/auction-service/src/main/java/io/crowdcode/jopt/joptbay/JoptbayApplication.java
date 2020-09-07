package io.crowdcode.jopt.joptbay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JoptbayApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoptbayApplication.class, args);
	}

}
