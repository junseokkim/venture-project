package com.venture.networking.global.common.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
public class S3ImageService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, fileObj));
        fileObj.delete();
        return fileName;
    }

    public String generatePreSignedUrl(String objectKey, long expirationTime) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += expirationTime;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
            new GeneratePresignedUrlRequest(bucket, objectKey)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    public void deleteFile(String fileName) {
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
