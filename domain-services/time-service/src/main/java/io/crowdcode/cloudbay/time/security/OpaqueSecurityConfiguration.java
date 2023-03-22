package io.crowdcode.cloudbay.time.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static io.crowdcode.cloudbay.common.AnsiColor.purple;


/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Slf4j
@Profile("opaque && !introspection")
@Configuration
public class OpaqueSecurityConfiguration {

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        log.info(purple("Working with opaque token"));
        return http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer((configure) -> configure.opaqueToken())
                .build();
    }
}
