package com.venture.networking.domain.networking.dto.response;

import com.venture.networking.domain.networking.entity.FieldType;
import com.venture.networking.domain.networking.entity.ProfileField;

public record ProfileFieldResponse(
    Long profileFieldId,
    String name,
    FieldType type
) {
    public static ProfileFieldResponse from(ProfileField profileField) {
        return new ProfileFieldResponse(
            profileField.getId(),
            profileField.getName(),
            profileField.getType()
        );
    }
}
