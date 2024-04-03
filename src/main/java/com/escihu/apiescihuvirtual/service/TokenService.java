package com.escihu.apiescihuvirtual.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * TokenService is a service class that handles operations related to JSON Web Tokens (JWTs).
 * It provides a method for generating a JWT for an authenticated user.
 * It uses JwtEncoder and JwtDecoder for encoding and decoding JWTs.
 *
 */
@Service
public class TokenService {

    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;

    /**
     * Constructs a new TokenService with the specified JwtEncoder and JwtDecoder.
     *
     * @param jwtEncoder the JwtEncoder to be used by the TokenService
     * @param jwtDecoder the JwtDecoder to be used by the TokenService
     */
    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    /**
     * Generates a JWT for an authenticated user.
     * The JWT includes claims for the issuer, issued at timestamp, subject (username), and roles.
     * The roles claim is a space-separated string of the user's authorities.
     *
     * @param auth the Authentication object containing the user's authentication information
     * @return a JWT string
     */
    public String generateJwt(Authentication auth) {
        Instant now = Instant.now();
        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(auth.getName())
                .expiresAt(new Date(System.currentTimeMillis()+1000*60*24).toInstant())
                .claim("roles",scope)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
