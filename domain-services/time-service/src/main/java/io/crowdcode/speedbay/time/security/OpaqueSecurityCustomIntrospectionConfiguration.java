package io.crowdcode.speedbay.time.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Slf4j
@Profile("introspection")
@Configuration
public class OpaqueSecurityCustomIntrospectionConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.introspection-uri}")
    private String introspectionUrl;

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}")
    private String clientSecret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final OpaqueTokenIntrospector introspector
                = new NimbusOpaqueTokenIntrospector(introspectionUrl, clientId, clientSecret);
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .opaqueToken()
                .introspector((token -> {
                    log.info("Got OpaqueToken: {}",token);
                    return introspector.introspect(token);
                }))
        ;
    }
}
