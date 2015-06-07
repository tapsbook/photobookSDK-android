package com.tapsbook.photobooksdk_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tapsbook.sdk.TapsbookSDK;
import com.tapsbook.sdk.photos.Asset;

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
        for (int i = 1; i < 23; i++) {
            Asset asset = new Asset();
            asset.originPath = "file:///android_asset/"+i+".jpg";
            assets.add(asset);
        }
        return assets;
    }

    private void loadTapsbookSDK(ArrayList<Asset> assets) {
        if (assets.size() > 0) {
            TapsbookSDK.launchTapsbook(this, assets);
        } else {
            Toast.makeText(this, "Please make sure you have some favorite photos ", Toast.LENGTH_SHORT).show();
        }
    }
}
