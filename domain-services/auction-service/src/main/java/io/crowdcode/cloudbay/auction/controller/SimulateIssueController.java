package io.crowdcode.cloudbay.auction.controller;

import io.crowdcode.cloudbay.auction.effects.AllocateMemoryForSeconds;
import io.crowdcode.cloudbay.auction.effects.MemoryGuzzler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SimulateIssueController {

    private final MemoryGuzzler memoryGuzzler;
    private final AllocateMemoryForSeconds allocateMemoryForSeconds;

    @PutMapping("/guzzler/start")
    public ResponseEntity<String> startMemoryGuzzler() {
        memoryGuzzler.start();
        return ResponseEntity.ok("{\"message\":\"Starting memory wasting\"}");
    }

    @PutMapping("/guzzler/stop")
    public ResponseEntity<String> stopMemoryGuzzler() {
        memoryGuzzler.stop();
        return ResponseEntity.ok("{\"message\":\"Stopping memory wasting\"}");
    }

    @PutMapping("/allocate/mem/{size}/{seconds}")
    public ResponseEntity<String> allocateMemory(@PathVariable int size, @PathVariable int seconds) {
        allocateMemoryForSeconds.allocate(size, seconds);
        return ResponseEntity.ok("{\"message\":\"Alloced the memory for some seconds\"}");
    }


}
