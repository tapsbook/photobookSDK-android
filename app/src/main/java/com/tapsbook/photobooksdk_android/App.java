package com.tapsbook.photobooksdk_android;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.tapsbook.sdk.TapsbookSDK;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        TapsbookSDK.initialize("REPLACE_ME", this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        TapsbookSDK.deinitialize();
    }
}
