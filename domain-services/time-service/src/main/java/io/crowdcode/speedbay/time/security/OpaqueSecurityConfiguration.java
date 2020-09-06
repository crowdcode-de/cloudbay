package io.crowdcode.speedbay.time.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Slf4j
@Profile("opaque && !introspection")
@Configuration
public class OpaqueSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private OpaqueSecurityConfiguration opaqueSecurityConfiguration;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .opaqueToken()
        ;
    }
}
