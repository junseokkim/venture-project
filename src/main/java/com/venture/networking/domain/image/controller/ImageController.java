package com.venture.networking.domain.image.controller;

import com.venture.networking.domain.image.dto.response.ImageInquiryResponse;
import com.venture.networking.domain.image.dto.response.ImageUploadResponse;
import com.venture.networking.domain.image.entity.ImageCategory;
import com.venture.networking.global.common.response.BaseResponse;
import com.venture.networking.domain.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "이미지 API", description = "이미지 관련 API")
@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService ImageService;

    @Operation(summary = "이미지 업로드 API", description = "이미지를 업로드하는 API입니다.")
    @PostMapping( value = "/upload", consumes = {"multipart/form-data"})
    public BaseResponse<ImageUploadResponse> uploadFile(
        @RequestParam("file") MultipartFile file,
        @RequestParam("type") ImageCategory type
    ) {
        return BaseResponse.ok(ImageService.uploadFile(file, type));
    }

    @GetMapping("/image-url")
    public BaseResponse<ImageInquiryResponse> getImageUrl(
        @RequestParam("fileName") String fileName,
        @RequestParam("type") ImageCategory type
    ) {
        return BaseResponse.ok(ImageService.generatePreSignedUrl(fileName, type));
    }

    @Operation(summary = "이미지 삭제 API", description = "이미지를 삭제하는 API입니다.")
    @DeleteMapping("/delete")
    public BaseResponse<Void> deleteFile(
        @RequestParam("fileName") String fileName,
        @RequestParam("type") ImageCategory type
    ){
        ImageService.deleteFile(fileName, type);
        return BaseResponse.ok();
    }
}