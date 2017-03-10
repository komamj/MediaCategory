package com.koma.mediacategory.video;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koma.mediacategory.R;
import com.koma.mediacategory.base.BaseFragment;
import com.koma.mediacategory.data.model.VideoFile;
import com.koma.mediacategory.util.Constants;
import com.koma.mediacategory.util.LogUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by koma on 1/17/17.
 */

public class VideoFragment extends BaseFragment implements VideoContract.View {
    private static final String TAG = VideoFragment.class.getSimpleName();

    @NonNull
    private VideoContract.Presenter mPresenter;

    private List<VideoFile> mData;

    private VideoAdapter mAdapter;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.i(TAG, "onViewCreated");
        init();
    }

    private void init() {
        if (mPresenter != null) {
            mPresenter.subscribe();
        }

        mData = new ArrayList<>();

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, Constants.GRID_COUNT);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);

        mAdapter = new VideoAdapter(mContext, mData);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private final ContentObserver mVideoObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            mHandler.removeCallbacks(VideoFragment.this);
            mHandler.postDelayed(VideoFragment.this, REFRESH_TIME);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i(TAG, "onStart");
        mContext.getContentResolver().registerContentObserver(Constants.VIDEO_URI,
                true, mVideoObserver);
    }

    @Override
    public void onResume() {
        super.onResume();

        LogUtils.i(TAG, "onResume");

        if (mPresenter != null) {
            mPresenter.getVideoFiles();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        LogUtils.i(TAG, "onStop");

        if (mVideoObserver != null) {
            mContext.getContentResolver().unregisterContentObserver(mVideoObserver);
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

    @Override
    public void refreshAdapter(List<VideoFile> videoFiles) {
        LogUtils.i(TAG, "refreshAdapter");

        if (mData != null) {
            mData.clear();
            mData.addAll(videoFiles);

            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
        if (videoFiles == null || videoFiles.size() == 0) {
            showEmptyView();
        } else {
            hideEmptyView();
        }
    }

    @Override
    public void setPresenter(@NonNull VideoContract.Presenter presenter) {
        LogUtils.i(TAG, "setPresenter");

        mPresenter = presenter;
    }

    @Override
    public void run() {
        LogUtils.i(TAG, "Video uri change so refresh");

        if (mPresenter != null) {
            mPresenter.getVideoFiles();
        }
    }
}
