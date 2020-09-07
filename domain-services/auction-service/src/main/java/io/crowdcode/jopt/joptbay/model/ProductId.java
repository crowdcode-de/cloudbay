package io.crowdcode.jopt.joptbay.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Embeddable
public class ProductId implements Serializable {

    private String uuid;

}
