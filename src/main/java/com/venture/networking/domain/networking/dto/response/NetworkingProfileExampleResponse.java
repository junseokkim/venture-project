package com.venture.networking.domain.networking.dto.response;

import java.util.List;

public record NetworkingProfileExampleResponse(
    List<ProfileFieldResponse> profileFields
) {
}
