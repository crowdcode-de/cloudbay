package io.crowdcode.jopt.joptbay.fixture;

import io.crowdcode.jopt.joptbay.model.Auction;
import io.crowdcode.jopt.joptbay.model.Bid;
import io.crowdcode.jopt.joptbay.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class AuctionFixture {


    public static final int DEFAULT_BEGIN = 2;
    public static final int DEFAULT_EXPIRE = 4;

    public static Product defaultProduct() {
        return new Product()
                .setProductUuid(UUID.randomUUID().toString())
                .setTitle("Default Product Title")
                .setDescription("Default Product Description");
    }

    public static Auction defaultAuction() {
        return new Auction()
                .setBeginDateTime(LocalDateTime.now())
                .setExpireDateTime(LocalDateTime.now().plusMinutes(DEFAULT_EXPIRE))
                .setProduct(defaultProduct());
    }

    public static Bid lowBid() {
        return new Bid()
                .setAmount(BigDecimal.ONE)
                .setEmail(randomMailAddress());

    }


    public static Bid highBid() {
        return new Bid()
                .setAmount(BigDecimal.TEN)
                .setEmail(randomMailAddress());
    }

    public static Bid buildBid(double amount) {
        return new Bid()
                .setAmount(BigDecimal.valueOf(amount))
                .setEmail(randomMailAddress());
    }

    public static String randomMailAddress() {
        return UUID.randomUUID().toString()+"@crowdcode.io";
    }
}
