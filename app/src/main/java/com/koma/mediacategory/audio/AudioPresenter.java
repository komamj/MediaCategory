package com.koma.mediacategory.audio;

import com.koma.mediacategory.data.MediaRepository;
import com.koma.mediacategory.data.model.AudioFile;
import com.koma.mediacategory.util.LogUtils;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by koma on 2017/1/14.
 */

public class AudioPresenter implements AudioContract.Presenter {
    private static final String TAG = AudioPresenter.class.getSimpleName();
    private AudioContract.View mView;
    private CompositeSubscription mSubscriptions;
    private Subscription mAudioFilesSubsription;
    private MediaRepository mRepository;

    public AudioPresenter(AudioContract.View view, MediaRepository respository) {
        mView = view;
        mView.setPresenter(this);
        mRepository = respository;
    }

    @Override
    public void subscribe() {
        LogUtils.i(TAG, "subsribe");
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG, "unSubscribe");
        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }
    }

    @Override
    public void getAudioFiles() {
        LogUtils.i(TAG, "getAudioFiles");
        if (mAudioFilesSubsription != null) {
            mSubscriptions.remove(mAudioFilesSubsription);
        }
        mAudioFilesSubsription = mRepository.getAudioFiles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<AudioFile>>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i(TAG, "getAudioFiles onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        LogUtils.i(TAG, "getAudioFiles onError :" + e.toString());
                    }

                    @Override
                    public void onNext(List<AudioFile> audioFiles) {
                        if (audioFiles != null) {
                            if (mView != null) {
                                mView.refreshAdapter(audioFiles);
                            }
                        }
                    }
                });
        mSubscriptions.add(mAudioFilesSubsription);
    }
}
