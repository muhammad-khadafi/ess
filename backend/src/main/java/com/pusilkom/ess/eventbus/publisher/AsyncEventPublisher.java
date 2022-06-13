package com.pusilkom.ess.eventbus.publisher;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.pusilkom.ess.eventbus.subscriber.EmailEventSubscriber;
import com.pusilkom.ess.util.DebugUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;

@Component
public class AsyncEventPublisher {

    Logger log = LoggerFactory.getLogger(this.getClass());

    AsyncEventBus asyncEventBus;

    @Autowired EmailEventSubscriber emailEventSubscriber;

    @Autowired DebugUtil debugUtil;

    @PostConstruct
    private void init() {
        asyncEventBus = new AsyncEventBus(Executors.newCachedThreadPool(), new SubscriberExceptionHandler() {
            @Override
            public void handleException(Throwable throwable, SubscriberExceptionContext subscriberExceptionContext) {
                log.error("asyncEventBus : ", throwable);
            }
        });
        asyncEventBus.register(emailEventSubscriber);
    }

    public void sendEvent(Object event) {
        asyncEventBus.post(event);
    }

}
