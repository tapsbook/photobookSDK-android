package com.tapsbook.photobooksdk_android.Manager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tapsbook.photobooksdk_android.Base.AbsBaseActivity;
import com.tapsbook.photobooksdk_android.Model.StoreCustomer;
import com.tapsbook.photobooksdk_android.Model.StoreOrder;

import org.apache.http.Header;

/**
 * Created by yaohu on 15/7/20.
 */
public class PhotobookStoreServer {

    private static boolean isdebug=true;

    public final  static String kTBStoreAPIBaseURLProduction = "https://dashboard.tapsbook.com"; //"http://127.0.0.1:3000";
    public final  static String kTBStoreAPIBaseURLProduction_CN = "https://dashboard.shiyi.co"; //"http://127.0.0.1:3000";
    public final  static String kTBStoreAPIBaseURLTest = "http://search.tapsbook.com"; //"http://127.0.0.1:3000";

    public final  static String BASEURL= isdebug?kTBStoreAPIBaseURLTest:kTBStoreAPIBaseURLProduction_CN;

    public final  static String kTBStoreGetOrder = "api/v1/orders/show";
    public final  static String kTBStorePostOrder = "api/v1/orders";
    public final  static String kTBStorePutOrder = "api/v1/orders";
    public final  static String kTBStorePostLineItems = "api/v1/orders/%zd/line_items";
    public final  static String kTBStorePutLineItems = "api/v1/orders/%zd/line_items";
    public final  static String kTBStoreGetProduct = "api/v1/products/show";
    public final  static String kTBStoreGetProductList = "api/v1/products";
    public final  static String kTBStoreGetCoupon = "api/v1/promotions";
    public final  static String kTBStorePostPayment = "api/v1/payments/charge";
    public final  static String kTBStorePostAlipaySign = "api/v1/payments/alipay/sign";
    public final  static String kTBStoreAlipayNotifyURL = "api/v1/payments/alipay_notify";
    public final  static String kTBStoreGetActivePromotion = "api/v1/promo_messages";
    public final  static String  kTBStorePreorder = "api/v1/preorder";

    private AsyncHttpClient client;
    private AbsBaseActivity absBaseActivity;

    private static  PhotobookStoreServer defaultInstance;

    public PhotobookStoreServer(AbsBaseActivity absBaseActivity) {
        this.absBaseActivity = absBaseActivity;
    }

    /** Convenience singleton for apps using a process-wide EventBus instance. */
    public static PhotobookStoreServer getDefault(AbsBaseActivity absBaseActivity) {
        if (defaultInstance == null) {
            synchronized (PhotobookStoreServer.class) {
                if (defaultInstance == null) {
                    defaultInstance = new PhotobookStoreServer(absBaseActivity);
                }
            }
        }
        return defaultInstance;
    }

    public AsyncHttpClient getClient() {
        if(client==null){
            client=new AsyncHttpClient();
        }
        return client;
    }

    public void putOrder(StoreOrder order,StoreCustomer customer){

    }

    public void getProductOfProductID(Integer productID){

    }

    public void getActivePromotionMessage(){

    }

    public void getCouponOfCounponCode(String code){

    }

    public void postPaymentWithCardToken(String cardToken,StoreCustomer customer){

    }

    public void postOrder(StoreOrder order,StoreCustomer customer,String customerId,String stripToken,String paymentType,String promoCode,double subtotalPrice,double totalPrice,double shippingCost,double tax,String currency){

    }

    public void alipaySignOrderString(String string){

    }

    public void preorderWithAlbum(){

    }

    class ResponseListener extends TextHttpResponseHandler{

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {

        }
    }
}
