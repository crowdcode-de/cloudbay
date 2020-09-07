package io.crowdcode.cloudbay.greetingservice;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GreetingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreetingServiceApplication.class, args);
	}

	@Bean
	public ApplicationRunner shutdownAfterStart() {
		return (args) -> {
			if (args.containsOption("quite")) {
				System.out.println("Quite System After Startup!");
				System.exit(0);
			}
		};
	}

}
