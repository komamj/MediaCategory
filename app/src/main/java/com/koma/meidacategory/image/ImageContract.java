package com.koma.meidacategory.image;

import com.koma.meidacategory.base.BasePresenter;
import com.koma.meidacategory.base.BaseView;
import com.koma.meidacategory.data.model.ImageFile;

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
