package com.koma.mediacategory.audio;

import com.koma.mediacategory.base.BasePresenter;
import com.koma.mediacategory.base.BaseView;
import com.koma.mediacategory.data.model.AudioFile;

import java.util.List;

/**
 * Created by koma on 2017/1/14.
 */

public interface AudioContract {
    interface View extends BaseView<Presenter> {
        void refreshAdapter(List<AudioFile> audioFiles);
    }

    interface Presenter extends BasePresenter {
        void getAudioFiles();
    }
}
