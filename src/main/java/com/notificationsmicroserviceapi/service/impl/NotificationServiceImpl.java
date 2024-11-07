package com.notificationsmicroserviceapi.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.notificationsmicroserviceapi.common.event.NotificationEvent;
import com.notificationsmicroserviceapi.common.property.MailgunProperties;
import com.notificationsmicroserviceapi.service.NotificationService;
import com.notificationsmicroserviceapi.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.nio.file.Files;

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
        try{
            String htmlTemplate = fetchTemplateFromS3("template.html");
            String htmlContent = processTemplate(htmlTemplate, notificationEvent);
            return sendEmail(notificationEvent, htmlContent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String  sendEmail(NotificationEvent notificationEvent, String htmlContent) throws Exception {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + mailgunProperties.getUrl() + "/messages")
                .basicAuth("api", mailgunProperties.getApiKey())
                .queryString("from", "<no-reply@covalance.io>")
                .queryString("to", notificationEvent.getReceiverEmail() != null ? notificationEvent.getReceiverEmail() : "")
                .queryString("subject", "Notification")
                .queryString("html", htmlContent)
                .asJson();
        return request.getStatusText();
    }

    private String processTemplate(String htmlTemplate, NotificationEvent notificationEvent) {

        Context context = new Context();
        context.setVariable("receiverEmail", notificationEvent.getReceiverEmail());
        context.setVariable("message", notificationEvent.getMessage());
        return templateEngine.process(htmlTemplate, context);
    }

    private String fetchTemplateFromS3(String fileName) throws Exception {

        File templateFile = s3Service.getFileByFileName(fileName);
        return new String(Files.readAllBytes(templateFile.toPath()));

    }

}
