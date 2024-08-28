package com.venture.networking.domain.profile.dto.response;

import com.venture.networking.domain.profile.entity.Profile;
import com.venture.networking.domain.profile.entity.ProfileTag;
import java.util.List;

public record ProfileDetailResponse(
    Long profileId,
    String name,
    String representativeImgUrl,
    List<String> tags,
    List<ProfileDataResponse> profileFields
) {
    public static ProfileDetailResponse from(Profile profile, List<ProfileDataResponse> profileFields) {
        return new ProfileDetailResponse(
            profile.getId(),
            profile.getName(),
            profile.getRepresentativeImg(),
            profile.getProfileTags().stream()
                .map(ProfileTag::getName)
                .toList(),
            profileFields
        );
    }
}
