package com.venture.networking.domain.auth.dto.response;

public record AuthTokenIssueResponse(
    String accessToken,
    String refreshToken
) {

}
