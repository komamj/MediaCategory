package com.koma.mediacategory.image;

import com.koma.mediacategory.base.BasePresenter;
import com.koma.mediacategory.base.BaseView;
import com.koma.mediacategory.data.model.ImageFile;

import java.util.List;

/**
 * Created by koma on 1/18/17.
 */

public interface ImageContract {
    interface View extends BaseView<Presenter> {
        void refreshAdapter(List<ImageFile> imageFiles);
    }

    interface Presenter extends BasePresenter {
        void getImageFiles();
    }
}
