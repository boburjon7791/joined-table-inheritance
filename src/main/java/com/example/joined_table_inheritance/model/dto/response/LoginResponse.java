package com.example.joined_table_inheritance.model.dto.response;

import com.example.joined_table_inheritance.config.security.TokenDTO;

public record LoginResponse(
        BaseUserResponse user,
        TokenDTO tokens
) {
    public static LoginResponse of(BaseUserResponse user, TokenDTO tokens) {
        return new LoginResponse(user, tokens);
    }
}
