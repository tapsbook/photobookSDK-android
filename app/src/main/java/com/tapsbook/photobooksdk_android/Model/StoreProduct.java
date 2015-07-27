package com.tapsbook.photobooksdk_android.Model;

import java.util.ArrayList;

/**
 * Created by yaohu on 15/7/20.
 */
public class StoreProduct {

    public int   serverID;

    public String  name;

    public String  tbDesc;

    public double price;

    public double pricePerPage;

    public String  currency;

    public String  shippingMethod;

    public double baseShippingPrice;

    public double shippingPricePerAdditionalItem;

    public ArrayList<StoreProductVariant>variants;
}
