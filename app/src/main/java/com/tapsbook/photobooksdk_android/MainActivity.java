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
            TapsbookSDK.Option option = new TapsbookSDK.Option();
            //now you can set the option for each album
            option.setProductTheme(200);// set the given product theme id
            option.setProductSku("1200");// set the given product sku
            option.setProductMaxPageCount(30);// set max page count of this album
            option.setProductMinPageCount(20);// set min page count of this album
            TapsbookSDK.launchTapsbook(this, assets, App.getInstance(), option);
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
