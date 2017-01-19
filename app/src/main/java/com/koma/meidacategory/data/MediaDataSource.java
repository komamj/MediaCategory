package com.koma.meidacategory.data;

import com.koma.meidacategory.data.model.AudioFile;
import com.koma.meidacategory.data.model.ImageFile;
import com.koma.meidacategory.data.model.VideoFile;

import java.util.List;

import rx.Observable;

/**
 * Created by koma on 2017/1/14.
 */

public interface MediaDataSource {
    Observable<List<AudioFile>> getAudioFiles();

    Observable<List<VideoFile>> getVideoFiles();

    Observable<List<ImageFile>> getImageFiles();
}
