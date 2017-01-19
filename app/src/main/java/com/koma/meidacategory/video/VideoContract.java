package com.koma.meidacategory.video;

import com.koma.meidacategory.base.BasePresenter;
import com.koma.meidacategory.base.BaseView;
import com.koma.meidacategory.data.model.VideoFile;

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
