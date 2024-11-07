package com.notificationsmicroserviceapi.controller.impl;

import com.notificationsmicroserviceapi.controller.NotificationsApi;
import com.notificationsmicroserviceapi.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class NotificationController implements NotificationsApi {

    private final S3Service s3Service;

    public NotificationController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @Override
    public ResponseEntity<String> uploadTemplate(MultipartFile multipartFile) {
        return ResponseEntity.ok(s3Service.uploadTemplate(multipartFile));
    }
}
