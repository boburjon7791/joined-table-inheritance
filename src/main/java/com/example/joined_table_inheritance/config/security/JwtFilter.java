package com.example.joined_table_inheritance.config.security;

import com.example.joined_table_inheritance.config.model.response.ApiResponse;
import com.example.joined_table_inheritance.config.utils.ResultCodes;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    protected final CustomUserDetailsService customUserDetailsService;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JWT Filter : {}, {}", request.getRequestURI(), request.getHeader(HttpHeaders.USER_AGENT));
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization==null || authorization.isBlank() || authorization.length()<10) {
            filterChain.doFilter(request, response);
            return;
        }
        authorization=authorization.substring(7);
        try {
            String username = jwtProvider.validateToken(authorization);
            CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }catch (AuthenticationException e){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ApiResponse<Void> apiResponse = ApiResponse.error(e.getMessage(), ResultCodes.CONFLICT);
            response.getOutputStream().write(objectMapper.writeValueAsBytes(apiResponse));
            response.getOutputStream().flush();
            return;
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ApiResponse<Void> apiResponse = ApiResponse.error(e.getMessage(), ResultCodes.INTERNAL_SERVER_ERROR);
            response.getOutputStream().write(objectMapper.writeValueAsBytes(apiResponse));
            response.getOutputStream().flush();
            return;
        }
        filterChain.doFilter(request, response);
    }
}
