package com.koma.meidacategory.data;

import com.koma.meidacategory.data.model.AudioFile;
import com.koma.meidacategory.data.model.ImageFile;
import com.koma.meidacategory.data.model.VideoFile;
import com.koma.meidacategory.util.MediaCategoryUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by koma on 2017/1/14.
 */

public class LocalDataSource implements MediaDataSource {
    @Override
    public Observable<List<AudioFile>> getAudioFiles() {
        return Observable.create(new Observable.OnSubscribe<List<AudioFile>>() {
            @Override
            public void call(Subscriber<? super List<AudioFile>> subscriber) {
                subscriber.onNext(MediaCategoryUtils.getAudioFiles());
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<VideoFile>> getVideoFiles() {
        return Observable.create(new Observable.OnSubscribe<List<VideoFile>>() {
            @Override
            public void call(Subscriber<? super List<VideoFile>> subscriber) {
                subscriber.onNext(MediaCategoryUtils.getVideoFiles());
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<ImageFile>> getImageFiles() {
        return Observable.create(new Observable.OnSubscribe<List<ImageFile>>() {
            @Override
            public void call(Subscriber<? super List<ImageFile>> subscriber) {
                subscriber.onNext(MediaCategoryUtils.getImageFiles());
                subscriber.onCompleted();
            }
        });
    }
}
