package com.vgupta.postorder.invoice;

import com.vgupta.postorder.common.model.Media;
import com.vgupta.postorder.common.model.MediaService;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class InvoiceService {

    MediaService mediaService;

    public  void start(){
        //Assuming This As Queueing System Client
        BlockingQueue<InvoiceEvent> eventQueue = new ArrayBlockingQueue<>(1000);
        consumer(eventQueue);
    }


    public void consumer(BlockingQueue<InvoiceEvent> eventQueue){
        while(true){
            InvoiceEvent invoiceEvent = eventQueue.poll();
            String mediaId = invoiceEvent.getMediaId();
            String orderId = invoiceEvent.getOrderId();
            String invoiceUrl =  generateInvoice(orderId);

            //Get MediaService Contract

            Media media = mediaService.getMedia(mediaId);
            media.setInvoiceUrl(invoiceUrl);
            mediaService.uploadMedia(media);

        }
    }

    private static String generateInvoice(String orderId) {
        //generate invoiceUrl uploads it in s3 and return s3 url
        String invoiceUrl = null;
        return invoiceUrl;
    }
}
