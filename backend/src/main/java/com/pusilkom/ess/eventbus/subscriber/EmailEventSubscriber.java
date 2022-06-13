package com.pusilkom.ess.eventbus.subscriber;

import com.google.common.eventbus.Subscribe;
import com.pusilkom.ess.eventbus.event.SendEmailEvent;
import com.pusilkom.ess.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailEventSubscriber {

    @Autowired EmailUtil emailUtil;

    @Subscribe
    public void processingSendEmailEvent(SendEmailEvent sendEmailEvent) {

        emailUtil.send(sendEmailEvent.getEmailDest(), sendEmailEvent.getSubject(),
                sendEmailEvent.getContent(), sendEmailEvent.getFileAttachment());
    }

}
