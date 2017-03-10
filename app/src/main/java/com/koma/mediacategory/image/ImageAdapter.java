package com.koma.mediacategory.image;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.koma.mediacategory.R;
import com.koma.mediacategory.data.model.ImageFile;
import com.koma.mediacategory.util.Constants;
import com.koma.mediacategory.widget.SquareImageView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by koma on 1/18/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private static final String TAG = ImageAdapter.class.getSimpleName();
    private Context mContext;
    private List<ImageFile> mData;

    public ImageAdapter(Context context, List<ImageFile> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_item_view, null);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.mTitle.setText(mData.get(position).getTitle());
        Glide.with(mContext).load(Uri.fromFile(new File(mData.get(position).getFilePath())))
                .placeholder(R.mipmap.default_image_thumbnail)
                .crossFade(Constants.ANIMATION_TIME)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_video)
        SquareImageView mImageView;

        @BindView(R.id.tv_title)
        TextView mTitle;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
