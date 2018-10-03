package com.vgupta.postorder.mail;

import com.vgupta.postorder.common.model.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailEvent {
    Order order;
    String mediaId;
    MailEventType eventType;
    //retryCount tells how many time this event processing has failed.
    //We can use this retry count to create an alert to developer team
    // when it's value is greater than some threshold
    int retryCount;
}
