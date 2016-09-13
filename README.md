## Tapsbook Android SDK

The Tapsbook Android SDK allows you to easily integrate a native photo book creation and checkout (optional) experience directly in your Android app. Our photo book solution offers a quick and simple experience for your app users to preserve memories and showcase their favorite photos in a beautiful way while enabling you to maximize your monetization. The SDK provides three ready-to-use components to help you integrate our photo book solution within an hour.

1. Simple yet powerful photo book tool for your users to quickly create photo book in a few minutes
2. Beautiful, fully customizable photo book page templates featuring your own branding/logo
3. Full-featured, frictionless eCommerce checkout workflow for processing payments via various payment gateways (Stripe, Alipay or WeChat etc)

The SDK is integrated with the backend system via REST API to manage the photo book printing workflow by working with our photo book print partners and shipping service providers to offer you a turnkey photo merchandising solution.

To get started, you will need to:

1. Sign up for a free tapsBook developer account: Go to http://search.tapsbook.com to sign up.
2. Register your app and get an app key.

### Installation

The best way to install Tapsbook SDK for Android is to use gradle and jcenter. First add jcenter to your list of Maven repositories, if needed.

```
allprojects {
    repositories {
        jcenter()
    }
}
```

Then, add the Tapsbook SDK as a project dependency. the SDK binary can be found under library-release folder (included in this repo) you need to include it as a dependency in your project.

```
dependencies {
    compile project(':library-release')                 //core SDK
    compile project(':opencv-release')                  //for page rendering
    compile 'com.qiniu:qiniu-android-sdk:7.0.6'         //for image upload
    compile('com.crashlytics.sdk.android:crashlytics:2.5.2@aar') { //for crashlytics
            transitive = true;
    }
    compile 'com.android.support:recyclerview-v7:21.0.0'
    compile 'com.jakewharton:butterknife:6.1.0'
    apt 'com.raizlabs.android:DBFlow-Compiler:2.2.1'
    compile 'com.raizlabs.android:DBFlow-Core:2.2.1'
    compile 'com.raizlabs.android:DBFlow:2.2.1'
    compile 'com.github.bumptech.glide:glide:3.6.1'
    compile 'com.google.android.gms:play-services:7.5.0'

}
```

Add the following code to initialize the Tapsbook SDK.

```
import com.tapsbook.sdk.TapsbookSDK;
import com.tapsbook.sdk.photos.Asset;

//Product configuration options
TapsbookSDK.config.generate.maxNumberOfPages = 40;//set max pages
TapsbookSDK.config.generate.minNumberOfPages = 20;//set min pages
TapsbookSDK.config.generate.generateGivenSizeImages = true;//set generate the 800x400 image
TapsbookSDK.config.generate.isStartPageOnLeft = true; //set album start from left
TapsbookSDK.config.generate.kTBProductPreferredTheme = 200; //set product theme
...
more options please read our doc
```

Finally, invoke the Tapsbook SDK from your application using one of the following methods.

```
//initialize a set of photos to be included in the book.
ArrayList<Asset> assets = new ArrayList<>();
Asset asset = new Asset();
asset.originPath = "your local jpg file path";
if use online images,you should use like this:
asset.urlPath = "your online jpg file path";
assets.add(asset)

// Create book and Show the photobook UI
TapsbookSDK.launchTapsbook(this, assets);
TapsbookSDK.launchTapsbook(this, assets, new TapsbookSDKCallback() {
    /**
     *
     * @param key the key
     * @param item service needed content
     * @param imagePaths generated images path list if set true
     */
    @Override
    public void complete(String key, LineItem lineItem, List<String> imagePaths) {
        //now the order is complete, launch own checkout activity
        ....
    }
});

```

### Other Config options

Before calling `TapsbookSDK.launchTapsbook` you can further customize your configuration.
These options allows you to personalize the app experience.

* Photo count per page from Auto-generated book


### Theming

Tapsbook uses own theme rather than inheriting the theme of the hosting app.


### Doc
* To operate the album, please use `AlbumManager`,to call `AlbumManager.getInstance()`

```
//get saved album
AlbumManager.getInstance().getSavedAlbums()

//save currentAlbum
AlbumManager.getInstance().saveCurrentAlbum()

//delete currentAlbum,can delete by album id only
AlbumManager.getInstance().deleteCurrentAlbum(String albumId)

//get currentAlbum
AlbumManager.getInstance().getCurrentAlbum()

```

* To launch SDK

```
/**
 * Create a new album with photo specified as {@link Asset}
 *
 * @param context the Context used to launch activity
 * @param assets the array list of {@link Asset} to be used to pass the photo list path
 */
 public static void launchTapsbook(Context context, List<Asset> assets) {
    
/**
 * Open an existing album with a given albumID
 *
 * @param context the Context used to launch activity
 * @param albumId the album id of current album
 */
 public static void launchTapsbook(Context context, String albumId) {

/**
 * Open an existing album with a given albumID and have access to the callback when user completed the editing.
 *
 * @param context the Context used to launch activity
 * @param callback the callback when upload images complete
 * @param albumId the album id of current album
 * @param sku the sku of current product
 */
public static void launchTapsbook(@NotNull Context context,@NotNull TapsbookSDKCallback callback, @NotNull String albumId, @Nullable String sku) {

```


