package com.koma.meidacategory.audio;

import android.database.ContentObserver;
import android.os.Handler;

import com.koma.meidacategory.util.LogUtils;

/**
 * Created by koma on 2017/1/14.
 */

public class AudioPresenter implements AudioContract.Presenter {
    private static final String TAG = AudioPresenter.class.getSimpleName();
    private AudioContract.View mView;

    public AudioPresenter(AudioContract.View view){
        mView = view;
        mView.setPresenter(this);
    }

    private final ContentObserver mAudioObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            LogUtils.i(TAG, "Audio uri change so refresh");
        }
    };
    @Override
    public void subscribe() {
        LogUtils.i(TAG,"subsribe");
    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG,"unSubscribe");
    }
}
