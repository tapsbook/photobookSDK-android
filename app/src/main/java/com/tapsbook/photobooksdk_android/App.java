package com.tapsbook.photobooksdk_android;

import android.app.Application;

import com.stripe.Stripe;
import com.tapsbook.sdk.TapsbookSDK;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stripe.apiKey = getResources().getString(R.string.stripeKey);
        TapsbookSDK.initialize("REPLACE_ME", this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        TapsbookSDK.deinitialize();
    }
}
