package com.venture.networking.domain.image.dto.response;

public record ImageUploadResponse(String imageName) {
    public static ImageUploadResponse from(String imageName) {
        return new ImageUploadResponse(imageName);
    }
}
