package io.crowdcode.jopt.joptbay.dto;

import io.crowdcode.jopt.joptbay.model.Bid;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class AuctionSummary {

    private String productUuid;
    private String productTitle;
    private Bid highestBid;
    private LocalDateTime expiresAt;

}
