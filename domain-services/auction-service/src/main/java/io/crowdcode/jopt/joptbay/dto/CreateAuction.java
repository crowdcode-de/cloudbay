package io.crowdcode.jopt.joptbay.dto;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateAuction {

    private String productTitle;
    private String productDescription;
    private int secondsToRun;

}
