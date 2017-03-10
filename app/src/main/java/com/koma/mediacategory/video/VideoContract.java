package com.koma.mediacategory.video;

import com.koma.mediacategory.base.BasePresenter;
import com.koma.mediacategory.base.BaseView;
import com.koma.mediacategory.data.model.VideoFile;

import java.util.List;

/**
 * Created by koma on 1/17/17.
 */

public class VideoContract {
    interface View extends BaseView<Presenter> {
        void refreshAdapter(List<VideoFile> audioFiles);
    }

    interface Presenter extends BasePresenter {
        void getVideoFiles();
    }
}
