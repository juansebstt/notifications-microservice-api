package com.notificationsmicroserviceapi.service;

import com.notificationsmicroserviceapi.common.event.NotificationEvent;

public interface NotificationService {

    String sendNotification(NotificationEvent notificationEvent);

}
