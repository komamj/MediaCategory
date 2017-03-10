package com.koma.mediacategory.video;

import android.support.annotation.NonNull;

import com.koma.mediacategory.data.MediaRepository;
import com.koma.mediacategory.data.model.VideoFile;
import com.koma.mediacategory.util.LogUtils;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by koma on 1/17/17.
 */

public class VideoPresenter implements VideoContract.Presenter {
    private static final String TAG = VideoPresenter.class.getSimpleName();

    @NonNull
    private VideoContract.View mView;
    @NonNull
    private MediaRepository mRepository;

    private CompositeSubscription mSubscriptions;
    private Subscription mVideoSubscription;

    public VideoPresenter(VideoContract.View view, MediaRepository repository) {
        this.mView = view;
        this.mView.setPresenter(this);
        this.mRepository = repository;
    }

    @Override
    public void subscribe() {
        LogUtils.i(TAG, "subscribe");
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void getVideoFiles() {
        LogUtils.i(TAG, "getVideoFiles");
        if (mVideoSubscription != null) {
            mSubscriptions.remove(mVideoSubscription);
        }
        mVideoSubscription = mRepository.getVideoFiles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<VideoFile>>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i(TAG, "getVideoFiles onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i(TAG, "getVideoFiles onError : " + e.toString());
                    }

                    @Override
                    public void onNext(List<VideoFile> videoFiles) {
                        if (videoFiles != null) {
                            if (mView != null) {
                                mView.refreshAdapter(videoFiles);
                            }
                        }
                    }
                });
        mSubscriptions.add(mVideoSubscription);
    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG, "unSubscribe");
        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }
    }
}
