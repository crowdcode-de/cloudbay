package io.crowdcode.cloudbay.catalog.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Entity
@Getter
@Setter
@Accessors(chain=true)
@EqualsAndHashCode
@ToString
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @NotNull
    private String sku;

    @NotNull
    private String title;

    @Lob
    private String description;

    private LocalDateTime createdAt;

    private BigDecimal retailPrice;
    private BigDecimal amount;
    private String unit;

    @ElementCollection(fetch= FetchType.EAGER)
    private List<String> tags = new ArrayList<>();

    public Product addTags(String... tags) {
        this.tags.addAll(Arrays.asList(tags));
        return this;
    }

}
