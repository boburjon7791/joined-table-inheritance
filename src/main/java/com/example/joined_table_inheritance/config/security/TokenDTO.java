package com.example.joined_table_inheritance.config.security;

import java.util.Date;

public record TokenDTO(String accessToken, Date accessTokenExpiration) {
    public static TokenDTO of(String accessToken, Date accessTokenExpiration) {
        return new TokenDTO(accessToken, accessTokenExpiration);
    }
}
