package com.venture.networking.config.security.auth;

import com.venture.networking.config.security.jwt.JwtTokenProvider;
import com.venture.networking.domain.auth.dto.response.AuthTokenIssueResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    public CustomAuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
        String email = (String) kakaoAccount.get("email");

        System.out.println("Extracted Email: " + email);

        AuthTokenIssueResponse authTokenIssueResponse = jwtTokenProvider.issueToken(email, null);

        // JWT 토큰을 응답 헤더에 추가
        response.addHeader("Authorization", "Bearer " + authTokenIssueResponse.accessToken());
        response.addHeader("Refresh-Token", authTokenIssueResponse.refreshToken());

        // 로그인 성공 후 리디렉션할 URL 설정 (프론트엔드에서 처리할 페이지로 리다이렉트)
        getRedirectStrategy().sendRedirect(request, response, "/profile-selection");
    }
}