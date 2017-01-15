package com.koma.meidacategory.data;

import com.koma.meidacategory.data.model.AudioFile;
import com.koma.meidacategory.util.LogUtils;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by koma on 2017/1/14.
 */

public class MediaRepository implements MediaDataSource{
    private static final String TAG = MediaRepository.class.getSimpleName();
    private static MediaRepository sRespository;
    private LocalDataSource mLocalDataSource;
    private MediaRepository(){
        mLocalDataSource = new LocalDataSource();
    }
    public synchronized static MediaRepository getInstance(){
        if (sRespository == null) {
            synchronized (MediaRepository.class) {
                if (sRespository == null) {
                    sRespository = new MediaRepository();
                }
            }
        }
        return sRespository;
    }

    @Override
    public Observable<ArrayList<AudioFile>> getAudioFiles() {
        LogUtils.i(TAG,"getAudioFiles");
        return mLocalDataSource.getAudioFiles();
    }
}