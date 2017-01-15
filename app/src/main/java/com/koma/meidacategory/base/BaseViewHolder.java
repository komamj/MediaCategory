package com.koma.meidacategory.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by koma on 2017/1/15.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = BaseViewHolder.class.getSimpleName();

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this.itemView);
    }
}
