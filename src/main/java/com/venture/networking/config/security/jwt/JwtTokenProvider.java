package com.venture.networking.config.security.jwt;

import static com.venture.networking.config.security.jwt.JwtConstant.ACCESS_TOKEN_EXPIRE_TIME;
import static com.venture.networking.config.security.jwt.JwtConstant.PROFILE_ID;
import static com.venture.networking.config.security.jwt.JwtConstant.REFRESH_TOKEN_EXPIRE_TIME;

import com.venture.networking.config.security.auth.PrincipalDetails;
import com.venture.networking.config.security.auth.PrincipalDetailsService;
import com.venture.networking.domain.auth.dto.response.AuthTokenIssueResponse;
import com.venture.networking.domain.auth.entity.Token;
import com.venture.networking.domain.auth.repository.TokenRepository;
import com.venture.networking.domain.member.repository.MemberRepository;
import com.venture.networking.global.common.exception.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final TokenRepository tokenRepository;
    private final PrincipalDetailsService principalDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public AuthTokenIssueResponse issueToken(String email, Long profileId) {
        // 기존 토큰 삭제
        if (tokenRepository.existsById(email)) {
            tokenRepository.deleteById(email);
        }

        String accessToken = generateToken(email, profileId, ACCESS_TOKEN_EXPIRE_TIME);
        String refreshToken = generateToken(email, profileId, REFRESH_TOKEN_EXPIRE_TIME);
        tokenRepository.save(new Token(email, refreshToken));
        System.out.printf("accessToken = %s, refreshToken = %s\n", accessToken, refreshToken);
        return new AuthTokenIssueResponse(accessToken, refreshToken);
    }

    private String generateToken(String email, Long profileId, long expireTime) {
        Claims claims = Jwts.claims().setSubject(email);
        if (profileId != null) {
            claims.put(JwtConstant.PROFILE_ID, profileId);
        }

        Date now = new Date();
        Date validity = new Date(now.getTime() + expireTime);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        String email = claims.getSubject();
        Long profileId = claims.get(JwtConstant.PROFILE_ID, Long.class);

        PrincipalDetails principal = (profileId != null)
            ? principalDetailsService.loadUserByProfileId(email, profileId)
            : principalDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException(); // 토큰 만료 시 추가 처리 가능
        }
        return false;
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey)
                .build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}