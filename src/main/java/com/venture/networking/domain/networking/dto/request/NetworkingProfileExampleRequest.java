package com.venture.networking.domain.networking.dto.request;

import java.util.List;

public record NetworkingProfileExampleRequest(
    String name,
    String representativeImg,
    List<String> tags,
    List<ProfileFieldExampleRequest> profileFields
) {
}
