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

Then, add the Tapsbook SDK as a project dependency. Current SDK should be downloaded and unzipped to the library-release folder.

```
dependencies {
    compile project(':library-release')
}
```

Add the following code to initialize the Tapsbook SDK.

```
//Product configuration options. coming soon.
Config config = new Config("yoursite.tapsbook.com");
```

Finally, invoke the Tapsbook SDK from your application using one of the following methods.

```
TapsbookSDK.launchTapsbook(this, assets);	// Show the photobook builder UI

```

### Other Config options

Before calling `TapsbookSDK.launchTapsbook` you can further customize your configuration.
These options allows you to personalize the app experience.

* Photo count per page from Auto-generated book


### Theming

Tapsbook uses own theme rather than inheriting the theme of the hosting app.
