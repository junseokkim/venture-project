package com.venture.networking.domain.networking.dto.request;

import com.venture.networking.domain.networking.entity.FieldType;

public record ProfileFieldExampleRequest(
    String name,
    Long number,
    String value,
    FieldType type
) {
}
