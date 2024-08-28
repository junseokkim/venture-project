package com.venture.networking.domain.networking.dto.request;

public record NetworkingCreateRequest(
    String name,
    String representativeImg,
    String description,
    String relatedLink
) {
}
