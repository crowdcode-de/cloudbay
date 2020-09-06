package io.crowdcode.cloudbay.catalog.repository;

import io.crowdcode.cloudbay.catalog.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p LEFT JOIN p.tags t WHERE t in :tags")
    List<Product> findByTag(@Param("tags") String... tags);

    Product findBySku(String productSku);

}