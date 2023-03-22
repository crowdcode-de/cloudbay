package io.crowdcode.cloudbay.catalog.config;

import io.crowdcode.cloudbay.catalog.security.keycloak.KeycloakJwtGrantedAuthoritiesConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Configuration
public class SecurityConfiguration {

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .anonymous()
                .and()
                .cors().configurationSource(
                        (request) -> {
                            CorsConfiguration corsConfiguration = new CorsConfiguration();
                            corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
                            corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL);
                            corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
                            return corsConfiguration;
                        })
                .and()
                .csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer((configure) -> {
                    configure.jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
                }).build();
    }

    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakJwtGrantedAuthoritiesConverter());
        return jwtAuthenticationConverter;
    }
}
