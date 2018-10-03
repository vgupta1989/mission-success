package com.vgupta.postorder.mail;

import com.vgupta.postorder.common.model.EmailClient;
import com.vgupta.postorder.common.model.Media;
import com.vgupta.postorder.common.model.Order;
import com.vgupta.postorder.common.model.MediaService;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MailService {
    private EmailClient emailClient;

    public void Start() {
        //Assuming This As Queueing System Client
        BlockingQueue<MailEvent> eventQueue = new ArrayBlockingQueue<>(1000);
        consumer(eventQueue);
    }


    public void consumer(BlockingQueue<MailEvent> eventQueue) {
        while (true) {
            MailEvent mailEvent = eventQueue.poll();
            String mediaId = mailEvent.getMediaId();
            Order order = mailEvent.getOrder();
//            Invoice invoiceUrl =  (orderId);
            //Get MediaService Contract
            MediaService mediaService = null;
            Media media = mediaService.getMedia(mediaId);
            if (media.getInvoiceUrl() != null) {
                Email email = generateMailWithInvoice(order, media);
                boolean success = sendEmail(email);
                //If failed then put the event in queue again to retry
                if (!success) {
                    mailEvent.retryCount++;
                    eventQueue.offer(mailEvent);
                }
            } else {
                if (mailEvent.eventType.equals(MailEventType.CONFIRMATION)) {
                    Email email = getConfirmationMail(order);
                    boolean success = sendEmail(email);
                    //if email successfully sent then put an event in queue to
                    //send another email with Invoice.
                    if (success) {
                        mailEvent.eventType = MailEventType.INVOICE;
                        mailEvent.retryCount = 0;
                        eventQueue.offer(mailEvent);
                    } else {
                        //if confirmation email send failed put a event in queue
                        //for sending confirmation mail again to retry
                        mailEvent.retryCount++;
                        eventQueue.offer(mailEvent);
                    }
                }
            }

        }
    }

    private boolean sendEmail(Email email) {
        boolean success = true;
        try {
            emailClient.send(email);
        } catch (Exception e) {

            success = false;
        }
        return success;
    }

    private Email getConfirmationMail(Order order) {
        //Logic to generate Mail Content and generating Email.
        return new Email();
    }

    private Email generateMailWithInvoice(Order order, Media media) {
        //Logic to generate Email With Invoice Attached.
        return new Email();
    }
}
