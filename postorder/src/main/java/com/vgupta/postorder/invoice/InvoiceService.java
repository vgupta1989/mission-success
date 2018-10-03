package com.vgupta.postorder.invoice;

import com.vgupta.postorder.common.model.MediaService;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class InvoiceService {
    public static void main(){
        //Assuming This As Queueing System Client
        BlockingQueue<InvoiceEvent> eventQueue = new ArrayBlockingQueue<>(1000);
        consumer(eventQueue);
    }


    public static void consumer(BlockingQueue<InvoiceEvent> eventQueue){
        while(true){
            InvoiceEvent invoiceEvent = eventQueue.poll();
            String mediaId = invoiceEvent.getMediaId();
            String orderId = invoiceEvent.getOrderId();
            Invoice invoice =  generateInvoice(orderId);
            //Get MediaService Contract
            MediaService mediaService = null;
            mediaService.uploadMedia(mediaId, invoice);

        }
    }

    private static Invoice generateInvoice(String orderId) {
        return new Invoice();
    }
}
