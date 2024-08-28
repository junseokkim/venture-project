package com.venture.networking.domain.auth.controller;

import com.venture.networking.domain.auth.dto.request.AuthReIssueRequest;
import com.venture.networking.domain.auth.dto.response.AuthTokenIssueResponse;
import com.venture.networking.domain.auth.service.AuthService;
import com.venture.networking.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/re-issue")
    public BaseResponse<AuthTokenIssueResponse> reIssueToken(@RequestBody AuthReIssueRequest request) {
        return BaseResponse.ok(authService.reIssueToken(request));
    }

}
