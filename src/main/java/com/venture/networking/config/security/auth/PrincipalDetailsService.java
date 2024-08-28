package com.venture.networking.config.security.auth;

import com.venture.networking.domain.member.entity.Member;
import com.venture.networking.domain.member.repository.MemberRepository;
import com.venture.networking.domain.profile.entity.Profile;
import com.venture.networking.domain.profile.repository.ProfileRepository;
import com.venture.networking.global.common.exception.MemberNotFoundException;
import com.venture.networking.global.common.exception.ProfileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member memberEntity = memberRepository.findByEmail(email)
            .orElseThrow(MemberNotFoundException::new);

        return new PrincipalDetails(memberEntity, null);  // 초기에는 profileId를 null로 설정
    }

    public PrincipalDetails loadUserByProfileId(String email, Long profileId) throws UsernameNotFoundException {
        Member memberEntity = memberRepository.findByEmail(email)
            .orElseThrow(MemberNotFoundException::new);

        Profile profile = profileRepository.findByIdAndMember(profileId, memberEntity)
            .orElseThrow(ProfileNotFoundException::new);

        return new PrincipalDetails(memberEntity, profile.getId());
    }
}