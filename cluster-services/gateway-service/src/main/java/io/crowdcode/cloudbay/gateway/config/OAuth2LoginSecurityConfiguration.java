package io.crowdcode.cloudbay.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Configuration
public class OAuth2LoginSecurityConfiguration {


    public SecurityFilterChain filterSecurityChain(HttpSecurity http) throws Exception {
        return http
            .cors().configurationSource((request) -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
                corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL);
                corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
                return corsConfiguration;
            })
                .and()
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers("/","/index.html","/favicon.png").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .and()
                .logout()
                .and()
                .build();
    }
}
