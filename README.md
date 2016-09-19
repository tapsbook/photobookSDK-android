## Tapsbook Android SDK

The Tapsbook Android SDK allows you to easily integrate a native photo book creation and checkout (optional) experience directly in your Android app. Our photo book solution offers a quick and simple experience for your app users to preserve memories and showcase their favorite photos in a beautiful way while enabling you to maximize your monetization. The SDK provides three ready-to-use components to help you integrate our photo book solution within an hour.

1. Simple yet powerful photo book tool for your users to quickly create photo book in a few minutes
2. Beautiful, fully customizable photo book page templates featuring your own branding/logo
3. Full-featured, frictionless eCommerce checkout workflow for processing payments via various payment gateways (Stripe, Alipay or WeChat etc)

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

### Integration

Integrating TapsbookSDK to your android project is as simple as 1-2-3

1. Feed the photos as Photo Asset to SDK
2. Show the photo book editor in the SDK to let user create a photo book
3. Take the SDK generated JSON page object or SDK generated page images for production.

The following code snippet shows how an Android app can add the photo book functions
````
ArrayList<Asset> assets = new ArrayList<>();
for (String photoLocalPath: myPhotos) {
    Asset photoAsset = new Asset();
    photoAsset.originPath = photoLocalPath;
    assets.add(photoAsset)
}
TapsbookSDK.launchTapsbook(currentActivity, assets);
````

### Configuration

TapsbookSDK provides lots of config options so you can easily configure what kind of books you want your users to build by setting the following global variable

````
TapsbookSDK.config.generate.maxNumberOfPages = 40;//set max pages
TapsbookSDK.config.generate.minNumberOfPages = 20;//set min pages
TapsbookSDK.config.generate.generateGivenSizeImages = true;//set generate the 800x400 image
TapsbookSDK.config.generate.isStartPageOnLeft = true; //set album start from left
TapsbookSDK.config.generate.kTBProductPreferredTheme = 200; //set product theme
````

### Customizing the look and feel

Tapsbook uses its own theme rather than inheriting the theme of the hosting app, the built in default theme is a neutural color scheme so that it can blend into your app very easily. If you prefer to customize the SDK look and feel, we offer services to help you to do that.

### CRUD operations to the created books

TapsbookSDK provides a singleton class `AlbumManager` let you easily implement Read, Update and Delete operation to existing books in your app. You can create a view that use the following methods to retrieve the list of all albums, then render them as a list view which showing the album covers (all these metadata are accessible through the Album object).

```
//get saved album
AlbumManager.getInstance().getSavedAlbums()

//delete currentAlbum,can delete by album id only
AlbumManager.getInstance().deleteCurrentAlbum(String albumId)

//get currentAlbum
AlbumManager.getInstance().getCurrentAlbum()

```

### Documentation
The full JavaDoc of the TapsbookSDK android version can be found here: http://tapsbook.com/doc-android/index.html?com/tapsbook/sdk/TapsbookSDK.html


### Backend and Fulfillment options

Two options:

1. Use TapsbookSDK built in backend and integrated vendors
2. Use your own print workflow

TapsbookSDK has built its world wide network of print partners serving AP, Europe and North America markets, and this will be your quickest option to create a turnkey photo merchandising solution.. In the default mode, the SDK connects to the backend system via REST API to manage the product SKUs and photo book printing workflow by working with our photo book print partners and shipping service providers to offer you 

To get started using the first option, you will need to:

1. Sign up for a free tapsBook developer account: Go to http://dashboard.tapsbook.com to sign up.(invitation only, shoot us an email support@tapsbook.com)
2. Register your app and get an app key.

*What if you have your own print workflow and print facility?* No worries! The key design philosophy of TapsbookSDK is OPEN! TapsbookSDK can be easily integrated with exiting modern printing workflow quickly. TapsbookSDK allows many top print companies to create their own branded photo book mobile apps that connets to their own fulfillment capabilities. 

### Other platforms

TapsbookSDK also has its iOS sibling, [TapsbookSDK for iOS](https://github.com/tapsbook/photobookSDK-iOS), same cool stuff.

### Contact us

Our team is based in Raleigh, NC and office in Beijing and Xian in China. We love to hear from you, drop us an email support@tapsbook.com


