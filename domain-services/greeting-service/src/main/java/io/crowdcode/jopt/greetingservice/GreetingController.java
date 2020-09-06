package io.crowdcode.jopt.greetingservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@RequestMapping
public class GreetingController {

    @GetMapping
    public Mono<String> hello() {
        return Mono.just("I am here!");
    }
}
