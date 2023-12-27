/**
 * JwtAuthConverter is a Spring Framework converter responsible for converting a
 * JSON Web Token (JWT) into a Spring Security AbstractAuthenticationToken,
 * specifically a JwtAuthenticationToken. It extracts authorities from the "realm_access"
 * claim in the JWT and creates a collection of GrantedAuthority objects to be used
 * for authentication.
 *
 * @author mdsharif
 * @version 1.0
 * @since 2023-01-01
 */
package com.custom.security.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Converts a JWT into a Spring Security AbstractAuthenticationToken.
 */
@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	/**
	 * Converts a Jwt into a JwtAuthenticationToken with extracted authorities.
	 *
	 * @param jwt The JSON Web Token to convert.
	 * @return JwtAuthenticationToken containing the JWT and extracted authorities.
	 */
	@Override
	public AbstractAuthenticationToken convert(Jwt jwt) {
		Collection<GrantedAuthority> roles = extractAuthorities(jwt);
		return new JwtAuthenticationToken(jwt, roles);
	}

	/**
	 * Extracts authorities from the "realm_access" claim in the JWT.
	 *
	 * @param jwt The JSON Web Token from which to extract authorities.
	 * @return Collection of GrantedAuthority objects extracted from the JWT.
	 */
	private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
		if (jwt.getClaim("realm_access") != null) {
			Map<String, Object> realmAccess = jwt.getClaim("realm_access");
			ObjectMapper mapper = new ObjectMapper();
			List<String> keycloakRoles = mapper.convertValue(realmAccess.get("roles"),
					new TypeReference<List<String>>() {
					});
			List<GrantedAuthority> roles = new ArrayList<>();

			for (String keycloakRole : keycloakRoles) {
				roles.add(new SimpleGrantedAuthority(keycloakRole));
			}

			return roles;
		}
		return new ArrayList<>();
	}
}
