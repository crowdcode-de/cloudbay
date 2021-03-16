package io.crowdcode.cloudbay.auction.controller;

import io.crowdcode.cloudbay.auction.dto.AuctionSummary;
import io.crowdcode.cloudbay.auction.dto.CreateAuction;
import io.crowdcode.cloudbay.auction.dto.ProductInfo;
import io.crowdcode.cloudbay.auction.dto.ProductUuid;
import io.crowdcode.cloudbay.auction.exceptions.BidTooLowException;
import io.crowdcode.cloudbay.auction.exceptions.InvalidAuctionStateException;
import io.crowdcode.cloudbay.auction.exceptions.ProductNotFoundException;
import io.crowdcode.cloudbay.auction.model.Bid;
import io.crowdcode.cloudbay.auction.service.AuctionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionService auctionEngine;

    public AuctionController(AuctionService auctionEngine) {
        this.auctionEngine = auctionEngine;
    }

    @PostMapping()
    public ResponseEntity<ProductUuid> startAuction(@RequestBody CreateAuction createAuction) {
        String uuid = auctionEngine.startAuction(createAuction);
        return ResponseEntity.created(URI.create("http://localhost:8080/auctions/" + uuid)).body(new ProductUuid(uuid));
    }

    @PutMapping("/{productUuid}")
    public ResponseEntity<Void> placeBid(@PathVariable String productUuid, @RequestBody Bid bid) throws ProductNotFoundException, InvalidAuctionStateException, BidTooLowException {
        auctionEngine.placeBidOnProduct(productUuid, bid);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{productUuid}")
    public AuctionSummary findProduct(@PathVariable String productUuid) throws ProductNotFoundException {
        return auctionEngine.getAuctionSummary(productUuid);
    }

    @GetMapping()
    public List<ProductInfo> findByTitle(@RequestParam(value = "search", required = false) String searchTerm) {
        return auctionEngine.findProductsByTitleOrDescription(searchTerm);
    }

}
