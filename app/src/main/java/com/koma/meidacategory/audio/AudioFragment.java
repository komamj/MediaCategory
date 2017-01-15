package com.koma.meidacategory.audio;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koma.meidacategory.R;
import com.koma.meidacategory.base.BaseFragment;
import com.koma.meidacategory.util.LogUtils;

import butterknife.BindView;

/**
 * Created by koma on 2017/1/14.
 */

public class AudioFragment extends BaseFragment implements AudioContract.View {
    private static final String TAG = AudioFragment.class.getSimpleName();
    private AudioContract.Presenter mPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    public void setPresenter(AudioContract.Presenter presenter) {
        LogUtils.i(TAG, "setPresenter");
        mPresenter = presenter;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        return view;
    }
}
