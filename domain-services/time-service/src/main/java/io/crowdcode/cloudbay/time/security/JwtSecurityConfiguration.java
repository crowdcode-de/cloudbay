package io.crowdcode.cloudbay.time.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;
import java.util.List;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Slf4j
@Profile("!opaque && !introspection")
@Configuration
public class JwtSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
//                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
        ;
    }

    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(customGrantedAuthorityConverter());
        return converter;
    }

    public Converter<Jwt, Collection<GrantedAuthority>> customGrantedAuthorityConverter() {
        return (jwt) -> {
            log.info("got jwt: " + jwt);
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        };
    }

}
