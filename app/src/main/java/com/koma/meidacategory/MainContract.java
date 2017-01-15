package com.koma.meidacategory;

import com.koma.meidacategory.base.BasePresenter;
import com.koma.meidacategory.base.BaseView;

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
