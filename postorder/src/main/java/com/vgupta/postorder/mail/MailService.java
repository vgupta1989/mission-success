package com.vgupta.postorder.mail;

import com.vgupta.postorder.common.model.EmailClient;
import com.vgupta.postorder.common.model.Media;
import com.vgupta.postorder.common.model.Order;
import com.vgupta.postorder.invoice.Invoice;
import com.vgupta.postorder.common.model.MediaService;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MailService {
    private EmailClient emailClient;
    public  void Start(){
        //Assuming This As Queueing System Client
        BlockingQueue<MailEvent> eventQueue = new ArrayBlockingQueue<>(1000);
        consumer(eventQueue);
    }


    public void consumer(BlockingQueue<MailEvent> eventQueue){
        while(true){
            MailEvent mailEvent = eventQueue.poll();
            String mediaId = mailEvent.getMediaId();
            Order order = mailEvent.getOrder();
//            Invoice invoice =  (orderId);
            //Get MediaService Contract
            MediaService mediaService = null;
            Media media = mediaService.getMedia(mediaId);
            if(media.getInvoice() != null){
                Email email = generateMailWithInvoice(order, media.getInvoice());
                sendEmail(eventQueue, mailEvent, email);
            }
            else
            {
                if(mailEvent.eventType.equals(MailEventType.CONFIRMATION)){
                    Email email = generateMailWithoutInvoice(order);
                   boolean success =  sendEmail(eventQueue, mailEvent, email);
                   if(success){
                       mailEvent.eventType = MailEventType.INVOICE;
                       eventQueue.offer(mailEvent);
                    }
                }
            }

        }
    }

    private boolean sendEmail(BlockingQueue<MailEvent> eventQueue, MailEvent mailEvent, Email email) {
        boolean failed = false;
        try {
            emailClient.send(email);
        }catch (Exception e){
            mailEvent.retryCount++;
            eventQueue.offer(mailEvent);
            failed = true;
        }
        return failed;
    }

    private Email generateMailWithoutInvoice(Order order) {
        return null;
    }

    private Email generateMailWithInvoice(Order order, Invoice invoice) {
        //lo
        return null;
    }
}
