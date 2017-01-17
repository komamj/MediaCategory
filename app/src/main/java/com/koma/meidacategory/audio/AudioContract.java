package com.koma.meidacategory.audio;

import com.koma.meidacategory.base.BasePresenter;
import com.koma.meidacategory.base.BaseView;
import com.koma.meidacategory.data.model.AudioFile;

import java.util.List;

/**
 * Created by koma on 2017/1/14.
 */

public interface AudioContract {
    interface View extends BaseView<Presenter> {
        void refershAdapter(List<AudioFile> audioFiles);
    }

    interface Presenter extends BasePresenter {
        void getAudioFiles();
    }
}
