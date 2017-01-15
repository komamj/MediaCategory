package com.koma.meidacategory;

import com.koma.meidacategory.util.LogUtils;

/**
 * Created by koma on 2017/1/14.
 */

public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = MainPagerAdapter.class.getSimpleName();

    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        LogUtils.i(TAG, "subscribe");
    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG, "unSubscribe");
    }

    @Override
    public void initAdapter() {

    }
}
