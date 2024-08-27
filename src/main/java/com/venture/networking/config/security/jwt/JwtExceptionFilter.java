package com.venture.networking.config.security.jwt;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.venture.networking.global.common.exception.TokenExpiredException;
import com.venture.networking.global.common.response.BaseResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            log.error("TokenExpiredException: {}", e.getMessage());
            setErrorResponse(response, e, BaseResponse.fail(e.getMessage()), HttpServletResponse.SC_FORBIDDEN);
        } catch (AuthenticationException e) {
            log.error("AuthenticationException: {}", e.getMessage());
            setErrorResponse(response, e, BaseResponse.fail(e.getMessage()), HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception e) {
            log.error("Exception: {}", e.getMessage());
            setErrorResponse(response, e, BaseResponse.fail(e.getMessage()), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void setErrorResponse(
        HttpServletResponse response,
        Exception e,
        BaseResponse<Void> baseResponse,
        int status
    ) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        String bodyString = objectMapper.writeValueAsString(baseResponse);
        response.getWriter().write(bodyString);
    }
}