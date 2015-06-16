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
    compile project(':library-release')
    compile 'com.android.support:recyclerview-v7:21.0.0'
    compile 'com.jakewharton:butterknife:6.1.0'
    compile 'com.squareup.picasso:picasso:2.5.2'
    apt 'com.raizlabs.android:DBFlow-Compiler:2.0.0'
    compile "com.raizlabs.android:DBFlow-Core:2.0.0"
    compile "com.raizlabs.android:DBFlow:2.0.0"
}
```

Add the following code to initialize the Tapsbook SDK.

```
import com.tapsbook.sdk.TapsbookSDK;
import com.tapsbook.sdk.photos.Asset;

//Product configuration options. coming soon.
//Config config = new Config("yoursite.tapsbook.com");
```

Finally, invoke the Tapsbook SDK from your application using one of the following methods.

```
//initialize a set of photos to be included in the book.
ArrayList<Asset> assets = new ArrayList<>();
Asset asset = new Asset();
asset.originPath = "your local jpg file path";
assets.add(asset)

// Create book and Show the photobook UI
TapsbookSDK.launchTapsbook(this, assets);	
```

### Other Config options

Before calling `TapsbookSDK.launchTapsbook` you can further customize your configuration.
These options allows you to personalize the app experience.

* Photo count per page from Auto-generated book


### Theming

Tapsbook uses own theme rather than inheriting the theme of the hosting app.
