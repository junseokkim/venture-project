package com.venture.networking.config.security.auth;

import com.venture.networking.domain.member.entity.Member;
import com.venture.networking.domain.member.repository.MemberRepository;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakaoAccount.get("email");

        if (email == null || email.isEmpty()) {
            log.error("이메일 정보를 가져올 수 없습니다. Attributes: {}", attributes);
            throw new OAuth2AuthenticationException("이메일 정보를 가져올 수 없습니다.");
        }

        Member member = memberRepository.findByEmail(email)
            .orElseGet(() -> registerNewUser(oAuth2User, email));

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
            oAuth2User.getAttributes(),
            "id"  // 기본 키 역할을 하는 속성명
        );
    }

    private Member registerNewUser(OAuth2User oAuth2User, String email) {
        // 새로운 사용자를 등록하는 로직입니다.
        return memberRepository.save(Member.builder()
            .email(email)
            .build());
    }
}

