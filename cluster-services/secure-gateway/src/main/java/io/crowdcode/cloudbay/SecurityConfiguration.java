package io.crowdcode.cloudbay;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return
                http
                        .authorizeExchange(exchanges -> {
                            exchanges
                                    .pathMatchers("/","/index.html").permitAll()
                                    .anyExchange().authenticated();
                        })
                        .oauth2Login(withDefaults())
                        .csrf().disable()
                        .build();
    }

}
