package io.crowdcode.cloudbay.catalog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static io.crowdcode.cloudbay.common.AnsiColor.blue;

@Slf4j
@Service
public class TimeService {

//    private final RestTemplate restTemplate;

    private final WebClient webClient;

    @Value("${time.service.url}")
    private String timeServiceUrl;

    public TimeService(WebClient webClient) {
        this.webClient = webClient;
    }

    //    @HystrixCommand(fallbackMethod = "defaultNow")
    public LocalDateTime retrieveNow() {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(timeServiceUrl)
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(TimeResponse.class)
                    .block().getNow();
        } catch (Exception e) {
            return defaultNow();
        }
    }

    public LocalDateTime defaultNow() {
        log.info(blue("using default now implementation"));
        return LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    }


}
