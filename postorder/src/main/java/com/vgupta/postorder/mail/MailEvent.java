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
    int retryCount;
}
