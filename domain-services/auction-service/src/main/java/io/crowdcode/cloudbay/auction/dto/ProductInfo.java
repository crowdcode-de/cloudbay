package io.crowdcode.cloudbay.auction.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductInfo {

    private String productUuid;
    private String productTitle;

}
