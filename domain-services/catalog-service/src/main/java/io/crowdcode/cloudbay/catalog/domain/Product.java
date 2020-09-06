package io.crowdcode.cloudbay.catalog.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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