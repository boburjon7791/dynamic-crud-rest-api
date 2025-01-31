package com.example.dynamic_crud_rest_api.base.config.security;

import com.example.dynamic_crud_rest_api.base.model.response.ApiResponse;
import com.example.dynamic_crud_rest_api.base.model.response.ResultCodes;
import com.example.dynamic_crud_rest_api.base.utils.ApiConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService customUserDetailsService;
    private final ObjectMapper objectMapper;

    public static final String[] WHITE_LIST={
            ApiConstants.BASE_PATH +"/auth/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers(WHITE_LIST).permitAll();
                    registry.requestMatchers(permitAllRequestMatchers()).permitAll();
                    registry.requestMatchers(authenticatedRequestMatchers()).authenticated();
                    registry.anyRequest().permitAll();
                })
                .authenticationProvider(authenticationProvider())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling->{
                    exceptionHandling.authenticationEntryPoint(authenticationEntryPoint());
                    exceptionHandling.accessDeniedHandler(accessDeniedHandler());
                })
                .build();
    }

    public AuthenticationEntryPoint authenticationEntryPoint(){
        return (request, response, authException) -> {
            ApiResponse<Object> apiResponse = ApiResponse.error(authException.getMessage(), ResultCodes.AUTHORIZATION_REQUIRED);
            response.getOutputStream().write(objectMapper.writeValueAsBytes(apiResponse));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        };
    }

    public AccessDeniedHandler accessDeniedHandler(){
        return (request, response, accessDeniedException) -> {
            ApiResponse<Object> apiResponse = ApiResponse.error(accessDeniedException.getMessage(), ResultCodes.ACCESS_DENIED);
            response.getOutputStream().write(objectMapper.writeValueAsBytes(apiResponse));
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static RequestMatcher[] permitAllRequestMatchers(){
        RequestMatcher allGet=new AntPathRequestMatcher(ApiConstants.BASE_PATH+"/**", HttpMethod.GET.name());
        return new RequestMatcher[]{allGet};
    }
    public static RequestMatcher[] authenticatedRequestMatchers(){
        RequestMatcher authenticatedPost=new AntPathRequestMatcher(ApiConstants.BASE_PATH+"/**", HttpMethod.POST.name());
        RequestMatcher authenticatedPut=new AntPathRequestMatcher(ApiConstants.BASE_PATH+"/**", HttpMethod.PUT.name());
        RequestMatcher authenticatedDelete=new AntPathRequestMatcher(ApiConstants.BASE_PATH+"/**", HttpMethod.DELETE.name());
        return new RequestMatcher[]{authenticatedPost, authenticatedPut, authenticatedDelete};
    }
}
