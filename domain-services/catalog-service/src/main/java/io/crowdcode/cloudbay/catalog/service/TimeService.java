package io.crowdcode.cloudbay.catalog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static io.crowdcode.cloudbay.common.AnsiColor.blue;

@Slf4j
@Service
public class TimeService {

    private final RestTemplate restTemplate;

    @Value("${time.service.url}")
    private String timeServiceUrl;

    public TimeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //    @HystrixCommand(fallbackMethod = "defaultNow")
    public LocalDateTime retrieveNow() {
        try {
            ResponseEntity<TimeResponse> responseEntity = restTemplate
                    .getForEntity(timeServiceUrl, TimeResponse.class);
            return responseEntity.getBody().getNow();
        } catch (Exception e) {
            return defaultNow();
        }
    }

    public LocalDateTime defaultNow() {
        log.info(blue("using default now implementation"));
        return LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    }


}
