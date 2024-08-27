package com.venture.networking.config.security.auth;

import com.venture.networking.domain.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class PrincipalDetails implements UserDetails {

    private final Member member;
    private final Long profileId;

    public PrincipalDetails(Member member, Long profileId) {
        this.member = member;
        this.profileId = profileId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "ROLE_USER");
    }

    @Override
    public String getPassword() {
        return null;  // 비밀번호가 필요하지 않음 (소셜 로그인 사용)
    }

    @Override
    public String getUsername() {
        return member.getEmail();  // 이메일을 username으로 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return member.getDeletedAt() == null;  // 삭제된 계정은 비활성화
    }
}
