package com.venture.networking.global.common.s3;

import com.venture.networking.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class S3ImageController {

    private final S3ImageService s3ImageService;

    // 파일 업로드
    @PostMapping( value = "/upload", consumes = {"multipart/form-data"})
    public BaseResponse<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = s3ImageService.uploadFile(file);
        return BaseResponse.ok("File uploaded: " + fileName);
    }

    @GetMapping("/image-url")
    public BaseResponse<String> getImageUrl(@RequestParam("fileName") String fileName) {
        // Pre-signed URL 생성 (유효 기간 1시간)
        String preSignedUrl = s3ImageService.generatePreSignedUrl(fileName, 10 * 1000);
        return BaseResponse.ok(preSignedUrl);
    }

    // 파일 삭제
    @DeleteMapping("/delete")
    public BaseResponse<String> deleteFile(@RequestParam("fileName") String fileName) {
        s3ImageService.deleteFile(fileName);
        return BaseResponse.ok("File deleted: " + fileName);
    }
}