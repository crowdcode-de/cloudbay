package io.crowdcode.cloudbay.catalog.security.keycloak;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
public class KeycloakJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    public static final String REALM_ACCESS = "realm_access";
    public static final String ROLES = "roles";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Object realmAccess = jwt.getClaims().get(REALM_ACCESS);
        if (realmAccess instanceof Map) {
            Object roles = ((Map) realmAccess).get(ROLES);
            if (roles instanceof Collection) {
                Collection<?> roleItems = (Collection) roles;
                return roleItems.stream()
                        .map(Object::toString)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
            }
        }
        return List.of();
    }
}
