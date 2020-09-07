package io.crowdcode.jopt.joptbay.service;

import io.crowdcode.jopt.joptbay.dto.AuctionSummary;
import io.crowdcode.jopt.joptbay.dto.CreateAuction;
import io.crowdcode.jopt.joptbay.dto.ProductInfo;
import io.crowdcode.jopt.joptbay.exceptions.BidTooLowException;
import io.crowdcode.jopt.joptbay.exceptions.InvalidAuctionStateException;
import io.crowdcode.jopt.joptbay.exceptions.ProductNotFoundException;
import io.crowdcode.jopt.joptbay.model.Auction;
import io.crowdcode.jopt.joptbay.model.Bid;
import io.crowdcode.jopt.joptbay.model.Product;
import io.crowdcode.jopt.joptbay.repository.AuctionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@Profile("expired")
public class PersistOnlyExpiredAuctionService implements AuctionService {

    protected Map<String, Auction> activeAuctions = new HashMap<>();

    private AuctionRepository auctionRepository;
    private long counter;

    @Value("${shouldThrowExceptions:false}")
    private boolean shouldThrowExceptions = true;

    public PersistOnlyExpiredAuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @Override
    public String startAuction(CreateAuction createAuction) {
        String uuid = UUID.randomUUID().toString();
        synchronized (activeAuctions) {
            Product product = new Product()
                    .setTitle(createAuction.getProductTitle())
                    .setDescription(createAuction.getProductDescription())
                    .setProductUuid(uuid);
            Auction auction = new Auction()
                    .setBeginDateTime(LocalDateTime.now())
                    .setExpireDateTime(LocalDateTime.now().plusSeconds(createAuction.getSecondsToRun()))
                    .setProduct(product);

            activeAuctions.put(uuid, auction);
        }
        return uuid;
    }

    @Override
    public List<ProductInfo> findProductsByTitleOrDescription(String searchTerm) {
        List<ProductInfo> matchingProducts = new ArrayList<>();
        if (searchTerm != null && searchTerm.length() > 0) {
            synchronized (activeAuctions) {
                executeSearch(searchTerm, matchingProducts);
            }
        }
        return matchingProducts;
    }

    @Override
    public Bid getHighestBidForProduct(String productUuid) throws ProductNotFoundException {
        return retrieveAuction(productUuid).getHighestBid();
    }

    @Override
    public AuctionSummary getAuctionSummary(String productUuid) throws ProductNotFoundException {
        return mapToSummary(retrieveAuction(productUuid));
    }

    @Override
    public void placeBidOnProduct(String productUuid, Bid bid) throws ProductNotFoundException, BidTooLowException, InvalidAuctionStateException {
        Auction auction = retrieveAuction(productUuid);
        auction.addBid(bid);
        auctionRepository.save(auction);
    }

    @Override
    @Scheduled(fixedRate = 30_000)
    public void handleExpiredAuctions() {
        saveExpiredToDatabase();
        removeExpiredFromActive();
    }

    private void saveExpiredToDatabase() {
        synchronized (activeAuctions) {
            List<Auction> expiredAuctions = activeAuctions
                    .values()
                    .stream()
                    .filter(Auction::isExpired)
                    .map(auctionRepository::save)
                    .collect(Collectors.toList());
        }
    }

        private void removeExpiredFromActive() {
        synchronized (activeAuctions) {
            System.out.println("Auctions "+activeAuctions.size());
            activeAuctions.values().removeIf(Auction::isExpired);
            System.out.println("Auctions "+activeAuctions.size());
//            List<Auction> auctionsForChecking = new ArrayList<>(activeAuctions.values());
//            for (Iterator<Auction> iter = auctionsForChecking.iterator(); iter.hasNext(); ) {
//                Auction auction = iter.next();
//                if (auction.isExpired()) {
//                    iter.remove();
//                }
//            }
//            System.out.println("Auctions: " + auctionsForChecking.size());
        }

    }

    @Override
    public int activeAuctionCount() {
        return activeAuctions.size();
    }

    protected void executeSearch(String searchTerm, List<ProductInfo> matchingProducts) {
        matchingProducts.addAll(activeAuctions.values()
                .stream()
                .filter(Auction::isRunning)
                .map(Auction::getProduct)
                .filter((p) -> matchesTitleAndDescriptionOfProduct(searchTerm, p))
                .map(this::mapToProductInfo)
                .collect(Collectors.toList()));
    }

    private boolean matchesTitleAndDescriptionOfProduct(String searchTerm, Product product) {
        return matchesSearch(product.getTitle(), searchTerm) || matchesSearch(product.getDescription(), searchTerm);
    }

    protected ProductInfo mapToProductInfo(Product product) {
        return new ProductInfo()
                .setProductTitle(product.getTitle())
                .setProductUuid(product.getProductUuid());
    }

    protected boolean matchesSearch(String value, String searchTerm) {
        if (value != null && value.trim().toUpperCase().length() > 0) {
            if (value.trim().toUpperCase().contains(searchTerm.toUpperCase().trim())) {
                return true;
            }
        }
        return false;
    }

    private Auction retrieveAuction(String productUuid) throws ProductNotFoundException {
        Auction auction = activeAuctions.get(productUuid);
        if (auction == null) {
            auctionRepository.findByProductProductUuid(productUuid);
        }
        if (auction == null) {
            throw new ProductNotFoundException();
        }
        return auction;
    }

    private AuctionSummary mapToSummary(Auction auction) {
        valideAuction(auction);
        return new AuctionSummary()
                .setExpiresAt(auction.getExpireDateTime())
                .setHighestBid(auction.getHighestBid())
                .setProductTitle(auction.getProduct().getTitle())
                .setProductUuid(auction.getProduct().getProductUuid());
    }

    private void valideAuction(Auction auction) {
        if (shouldThrowExceptions) {
            try {
                counter++;
                if (counter % 100 == 0) {
                    throw new NumberFormatException("ups");
                }
            } catch (NumberFormatException e) {
                // please ignore
            }
        }
    }
}

