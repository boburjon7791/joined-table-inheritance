package com.example.joined_table_inheritance.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${jwt.access.token.key}")
    private String accessTokenKey;

    @Value("${jwt.access.token.expiration}")
    private Long accessTokenExpiration;

    @Value("${spring.application.name}")
    private String applicationName;

    public TokenDTO generateToken(String source){
        Date accessTokenExpiration = new Date(System.currentTimeMillis() + this.accessTokenExpiration);
        String accessToken= Jwts.builder()
                .subject(source)
                .issuedAt(new Date())
                .issuer(applicationName)
                .expiration(accessTokenExpiration)
                .signWith(getAccessTokenKey())
                .compact();
        return TokenDTO.of(accessToken, accessTokenExpiration);
    }

    public String validateToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getAccessTokenKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        if (claims.getExpiration().before(new Date())) {
            throw new RuntimeException("Expired or invalid JWT token");
        }
        return claims.getSubject();
    }

    private SecretKey getAccessTokenKey(){
        return Keys.hmacShaKeyFor(accessTokenKey.getBytes());
    }
}
