package com.tapsbook.photobooksdk_android;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tapsbook.sdk.TapsbookSDK;
import com.tapsbook.sdk.photos.Asset;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddPhotoActivity extends Activity {

    private List<String> usedPhotoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        initIntent();
    }

    private void initIntent() {
        if (getIntent() != null)
            //you can get used photos path list from edit view ,
            //developer can decide whether let user to select/show them again from your PhotoPicker
            usedPhotoList = getIntent().getStringArrayListExtra(TapsbookSDK.EXTRA_USED_PHOTO_LIST);
    }

    public void addPhoto(View v) {
        ArrayList<Asset> assetList = exportPhotos();
        Intent intent = new Intent();
        //In this step,developer must transform List<Asset> to SDK,like this, please follow this.
        intent.putParcelableArrayListExtra(TapsbookSDK.EXTRA_SELECTED_PHOTOS, assetList);
        setResult(RESULT_OK, intent);
        finish();
    }

    private ArrayList<Asset> exportPhotos() {
        ArrayList<Asset> assets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String file = getAssetsFiles()[i];
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
