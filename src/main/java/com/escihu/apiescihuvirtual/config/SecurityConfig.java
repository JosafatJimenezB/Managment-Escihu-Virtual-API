package com.escihu.apiescihuvirtual.config;

import com.escihu.apiescihuvirtual.utils.RSAKeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RSAKeyProperties keys;
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";
    private static final String TEACHER_ROLE = "TEACHER";

    private static final String STUDENT_ROLE = "STUDENT";

    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "webjars/**",
            "/swagger-ui.html",

    };

    private static final String[] ADMIN_LIST = {
            "/api/v1/**"
    };

    private static final String[] STUDENT_LIST = {
            "/api/v1/attendance/**"
    };

    private static final String[] TEACHER_LIST = {
            "/api/v1/attendance/**",
            "/api/v1/teacher/{id}",
            "/api/v1/attendance/{userId}/paginated"
    };

    public SecurityConfig(RSAKeyProperties keys) {
        this.keys = keys;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService detailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(detailsService);
        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(AUTH_WHITELIST).permitAll();
                    auth.requestMatchers("/api/v1/auth/login").permitAll();
                    auth.requestMatchers("/api/v1/auth/register").permitAll();
                    auth.requestMatchers(HttpMethod.PUT,"/api/v1/user/**").permitAll();
                    auth.requestMatchers("/api/v1/students/**", "/api/v1/student/").hasAnyRole(ADMIN_ROLE);
                    auth.requestMatchers(HttpMethod.PUT,"/api/v1/student/{id}").hasRole(ADMIN_ROLE);
                    auth.requestMatchers(HttpMethod.GET,"/api/v1/student/{id}").hasAnyRole(ADMIN_ROLE, STUDENT_ROLE);
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/attendance/{userId}/paginated").hasRole(STUDENT_ROLE);
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/user/profile-image-url/{userId}").hasAnyRole(ADMIN_ROLE, STUDENT_ROLE);
                    auth.requestMatchers(HttpMethod.POST, "/api/v1/user/upload/{userId}").hasAnyRole(ADMIN_ROLE, STUDENT_ROLE);
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/attendance/{userId}/paginated").hasAnyRole(ADMIN_ROLE, STUDENT_ROLE, TEACHER_ROLE);
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/teacher/{id}").hasAnyRole(ADMIN_ROLE, TEACHER_ROLE);
                    auth.requestMatchers(HttpMethod.GET, ADMIN_LIST).hasRole(ADMIN_ROLE);
                    auth.requestMatchers(HttpMethod.GET, TEACHER_LIST).hasRole(TEACHER_ROLE);
                    auth.requestMatchers(HttpMethod.GET, STUDENT_LIST).hasRole(STUDENT_ROLE);
                    auth.requestMatchers(HttpMethod.POST, ADMIN_LIST).hasRole(ADMIN_ROLE);
                    auth.requestMatchers(HttpMethod.PUT, ADMIN_LIST).hasRole(ADMIN_ROLE);
                    auth.requestMatchers(HttpMethod.DELETE, ADMIN_LIST).hasRole(ADMIN_ROLE);
                    auth.requestMatchers("/api/v1/admin/**").hasRole(ADMIN_ROLE);
                    auth.anyRequest().authenticated();
                });

        http
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }


    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtConvert = new JwtAuthenticationConverter();
        jwtConvert.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConvert;
    }

}
