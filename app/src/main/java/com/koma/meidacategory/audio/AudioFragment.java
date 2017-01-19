package com.koma.meidacategory.audio;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koma.meidacategory.R;
import com.koma.meidacategory.base.BaseFragment;
import com.koma.meidacategory.data.model.AudioFile;
import com.koma.meidacategory.util.Constants;
import com.koma.meidacategory.util.LogUtils;
import com.koma.meidacategory.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by koma on 2017/1/14.
 */

public class AudioFragment extends BaseFragment implements AudioContract.View {
    private static final String TAG = AudioFragment.class.getSimpleName();
    private AudioContract.Presenter mPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private AudioAdapter mAdapter;

    private List<AudioFile> mData;

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

    @Override
    public void onViewCreated(View view, Bundle savedInsatnceState) {
        super.onViewCreated(view, savedInsatnceState);
        LogUtils.i(TAG, "onViewCreated");
        init();
    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mData = new ArrayList<>();

        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext,
                layoutManager.getOrientation());

        mAdapter = new AudioAdapter(mContext, mData);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(mAdapter);
        if (mPresenter != null) {
            mPresenter.subscribe();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i(TAG, "onStart");
        mContext.getContentResolver().registerContentObserver(Constants.AUDIO_URI, true, mAudioObserver);
    }

    private final ContentObserver mAudioObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            LogUtils.i(TAG, "Audio uri change so refresh");
            if (mPresenter != null) {
                mPresenter.getAudioFiles();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(TAG, "onResume");
        if (mPresenter != null) {
            mPresenter.getAudioFiles();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i(TAG, "onPause");
        if (mAudioObserver != null) {
            mContext.getContentResolver().unregisterContentObserver(mAudioObserver);
        }
    }

    @Override
    public void refreshAdapter(List<AudioFile> audioFiles) {
        LogUtils.i(TAG, "refershAdapter");
        if (mData != null) {
            mData.clear();
            mData.addAll(audioFiles);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.i(TAG, "onDestroyView");
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }
}
