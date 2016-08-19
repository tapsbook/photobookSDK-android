package com.tapsbook.photobooksdk_android;

import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tapsbook.sdk.AlbumManager;
import com.tapsbook.sdk.TapsbookSDK;
import com.tapsbook.sdk.TapsbookSDKCallback;
import com.tapsbook.sdk.model.Album;
import com.tapsbook.sdk.services.domain.LineItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class App extends MultiDexApplication implements TapsbookSDKCallback {
    private final static String ASSETS_ROOT_PATH = "file:///android_asset/";

    private static App instance;
    private Handler handler = new Handler();

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TapsbookSDK.initialize("REPLACE_ME", this);
        //this method will set display logo and print logo
        TapsbookSDK.setAppLogo(ASSETS_ROOT_PATH + "logo_display.png", "");
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
    public void complete(String s, LineItem lineItem, List<String> imagePaths) {
        if (null != imagePaths && imagePaths.size() != 0) {
            String path = imagePaths.get(0);
            File file = new File(path);
            if (file.exists()) {
                String parent = file.getParent();
                Toast.makeText(this, "images save in file " + parent, Toast.LENGTH_LONG).show();
            }
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                saveJson();
            }
        }).start();

    }

    private void saveJson() {
        Gson gson = new Gson();
        Album currentAlbum = AlbumManager.getInstance().getCurrentAlbum();
        String json = gson.toJson(currentAlbum);
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
        } finally {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(App.getInstance(), "saved /mnt/sdcard/Tapsbook/album.json", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}