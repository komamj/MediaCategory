package com.koma.meidacategory.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koma.meidacategory.R;
import com.koma.meidacategory.base.BaseFragment;
import com.koma.meidacategory.util.LogUtils;

/**
 * Created by koma on 1/17/17.
 */

public class VideoFragment extends BaseFragment {
    private static final String TAG = VideoFragment.class.getSimpleName();

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.i(TAG, "onViewCreated");
    }
}
