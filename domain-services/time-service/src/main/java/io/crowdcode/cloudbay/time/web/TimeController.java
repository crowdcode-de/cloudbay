package io.crowdcode.cloudbay.time.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;

import static io.crowdcode.cloudbay.common.AnsiColor.blue;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Slf4j
@RestController
public class TimeController {

    @GetMapping("/now")
    public TimeResponse now(Authentication authentication) {
        log.info(blue(Arrays.toString(authentication.getAuthorities().toArray())));

        TimeResponse timeResponse = new TimeResponse().setNow(LocalDateTime.now());
        log.info(blue("GOT REQUEST AND SAY " + timeResponse.getNow()));
        return timeResponse;
    }
}
