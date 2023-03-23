package io.crowdcode.cloudbay.auction.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Embeddable
public class ProductId implements Serializable {

    private String uuid;

}
