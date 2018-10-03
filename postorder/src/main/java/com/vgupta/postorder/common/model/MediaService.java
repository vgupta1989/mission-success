package com.vgupta.postorder.common.model;

import com.vgupta.postorder.common.model.Media;
import com.vgupta.postorder.invoice.Invoice;

public interface MediaService {
    //UploadInvoice to Media Service
    public String uploadMedia(String mediaId, Invoice invoice);
    public Media getMedia(String orderId);

}
