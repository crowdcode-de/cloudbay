package io.crowdcode.cloudbay.auction.controller;

import io.crowdcode.cloudbay.auction.effects.MemoryGuzzler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SimulateIssueController {

    private MemoryGuzzler memoryGuzzler;

    public SimulateIssueController(MemoryGuzzler memoryGuzzler) {
        this.memoryGuzzler = memoryGuzzler;
    }

    @PutMapping("/guzzler/start")
    public ResponseEntity<String> startSpeicherfresser() {
        memoryGuzzler.start();
        return ResponseEntity.ok("Starting memory wasting");
    }

    @PutMapping("/guzzler/stop")
    public ResponseEntity<String> stopSpeicherfresser() {
        memoryGuzzler.stop();
        return ResponseEntity.ok("Stopping memory wasting");
    }


}
