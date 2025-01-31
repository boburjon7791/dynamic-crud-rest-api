package com.example.dynamic_crud_rest_api.base.config.security;

import com.example.dynamic_crud_rest_api.base.exception.ApiException;
import com.example.dynamic_crud_rest_api.base.model.response.ApiResponse;
import com.example.dynamic_crud_rest_api.base.model.response.ResultCodes;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || authorization.isBlank() || authorization.length()<20) {
            filterChain.doFilter(request, response);
            return;
        }
        authorization=authorization.substring(7);
        try {
            Claims claims = jwtProvider.extractAndGetClaims(authorization, true);
            CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        }catch (JwtException e){
            ApiResponse<Object> apiResponse = ApiResponse.error(e.getMessage(), ResultCodes.CONFLICT);
            response.getOutputStream().write(objectMapper.writeValueAsBytes(apiResponse));
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getOutputStream().flush();
        }catch (ApiException e){
            ApiResponse<Object> apiResponse = ApiResponse.error(e.getMessage(), e.getResultCode());
            response.getOutputStream().write(objectMapper.writeValueAsBytes(apiResponse));
            response.setStatus(e.getHttpStatus().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getOutputStream().flush();
        }catch (Exception e){
            ApiResponse<Object> apiResponse = ApiResponse.error(ResultCodes.SERVER_ERROR, e.getMessage());
            response.getOutputStream().write(objectMapper.writeValueAsBytes(apiResponse));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getOutputStream().flush();
        }
    }
}
