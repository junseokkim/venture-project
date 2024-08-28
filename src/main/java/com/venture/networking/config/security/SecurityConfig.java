package com.venture.networking.config.security;

import com.venture.networking.config.security.auth.CustomAuthenticationSuccessHandler;
import com.venture.networking.config.security.auth.CustomOAuth2UserService;
import com.venture.networking.config.security.jwt.JwtAuthenticationEntryPoint;
import com.venture.networking.config.security.jwt.JwtAuthenticationFilter;
import com.venture.networking.config.security.jwt.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Value("${server.url}")
    private String serverUrl;

    @Value("${server.port}")
    private String serverPort;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> {})
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2Login(oauth2 -> oauth2
                .successHandler(customAuthenticationSuccessHandler)
                .failureUrl("/loginFailure")  // 로그인 실패 시 이동할 URL
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)))
            .authorizeHttpRequests((authorizeRequests) ->
                authorizeRequests
                    .requestMatchers( "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                    .requestMatchers("/auth").permitAll()

                    .anyRequest().authenticated()
            )
            .exceptionHandling(exceptionHandling -> {
                exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint);
            })
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // 모든 도메인 허용
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 인증 정보 포함 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
