package io.crowdcode.jopt.joptbay.model;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Slf4j
@Data
@Entity
@Accessors(chain = true)
public class Bid extends AbstractEntity<Long> {

    public static final Bid ZERO = new Bid().setAmount(BigDecimal.ZERO).setEmail("-");

    private String email;
    private BigDecimal amount;

}
