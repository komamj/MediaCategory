package com.koma.meidacategory.data;

import com.koma.meidacategory.data.model.AudioFile;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by koma on 2017/1/14.
 */

public interface MediaDataSource {
    Observable<ArrayList<AudioFile>> getAudioFiles();
}
