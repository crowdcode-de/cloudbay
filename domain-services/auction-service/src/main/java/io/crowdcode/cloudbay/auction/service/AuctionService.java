package io.crowdcode.cloudbay.auction.service;


import io.crowdcode.cloudbay.auction.dto.AuctionSummary;
import io.crowdcode.cloudbay.auction.dto.CreateAuction;
import io.crowdcode.cloudbay.auction.dto.ProductInfo;
import io.crowdcode.cloudbay.auction.exceptions.BidTooLowException;
import io.crowdcode.cloudbay.auction.exceptions.InvalidAuctionStateException;
import io.crowdcode.cloudbay.auction.exceptions.ProductNotFoundException;
import io.crowdcode.cloudbay.auction.model.Bid;

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
     * @param productUuid
     * @param bid
     */
    void placeBidOnProduct(String productUuid, Bid bid) throws ProductNotFoundException, BidTooLowException, InvalidAuctionStateException;


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
