package com.venture.networking.domain.profile.dto.response;

import com.venture.networking.domain.networking.entity.FieldType;
import com.venture.networking.domain.profile.entity.ProfileData;

public record ProfileDataResponse(
    Long profileDataId,
    String name,
    String value,
    FieldType fieldType
) {
    public static ProfileDataResponse from(ProfileData profileData) {
        return new ProfileDataResponse(
            profileData.getId(),
            profileData.getProfileField().getName(),
            profileData.getValue(),
            profileData.getProfileField().getType()
        );
    }
}
