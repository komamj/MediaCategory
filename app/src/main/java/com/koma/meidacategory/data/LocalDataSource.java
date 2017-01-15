package com.koma.meidacategory.data;

import com.koma.meidacategory.data.model.AudioFile;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by koma on 2017/1/14.
 */

public class LocalDataSource implements MediaDataSource {
    @Override
    public Observable<ArrayList<AudioFile>> getAudioFiles() {
        return null;
    }
}
