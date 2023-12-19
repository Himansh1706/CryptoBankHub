/**
 * Configuration class for defining security settings in the application.
 * Enables Web Security and Method Security, and configures security filters, CORS, and JWT authentication.
 * Additionally, it provides settings for handling Swagger documentation paths and custom roles.
 *
 * @author mdsharif
 * @version 1.0
 * @since 2023-01-01
 */
package com.custom.security.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.custom.security.requestdto.PathProviderRequestDto;

import lombok.RequiredArgsConstructor;

/**
 * Configuration class for defining security settings in the application.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;
    private final PathProviderRequestDto pathProviderRequestDto;

    /**
     * Configures the security filter chain.
     *
     * @param http The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(t -> t.disable());
        http.cors(t -> corsConfigurationSource());
        http.authorizeHttpRequests(
                authorize -> authorize.requestMatchers(pathProviderRequestDto.getNonAuthenticatedPaths()).permitAll()
                        .requestMatchers("/keycloak/*").permitAll().requestMatchers(SWAGGER_PATH_ALLOWED).permitAll()
                        .anyRequest().authenticated());
        http.oauth2ResourceServer(t -> t.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)));
        http.sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    /**
     * Configures the default method security expression handler.
     *
     * @return The configured DefaultMethodSecurityExpressionHandler.
     */
    @Bean
    public DefaultMethodSecurityExpressionHandler msecurity() {
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        defaultMethodSecurityExpressionHandler.setDefaultRolePrefix("");
        return defaultMethodSecurityExpressionHandler;
    }

    /**
     * Configures the CORS (Cross-Origin Resource Sharing) settings.
     *
     * @return The configured CorsConfigurationSource.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Array of Swagger paths allowed without authentication.
     */
    protected static final String[] SWAGGER_PATH_ALLOWED = {
            "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources",
            "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swebjars/**",
            "swagger-ui/index.html", "swagger-ui/**", "/swagger-ui.html", "/v3/api-docs.yml"
    };
}
