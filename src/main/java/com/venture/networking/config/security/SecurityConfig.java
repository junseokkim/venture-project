package com.venture.networking.config.security;

import com.venture.networking.config.security.auth.CustomAuthenticationSuccessHandler;
import com.venture.networking.config.security.auth.CustomOAuth2UserService;
import com.venture.networking.config.security.jwt.JwtAuthenticationEntryPoint;
import com.venture.networking.config.security.jwt.JwtAuthenticationFilter;
import com.venture.networking.config.security.jwt.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
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
                    .requestMatchers("/auth/**", "/image/**").permitAll()

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
        configuration.addAllowedOrigin("http://localhost:8080");
        // TODO: Change this to your EC2 domain
        // configuration.addAllowedOrigin("http://your-ec2-domain.com");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
