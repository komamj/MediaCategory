package com.koma.meidacategory.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koma.meidacategory.R;

/**
 * Created by koma on 2017/1/15.
 */

public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = BaseAdapter.class.getSimpleName();

    private Context mContext;

    public BaseAdapter(Context context) {
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view, null);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
