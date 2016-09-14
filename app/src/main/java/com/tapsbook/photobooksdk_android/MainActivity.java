package com.tapsbook.photobooksdk_android;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tapsbook.sdk.TapsbookSDK;
import com.tapsbook.sdk.photos.Asset;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openBook(View view) {
        ArrayList<Asset> assets = exportPhotos();
        // set max page
        TapsbookSDK.config.generate.maxNumberOfPages = 40;
        // set min page
        TapsbookSDK.config.generate.minImagesPerPage = 20;
        // generate 800 * 400 image
        TapsbookSDK.config.generate.generateGivenSizeImages = true;
        loadTapsbookSDK(assets);
    }

    private ArrayList<Asset> exportPhotos() {
        ArrayList<Asset> assets = new ArrayList<>();
        for (String file : getAssetsFiles()) {
            if (file.endsWith("jpg")) {
                Asset asset = new Asset();
                asset.identifier = "your image id";
                asset.originPath = getAssetsImageCacheFilePath(file);
                asset.urlPath = "http://fake.me/" + System.currentTimeMillis() + ".jpg";
                assets.add(asset);
            }
        }
        return assets;
    }

    private void loadTapsbookSDK(ArrayList<Asset> assets) {
        if (assets.size() > 0) {
            TapsbookSDK.launchTapsbook(this, assets, App.getInstance(), null, null, null);
        } else {
            Toast.makeText(this, "Please make sure you have some favorite photos ", Toast.LENGTH_SHORT).show();
        }
    }

    private String getAssetsImageCacheFilePath(String file) {
        File f = new File(getCacheDir() + file);
        if (!f.exists()) {
            try {
                InputStream is = getAssets().open(file);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                FileOutputStream fos = new FileOutputStream(f);
                fos.write(buffer);
                fos.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return f.getPath();
    }

    private String[] getAssetsFiles() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {

        }
        return files;
    }
}
