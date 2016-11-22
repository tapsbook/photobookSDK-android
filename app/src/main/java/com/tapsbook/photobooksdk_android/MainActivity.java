package com.tapsbook.photobooksdk_android;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.tapsbook.sdk.TapsbookSDK;
import com.tapsbook.sdk.photos.Asset;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    private long themeId = 200;
    private String sku = "1004";
    private boolean isStartFromLeft = true;
    private boolean isRTL = false;
    private boolean isNeedAlbumTitle = true;
    private boolean useExternalCheckout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(this);
        Switch sw1 = (Switch) findViewById(R.id.sw_1);
        Switch sw2 = (Switch) findViewById(R.id.sw_2);
        Switch sw3 = (Switch) findViewById(R.id.sw_3);
        Switch sw4 = (Switch) findViewById(R.id.sw_4);
        sw1.setOnCheckedChangeListener(this);
        sw2.setOnCheckedChangeListener(this);
        sw3.setOnCheckedChangeListener(this);
        sw4.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_1:
                themeId = 200;
                sku = "1004";
                break;
            case R.id.rb_2:
                themeId = 201;
                sku = "998";
                break;
            case R.id.rb_3:
                themeId = 202;
                sku = "999";
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sw_1:
                isStartFromLeft = isChecked;
                break;
            case R.id.sw_2:
                isRTL = isChecked;
                break;
            case R.id.sw_3:
                isNeedAlbumTitle = isChecked;
                break;
            case R.id.sw_4:
                useExternalCheckout = isChecked;
                break;
        }
    }

    public void showAllAlbum(View view) {
        startActivity(new Intent(this, AlbumListActivity.class));
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
            option.setProductTheme(themeId);// set the given product theme id
            option.setProductSku(sku);// set the given product sku
            option.setStartPageFromLeft(isStartFromLeft);// set album start direction
            option.setPreferredUiDirectionIsRTL(isRTL);// set ui direction
            option.setNeedAlbumTitle(isNeedAlbumTitle);// set whether force user to add album title
            option.setUseExternalCheckout(useExternalCheckout);// set whether use your own checkout
            option.setProductMaxPageCount(30);// set max page count of this album
            option.setProductMinPageCount(20);// set min page count of this album
            TapsbookSDK.launchTapsbook(MainActivity.this, assets, App.getInstance(), option);
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
