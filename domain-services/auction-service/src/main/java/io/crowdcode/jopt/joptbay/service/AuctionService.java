package io.crowdcode.jopt.joptbay.service;


import io.crowdcode.jopt.joptbay.dto.AuctionSummary;
import io.crowdcode.jopt.joptbay.dto.CreateAuction;
import io.crowdcode.jopt.joptbay.dto.ProductInfo;
import io.crowdcode.jopt.joptbay.exceptions.BidTooLowException;
import io.crowdcode.jopt.joptbay.exceptions.InvalidAuctionStateException;
import io.crowdcode.jopt.joptbay.exceptions.ProductNotFoundException;
import io.crowdcode.jopt.joptbay.model.Bid;

import java.util.List;

public interface AuctionService {

    /**
     * Starts a given auction and assigns an ID to it
     *
     * @param createAuction
     * @return id
     */
    String startAuction(CreateAuction createAuction);


    /**
     * @param searchTerm
     * @return found products
     */
    List<ProductInfo> findProductsByTitleOrDescription(String searchTerm);


    /**
     * @param productUuid
     * @return highest bid (or null)
     */
    Bid getHighestBidForProduct(String productUuid) throws ProductNotFoundException;


    /**
     * @param productUuid
     * @return auction summary
     */
    AuctionSummary getAuctionSummary(String productUuid) throws ProductNotFoundException;


    /**
     * @param productUiid
     * @param bid
     */
    void placeBidOnProduct(String productUiid, Bid bid) throws ProductNotFoundException, BidTooLowException, InvalidAuctionStateException;


    /**
     * Check expired auctions for highest bidder and sends mail
     * as well as removes the auction
     */
    void handleExpiredAuctions();


    /**
     * @return count of auctions
     */
    int activeAuctionCount();

}
