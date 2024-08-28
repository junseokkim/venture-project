package com.venture.networking.domain.networking.dto.response;

import com.venture.networking.domain.networking.entity.Networking;
import lombok.Builder;

@Builder
public record NetworkingSummaryResponse(
    Long networkingId,
    String name,
    String representativeImg,
    Long participantCount
) {
    public static NetworkingSummaryResponse from(Networking networking, Long participantCount) {
        return NetworkingSummaryResponse.builder()
            .networkingId(networking.getId())
            .name(networking.getName())
            .representativeImg(networking.getRepresentativeImg())
            .participantCount(participantCount)
            .build();
    }
}
