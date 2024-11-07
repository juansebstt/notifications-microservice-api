package com.notificationsmicroserviceapi.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.notificationsmicroserviceapi.common.property.S3Properties;
import com.notificationsmicroserviceapi.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

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
                .map(s3Object -> getFileFromInputStream(fileName, s3Object));
    }

    private File getFileFromInputStream(String fileName, S3Object s3Object) {

        

    }

    private S3Object getObjectS3(String fileKey) {

        return amazonS3.getObject(s3Properties.getBucket(), fileKey);

    }

    @Override
    public String uploadTemplate(MultipartFile multipartFile) {
        return "";
    }
}
