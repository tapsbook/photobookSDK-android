package com.tapsbook.photobooksdk_android.Model;

/**
 * Created by yaohu on 15/7/20.
 */
public class StoreOrderLineItem {

    public Integer  serverID;
    public Integer  variantId; // ProductId
    public Integer  orderId;
    public Integer  quantity;
    public double  price;
    public String  currency;
    public double  costPrice;
    public double  adjustmentTotal;
    public double  additionalTaxTotal;
    public double  promoTotal;
    public double  includedTaxTotal;
    public double  preTaxAmount;
    public String  createdAt;
    public String  updatedAt;
    public StoreProduct product;

}
