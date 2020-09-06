package io.crowdcode.cloudbay.gateway.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Slf4j
@RestController
@RequestMapping(path = "/api")
public class GatewayController {

    @Value("${gateway.version}")
    private String version;

    @GetMapping("/version")
    public ResponseEntity<String> version() {
        return ResponseEntity.ok("{\"version\": \""+version+"\"}");
    }

    @GetMapping("/userinfo")
    public ResponseEntity<String> username(Authentication authentication) {
        log.info("{}",authentication);
        return ResponseEntity.ok("{\"username\":\""+authentication.getName()+"\"}");
    }

}
