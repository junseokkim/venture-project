package com.venture.networking.domain.networking.dto.response;

public record NetworkingCreateResponse(Long networkingId) {
    public static NetworkingCreateResponse from(Long networkingId) {
        return new NetworkingCreateResponse(networkingId);
    }
}
