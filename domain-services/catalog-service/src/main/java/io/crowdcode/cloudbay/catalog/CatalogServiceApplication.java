package io.crowdcode.cloudbay.catalog;

import io.crowdcode.cloudbay.catalog.domain.Product;
import io.crowdcode.cloudbay.catalog.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.UUID;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@SpringBootApplication
public class CatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }

    @Bean
    @Profile("dummydata")
    public CommandLineRunner createProducts(ProductRepository productRepository) {
        return (args) -> {
            productRepository.save(new Product().setSku(UUID.randomUUID().toString()).setTitle("Krefelder").addTags("Bier", "Cola"));
            productRepository.save(new Product().setSku(UUID.randomUUID().toString()).setTitle("Kakao").addTags("Kakao", "CHILD"));
            productRepository.save(new Product().setSku(UUID.randomUUID().toString()).setTitle("Milch").addTags("Milch", "CHILD"));
            productRepository.save(new Product().setSku(UUID.randomUUID().toString()).setTitle("Kaffee").addTags("Kaffee", "admin"));
        };
    }

}
