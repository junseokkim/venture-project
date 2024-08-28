package com.venture.networking.domain.networking.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record NetworkingParticipateListResponse(
    List<NetworkingSummaryResponse> networkingList
) {
    public static NetworkingParticipateListResponse from(List<NetworkingSummaryResponse> networkingList) {
        return NetworkingParticipateListResponse.builder()
            .networkingList(networkingList)
            .build();
    }
}
