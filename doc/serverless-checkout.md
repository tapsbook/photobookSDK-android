# SDK Integration with Private Backend

For approved partners, Tapsbook SDK provides a private serverless deployment option that allows the developer to use its own private backend instead of the shared Tapsbook backend.

The diagram below shows the steps your TapsbookSDK-enabled app should perform for a complete order creation and order checkout process.

![image](https://cloud.githubusercontent.com/assets/842068/18487269/9331a440-79c2-11e6-9d46-e0afb132fc18.png)

In this private deployment option, (number here refers to the diagram above) 

1. Your TapsbookSDK enabled app will connect to your own server providing APIs to get product spec and feed that to a customized TapsbookSDK. 
2. The TapsbookSDK behaves as a black box to provide the photo book editing user experience. After user is done editing, the TapsbookSDK saves the users' photo book data to a SDK provied local DB. 
3. Invoke the checkout flow which will let TapsbookSDK generates the order output JSON and page preview data on the mobile device, you can then freely pass the order output JSON to your product server. It is necessary that your application upload the user selected photo to a cloud storage for later order JPG rendering at your server side.
4. Your product server should include eCommerce functions that handles payment processing,and optionally notify the user whether the order is successful.
5. After the payment successful acknolegement from the bank, you can safely start your own production workflow. Your backend should load the order JSON with reference to the uploaded user photos and render the final order output as page JPGs. Once this complete, feed the final output JPGs to your own printing ERP for manufacturing. It is a required step that your backend also send an order notification to Tapsbook server for billing syncrhonization. 

## Integration details
You should follow the integration steps as documented for [TBSDKAlbumManager](http://tapsbook.com/doc/Classes/TBSDKAlbumManager.html), e.g. prepare the TBImages etc, plus also perform the following additional preparation steps.

During step 1, because your backend will manage the product SKU, your app should implement your product picker to let user pick a product (SKU) and this SKU needs also be provisioned in the TapsbookSDK's database through a customization step.  Finally you pass this SKU and other product custoimzation as an albumOption to SDK  
````
TapsbookSDK.Option option = new TapsbookSDK.Option();
option.setProductTheme(200);// set the given product theme id
option.setProductSku("1300");// set the given product sku
option.setProductMaxPageCount(30);// set max page count of this album
option.setProductMinPageCount(20);// set min page count of this album
option.setStartPageFromLeft(isStartFromLeft);// set album start direction
option.setPreferredUiDirectionIsRTL(isRTL);// set ui direction
option.setNeedAlbumTitle(isNeedAlbumTitle);// set whether force user to add album title
option.setUseExternalCheckout(useExternalCheckout);// set whether use your own checkout
````

During step 3. You should process the launch method callback which include the page thumbnail file path
````
TapsbookSDK.launchTapsbook(@NotNull Activity activity, @NotNull List<Asset> assets, @Nullable TapsbookSDKCallback callback, @Nullable Option option)
````
once this is complete, call the following method to start the final checkout. This async method will return the a dictonary containing the data for you to manufacture the photo book. The dictionary includes a string in “album_JSON” and an array of page image thumbnails in “album_page_thumbnails”. Your app can then sends these data to your backend for final processing.
````
/**
 *
 * @param key the order number(if necessary)
 * @param item service needed content
 * @param imagePaths array of page image thumbnails
 */
void complete(String key, LineItem item, List<String> imagePaths);
````

During step 5. Your backend server should load the order JSON and uploaded images to process the final print output for final production.  The JSON format sample and reference can be found below.
- [JSON sample](https://github.com/tapsbook/photobookSDK-iOS/blob/master/Doc/sample_album.json)
- [JSON reference](https://github.com/tapsbook/photobookSDK-iOS/blob/master/Doc/sample_json_info.md)

This step should also include a REST API call to Tapsbook server to notify that the order transaction has been successfuly completed and this record will be used for the billing synchronization. This is a non-blocking API call and does not stop your normal workflow.
````
[POST] https://dashboard.tapsbook.com/api/v1/orders
````
Here is the content for the POST request.
````
{
    "order_type" : "notify",
    “api_key":"YOUR_API_KEY ",
    "external_order_id":"YOUR ORDER ID",
    "currency": "USD",
    "total_price": "98.00",
    "order_date": "2016-08-02T12:20:21Z",
    "customer": {
        "address": {
            "phone": "CUSTOMER_PHONE",
            "address1": "",
            "email": "CUSTOMER_EMAIL",
            "state_id": "99",
            "country_id": "48",
        }
    }
}
````
