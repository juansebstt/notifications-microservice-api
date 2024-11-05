package com.notificationsmicroserviceapi.common.event;

import jakarta.validation.constraints.Email;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NotificationEvent {

    @Email
    private String receiverEmail;

    private String message;

}
