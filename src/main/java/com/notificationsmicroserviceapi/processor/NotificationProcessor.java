package com.notificationsmicroserviceapi.processor;

import com.notificationsmicroserviceapi.common.event.NotificationEvent;
import com.notificationsmicroserviceapi.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.function.Function;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationProcessor implements Function<Flux<NotificationEvent>, Mono<Void>> {

    public final NotificationService notificationService;

    @Override
    public Mono<Void> apply(Flux<NotificationEvent> notificationEventFlux) {
        return notificationEventFlux
                .doOnNext(entryEvent -> log.info("Recieved event"))
                .map(notificationService::sendNotification)
                .doOnNext(notificationEvent -> log.info("Notification Event: {}", notificationEvent))
                .subscribeOn(Schedulers.parallel())
                .then();
    }
}
