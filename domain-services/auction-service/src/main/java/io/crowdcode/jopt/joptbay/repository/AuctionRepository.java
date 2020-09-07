package io.crowdcode.jopt.joptbay.repository;

import io.crowdcode.jopt.joptbay.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query("SELECT a FROM Auction a WHERE a.id = :id")
    Optional<Auction> find(@Param("id") Long auctionId);


    @Query("SELECT a FROM Auction a LEFT JOIN a.bids b WHERE b.email = :email")
    Set<Auction> allAuctionWithABidFrom(String email);

    Optional<Auction> findByProductProductUuid(String productUuid);
}
