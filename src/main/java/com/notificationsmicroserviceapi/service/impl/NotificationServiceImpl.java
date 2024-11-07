package com.notificationsmicroserviceapi.service.impl;

import com.notificationsmicroserviceapi.common.event.NotificationEvent;
import com.notificationsmicroserviceapi.common.property.MailgunProperties;
import com.notificationsmicroserviceapi.service.NotificationService;
import com.notificationsmicroserviceapi.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final S3Service s3Service;
    private final MailgunProperties mailgunProperties;
    private final TemplateEngine templateEngine;

    @Autowired
    public NotificationServiceImpl(S3Service s3Service, MailgunProperties mailgunProperties, TemplateEngine templateEngine) {
        this.s3Service = s3Service;
        this.mailgunProperties = mailgunProperties;
        this.templateEngine = templateEngine;
    }

    @Override
    public String sendNotification(NotificationEvent notificationEvent) {
        return "";
    }
}
