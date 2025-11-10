package com.serafim.java_blog.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${aws.bucket-name}")
    private String bucketName;

    public String putObject(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        String keyName = UUID.randomUUID() + fileExtension;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            PutObjectRequest request = new PutObjectRequest(
                    bucketName,
                    keyName,
                    file.getInputStream(),
                    metadata
            );

            amazonS3Client.putObject(request);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return keyName;
    }

    public String getUrl(String keyName) {
        return amazonS3Client.getUrl(bucketName, keyName).toString();
    }

    public void deleteObject(String keyName) {
        DeleteObjectRequest request = new DeleteObjectRequest(
                bucketName,
                keyName
        );

        amazonS3Client.deleteObject(request);
    }

}
