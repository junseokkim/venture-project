package com.venture.networking.domain.image.dto.response;

public record ImageInquiryResponse(String imageUrl) {
    public static ImageInquiryResponse from(String imageUrl) {
        return new ImageInquiryResponse(imageUrl);
    }
}
