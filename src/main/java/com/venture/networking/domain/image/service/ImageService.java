package com.venture.networking.domain.image.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.venture.networking.domain.image.dto.response.ImageInquiryResponse;
import com.venture.networking.domain.image.dto.response.ImageUploadResponse;
import com.venture.networking.domain.image.entity.ImageCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 1시간
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    public ImageUploadResponse uploadFile(MultipartFile file, ImageCategory type) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = type + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, fileObj));
        fileObj.delete();
        return ImageUploadResponse.from(fileName);
    }

    public ImageInquiryResponse generatePreSignedUrl(String objectKey, ImageCategory type) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += EXPIRATION_TIME;
        expiration.setTime(expTimeMillis);

        // TODO: S3 버킷에 파일이 존재하지 않을 경우 예외 처리

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
            new GeneratePresignedUrlRequest(bucket, objectKey)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return ImageInquiryResponse.from(url.toString());
    }

    public void deleteFile(String fileName, ImageCategory type) {

        // TODO: S3 버킷에 파일이 존재하지 않을 경우 예외 처리
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return convertedFile;
    }
}
