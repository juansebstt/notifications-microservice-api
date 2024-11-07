package com.notificationsmicroserviceapi.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface S3Service {

    File getFileByFileName(String fileName);
    String uploadTemplate(MultipartFile multipartFile);

}
