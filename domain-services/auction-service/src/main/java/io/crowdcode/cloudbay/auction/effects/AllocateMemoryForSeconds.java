package io.crowdcode.cloudbay.auction.effects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Slf4j
@Service
public class AllocateMemoryForSeconds {

    private List<Bulk> dataList = Collections.synchronizedList(new ArrayList<>());

    public void allocate(int sizeInMegabytes, int secondsToHold) {
        dataList.add(Bulk.of(sizeInMegabytes, secondsToHold));
        log.info("Created bulk with {} megabytes for {} seconds.", sizeInMegabytes, secondsToHold );
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.SECONDS)
    public void cleanUp() {
        long oldSum = dataList.stream().mapToLong(Bulk::size).sum() / 1_000_000;
        dataList.removeIf(Bulk::isExpired);
        long newSum = dataList.stream().mapToLong(Bulk::size).sum() / 1_000_000;
        log.info("Reduced memory usage from {} MB to {} MB.", oldSum, newSum);
    }

    public static class Bulk {
        private byte[] data;
        private LocalDateTime expireAt;

        static Bulk of(int sizeInMegabytes, int secondsToHold) {
            Bulk bulk = new Bulk();
            bulk.expireAt = LocalDateTime.now().plusSeconds(secondsToHold);
            bulk.data = new byte[sizeInMegabytes * 1_0000_000];
            return bulk;
        }

        public int size() {
            return data.length;
        }

        public boolean isExpired() {
            return expireAt.isBefore(LocalDateTime.now());
        }
    }

}
