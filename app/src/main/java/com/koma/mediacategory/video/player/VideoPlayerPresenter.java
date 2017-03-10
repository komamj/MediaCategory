package com.koma.mediacategory.video.player;


import com.koma.mediacategory.util.LogUtils;

/**
 * Created by koma on 2/4/17.
 */

public class VideoPlayerPresenter implements VideoPlayerContract.Presenter {
    private static final String TAG = VideoPlayerPresenter.class.getSimpleName();

    private VideoPlayerContract.View mView;

    public VideoPlayerPresenter(VideoPlayerContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        LogUtils.i(TAG, "subscribe");
        preparePlayer();
    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG, "unSubscribe");
    }

    @Override
    public void preparePlayer() {
        if (mView != null) {
            mView.preparePlayer();
        }
    }

    @Override
    public void doStartOrPause() {
        if (mView != null) {
            mView.doStartOrPause();
        }
    }
}
