package com.tapsbook.photobooksdk_android;

import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tapsbook.sdk.TapsbookSDK;
import com.tapsbook.sdk.TapsbookSDKCallback;
import com.tapsbook.sdk.services.domain.LineItem;
import com.tapsbook.sdk.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class App extends MultiDexApplication implements TapsbookSDKCallback {

    private static App instance;
    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TapsbookSDK.initialize("REPLACE_ME", this);
        instance = this;
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

    @Override
    public void complete(String s, LineItem lineItem, List<String> list) {
        Toast.makeText(this, "saved /mnt/sdcard/Tapsbook/album.json", Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        String json = gson.toJson(lineItem);
        byte[] txt = json.getBytes();

        try {
            File path = new File("/mnt/sdcard/Tapsbook");
            File f = new File("/mnt/sdcard/Tapsbook/album.json");
            if (!path.exists()) {
                path.mkdirs();
            }
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStream os = new FileOutputStream(f);
            os.write(txt);
            os.flush();
            os.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}