package com.venture.networking.domain.auth.service;

import com.venture.networking.domain.auth.dto.request.AuthReIssueRequest;
import com.venture.networking.domain.auth.dto.response.AuthTokenIssueResponse;
import com.venture.networking.domain.auth.repository.TokenRepository;
import com.venture.networking.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;

    public AuthTokenIssueResponse reIssueToken(AuthReIssueRequest request) {
        return null;
    }
}
