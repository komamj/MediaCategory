package com.koma.mediacategory.audio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.koma.mediacategory.R;
import com.koma.mediacategory.data.model.AudioFile;
import com.koma.mediacategory.util.Constants;
import com.koma.mediacategory.util.MediaCategoryUtils;
import com.koma.mediacategory.widget.SquareImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by koma on 1/17/17.
 */

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioViewHolder> {
    private static final String TAG = AudioAdapter.class.getSimpleName();

    private Context mContext;
    private List<AudioFile> mData;

    public AudioAdapter(Context context, List<AudioFile> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public AudioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.audio_item_view, null);
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AudioViewHolder holder, int position) {
        Glide.with(mContext).load(MediaCategoryUtils
                .getAlbumArtUri(mData.get(position).getAlbumId()))
                .crossFade(Constants.ANIMATION_TIME)
                .placeholder(R.mipmap.audio_default).into(holder.mAlbum);
        holder.mTitle.setText(mData.get(position).getTitle());
        holder.mArtist.setText(mData.get(position).getArtist());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class AudioViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_album)
        SquareImageView mAlbum;

        @BindView(R.id.tv_title)
        TextView mTitle;
        @BindView(R.id.tv_artist)
        TextView mArtist;

        public AudioViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
