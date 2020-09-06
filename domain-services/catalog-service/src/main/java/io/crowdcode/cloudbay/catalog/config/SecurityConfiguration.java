package io.crowdcode.cloudbay.catalog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
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
                .permitAll();
    }
}
