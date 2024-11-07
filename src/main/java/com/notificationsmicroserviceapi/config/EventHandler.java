package com.notificationsmicroserviceapi.config;

import com.notificationsmicroserviceapi.common.event.NotificationEvent;
import com.notificationsmicroserviceapi.processor.NotificationProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class EventHandler {

    @Bean
    public Function<Flux<NotificationEvent>, Mono<Void>> notificationBinding(final NotificationProcessor processor) {
        return processor;
    }
    
}