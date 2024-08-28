package com.venture.networking.domain.profile.dto.response;

import java.util.List;

public record ProfileListResponse(
    List<ProfileSummaryResponse> profiles
) {
}
