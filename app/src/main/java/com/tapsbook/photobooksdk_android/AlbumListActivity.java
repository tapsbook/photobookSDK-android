package com.tapsbook.photobooksdk_android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tapsbook.sdk.AlbumManager;
import com.tapsbook.sdk.TapsbookSDK;
import com.tapsbook.sdk.model.AlbumInfo;

import java.util.List;

public class AlbumListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout tipLayout;
    private List<AlbumInfo> savedAlbums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);

        tipLayout = (LinearLayout) findViewById(R.id.tip_layout);
        recyclerView = (RecyclerView) findViewById(R.id.album_list);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layout);

        savedAlbums = AlbumManager.getInstance().getSavedAlbums();
        if (savedAlbums.size() > 0) {
            recyclerView.setAdapter(new MyAdapter());
        } else {
            tipLayout.setVisibility(View.VISIBLE);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_album_list, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final AlbumInfo albumInfo = savedAlbums.get(position);

            Bitmap bitmap = BitmapFactory.decodeFile(albumInfo.getHeaderCover());
            holder.ivCover.setImageBitmap(bitmap);
            holder.tvId.setText("Album Id: " + albumInfo.getId());
            holder.tvOrder.setText("Is Ordered: " + albumInfo.isOrdered());
            holder.tvNum.setText("Page Num: " + albumInfo.getPageNum());
            holder.rootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TapsbookSDK.launchTapsbook(AlbumListActivity.this, albumInfo.getId(), App.getInstance());
                }
            });
        }

        @Override
        public int getItemCount() {
            return savedAlbums == null ? 0 : savedAlbums.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout rootLayout;
            ImageView ivCover;
            TextView tvId;
            TextView tvOrder;
            TextView tvNum;

            public MyViewHolder(View itemView) {
                super(itemView);
                rootLayout = (LinearLayout) itemView.findViewById(R.id.root_layout);
                ivCover = (ImageView) itemView.findViewById(R.id.iv_cover);
                tvId = (TextView) itemView.findViewById(R.id.tv_id);
                tvOrder = (TextView) itemView.findViewById(R.id.tv_order);
                tvNum = (TextView) itemView.findViewById(R.id.tv_num);
            }
        }
    }
}
