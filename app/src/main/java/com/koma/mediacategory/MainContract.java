package com.koma.mediacategory;

import com.koma.mediacategory.base.BasePresenter;
import com.koma.mediacategory.base.BaseView;

/**
 * Created by koma on 2017/1/14.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void initViews();
    }

    interface Presenter extends BasePresenter {
        void initAdapter();
    }
}
