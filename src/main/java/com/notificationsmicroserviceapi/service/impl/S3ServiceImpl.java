package com.notificationsmicroserviceapi.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.notificationsmicroserviceapi.common.property.S3Properties;
import com.notificationsmicroserviceapi.service.S3Service;
import com.notificationsmicroserviceapi.util.FileUtilTools;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3;
    private final S3Properties s3Properties;

    @Autowired
    public S3ServiceImpl(AmazonS3 amazonS3, S3Properties s3Properties) {
        this.amazonS3 = amazonS3;
        this.s3Properties = s3Properties;
    }

    @Override
    public File getFileByFileName(String fileName) {
        return Optional.of(fileName)
                .map(this::getObjectS3)
                .map(s3Object -> getFileFromInputStream(fileName, s3Object))
                .orElseThrow(() -> new RuntimeException("Error uploading file"));
    }

    private File getFileFromInputStream(String fileName, S3Object s3Object) {

        try{
            return FileUtilTools.downloadS3ObjectToFile(s3Object.getObjectContent(), fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private S3Object getObjectS3(String fileKey) {

        return amazonS3.getObject(s3Properties.getBucket(), fileKey);

    }

    @Override
    public String uploadTemplate(MultipartFile multipartFile) {
        return Optional.of(multipartFile)
                .filter(given -> !given.isEmpty())
                .map(this::getObjectMetaData)
                .map(given -> this.uploadFileS3(given, multipartFile))
                .orElseThrow(() -> new RuntimeException("Error uploading file"));
    }

    private String uploadFileS3(ObjectMetadata objectMetadata, MultipartFile file) {

        String fileKey = UUID.randomUUID().toString().concat("." + FilenameUtils.getExtension(file.getOriginalFilename()));

        try {
            amazonS3.putObject(putObjectRequest(objectMetadata, fileKey, file));
            return fileKey;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private PutObjectRequest putObjectRequest(ObjectMetadata objectMetadata, String fileKey, MultipartFile file) throws IOException {

        return new PutObjectRequest(
                s3Properties.getBucket(), fileKey, file.getInputStream(), objectMetadata
        );

    }

    private ObjectMetadata getObjectMetaData(MultipartFile metaData) {
        var objectMetadata = new ObjectMetadata();

        objectMetadata.setContentType(metaData.getContentType());
        objectMetadata.setContentLength(metaData.getSize());

        return objectMetadata;
    }
}
