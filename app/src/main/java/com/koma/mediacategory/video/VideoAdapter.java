package com.koma.mediacategory.video;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.koma.mediacategory.R;
import com.koma.mediacategory.data.model.VideoFile;
import com.koma.mediacategory.util.Constants;
import com.koma.mediacategory.util.Utils;
import com.koma.mediacategory.widget.SquareImageView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by koma on 1/17/17.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private static final String TAG = VideoAdapter.class.getSimpleName();
    private Context mContext;
    private List<VideoFile> mData;

    public VideoAdapter(Context context, List<VideoFile> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_item_view, null);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.mTitle.setText(mData.get(position).getTitle());
        Glide.with(mContext).load(Uri.fromFile(new File(mData.get(position).getFilePath())))
                .placeholder(R.mipmap.default_video_thumbnail)
                .crossFade(Constants.ANIMATION_TIME)
                .into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_video)
        SquareImageView mImageView;

        @BindView(R.id.tv_title)
        TextView mTitle;

        public VideoViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(Constants.PLAYER_PACKAGE_NAME,
                    Constants.PLAYER_CLASS_NAME));
            intent.setData(Utils.getVideoUriById(mData.get(position).getId()));
            mContext.startActivity(intent);
        }
    }
}
