package com.venture.networking.domain.networking.dto.request;

import java.util.List;

public record NetworkingProfileCreateRequest(
    String name,
    String representativeImg,
    List<String> tags,
    List<ProfileFieldCreateRequest> profileFields
) {
}
