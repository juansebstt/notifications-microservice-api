package com.notificationsmicroserviceapi.controller;

import com.notificationsmicroserviceapi.common.constant.ApiPathConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(ApiPathConstants.V1_ROUTE + ApiPathConstants.NOTIFICATION_ROUTE)
public interface NotificationsApi {

    @PostMapping("/upload_template")
    ResponseEntity<String> uploadTemplate(@RequestParam("file") MultipartFile multipartFile);

}
