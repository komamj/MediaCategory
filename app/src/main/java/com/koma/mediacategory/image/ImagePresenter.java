package com.koma.mediacategory.image;

import android.support.annotation.NonNull;

import com.koma.mediacategory.data.MediaRepository;
import com.koma.mediacategory.data.model.ImageFile;
import com.koma.mediacategory.util.LogUtils;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by koma on 1/18/17.
 */

public class ImagePresenter implements ImageContract.Presenter {
    private static final String TAG = ImagePresenter.class.getSimpleName();

    @NonNull
    private ImageContract.View mView;
    @NonNull
    private MediaRepository mRepository;

    private CompositeSubscription mSubscriptions;
    private Subscription mImageSubscription;

    public ImagePresenter(ImageContract.View view, MediaRepository repository) {
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
    public void getImageFiles() {
        LogUtils.i(TAG, "getVideoFiles");
        if (mImageSubscription != null) {
            mSubscriptions.remove(mImageSubscription);
        }
        mImageSubscription = mRepository.getImageFiles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ImageFile>>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i(TAG, "getVideoFiles onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i(TAG, "getVideoFiles onError : " + e.toString());
                    }

                    @Override
                    public void onNext(List<ImageFile> videoFiles) {
                        if (videoFiles != null) {
                            if (mView != null) {
                                mView.refreshAdapter(videoFiles);
                            }
                        }
                    }
                });
        mSubscriptions.add(mImageSubscription);
    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG, "unSubscribe");
        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }
    }
}
