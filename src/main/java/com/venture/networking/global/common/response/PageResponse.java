package com.venture.networking.global.common.response;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record PageResponse<T>(
    List<T> results,
    int pageNum,
    int pageSize,
    int totalPages,
    boolean isFirst,
    boolean isLast
) {

    public static <T> PageResponse<T> from(Page<T> responsePage) {
        return PageResponse.<T>builder()
            .results(responsePage.getContent())
            .pageNum(responsePage.getNumber())
            .pageSize(responsePage.getSize())
            .totalPages(responsePage.getTotalPages())
            .isFirst(responsePage.isFirst())
            .isLast(responsePage.isLast())
            .build();
    }
}
