package com.venture.networking.domain.profile.dto.response;

import com.venture.networking.domain.profile.entity.Profile;
import com.venture.networking.domain.profile.entity.ProfileTag;
import java.util.List;

public record ProfileSummaryResponse(
    Long profileId,
    String name,
    String representativeImgUrl,
    List<String> tags
) {
    public static ProfileSummaryResponse from(Profile profile) {
        return new ProfileSummaryResponse(
            profile.getId(),
            profile.getName(),
            profile.getRepresentativeImg(),
            profile.getProfileTags().stream()
                .map(ProfileTag::getName)
                .toList()
        );
    }
}
