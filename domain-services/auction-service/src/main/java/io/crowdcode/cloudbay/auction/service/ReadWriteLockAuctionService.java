package io.crowdcode.cloudbay.auction.service;

import io.crowdcode.cloudbay.auction.dto.AuctionSummary;
import io.crowdcode.cloudbay.auction.dto.CreateAuction;
import io.crowdcode.cloudbay.auction.dto.ProductInfo;
import io.crowdcode.cloudbay.auction.exceptions.BidTooLowException;
import io.crowdcode.cloudbay.auction.exceptions.InvalidAuctionStateException;
import io.crowdcode.cloudbay.auction.exceptions.ProductNotFoundException;
import io.crowdcode.cloudbay.auction.model.Auction;
import io.crowdcode.cloudbay.auction.model.Bid;
import io.crowdcode.cloudbay.auction.model.Product;
import io.crowdcode.cloudbay.auction.repository.AuctionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@Profile("readwrite")
public class ReadWriteLockAuctionService implements AuctionService {

    protected Map<String, Auction> activeAuctions = new ConcurrentHashMap<>();

    private final AuctionRepository auctionRepository;

    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

    public ReadWriteLockAuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @Override
    public String startAuction(CreateAuction createAuction) {
        String uuid = UUID.randomUUID().toString();
        lock.writeLock().lock();
        try {
            Product product = new Product()
                    .setTitle(createAuction.getProductTitle())
                    .setDescription(createAuction.getProductDescription())
                    .setProductUuid(uuid);
            Auction auction = new Auction()
                    .setBeginDateTime(LocalDateTime.now())
                    .setExpireDateTime(LocalDateTime.now().plusSeconds(createAuction.getSecondsToRun()))
                    .setProduct(product);

            activeAuctions.put(uuid, auction);
        } finally {
            lock.writeLock().unlock();
        }
        return uuid;
    }

    @Override
    public List<ProductInfo> findProductsByTitleOrDescription(String searchTerm) {
        List<ProductInfo> matchingProducts = new ArrayList<>();
        if (searchTerm != null && searchTerm.length() > 0) {
             lock.readLock().lock();
             try {
                 executeSearch(searchTerm, matchingProducts);
             } finally {
                 lock.readLock().unlock();
             }
        }
        return matchingProducts;
    }

    @Override
    public Bid getHighestBidForProduct(String productUuid) throws ProductNotFoundException {
        return auctionRepository
                .findByProductProductUuid(productUuid)
                .orElseThrow(ProductNotFoundException::new)
                .getHighestBid();
    }

    @Override
    public AuctionSummary getAuctionSummary(String productUuid) throws ProductNotFoundException {
        return mapToSummary(retrieveAuction(productUuid));
    }

    @Override
    public void placeBidOnProduct(String productUuid, Bid bid) throws ProductNotFoundException, BidTooLowException, InvalidAuctionStateException {
        Auction auction = retrieveAuction(productUuid);
        auction.addBid(bid);
    }

    @Override
    @Scheduled(fixedRate = 1000)
    public void handleExpiredAuctions() {
        lock.writeLock().lock();
        try {
            List<Auction> expiredAuctions = activeAuctions
                    .values()
                    .parallelStream()
                    .filter(Auction::isExpired)
                    .map(auctionRepository::save)
                    .collect(Collectors.toList());

            log.info("Number of Auctions expired and saved: {}", expiredAuctions.size());

            activeAuctions.values().removeAll(expiredAuctions);

            log.info("Auctions: " + activeAuctionCount());
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public int activeAuctionCount() {
        return activeAuctions.size();
    }

    protected void executeSearch(String searchTerm, List<ProductInfo> matchingProducts) {
        matchingProducts.addAll(activeAuctions.values()
                .parallelStream()
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
        if (value != null && value.trim().length() > 0) {
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
        return new AuctionSummary()
                .setExpiresAt(auction.getExpireDateTime())
                .setHighestBid(auction.getHighestBid())
                .setProductTitle(auction.getProduct().getTitle())
                .setProductUuid(auction.getProduct().getProductUuid());
    }
}
