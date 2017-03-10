package com.koma.mediacategory.video.player;

import com.koma.mediacategory.base.BasePresenter;
import com.koma.mediacategory.base.BaseView;

/**
 * Created by koma on 2/4/17.
 */

public interface VideoPlayerContract {
    interface View extends BaseView<Presenter> {
        void preparePlayer();

        void doStartOrPause();

        void setScreenOritation(int activityInfo);
    }

    interface Presenter extends BasePresenter {
        void preparePlayer();

        void doStartOrPause();
    }
}
