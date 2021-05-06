package io.crowdcode.cloudbay.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Configuration
public class OAuth2ResourceSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
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
                .antMatchers("/", "/index.html", "/favicon.png").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt()
        ;
    }



}
