package com.koma.meidacategory.image;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koma.meidacategory.R;
import com.koma.meidacategory.base.BaseFragment;
import com.koma.meidacategory.data.model.ImageFile;
import com.koma.meidacategory.util.Constants;
import com.koma.meidacategory.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by koma on 1/18/17.
 */

public class ImageFragment extends BaseFragment implements ImageContract.View {
    private static final String TAG = ImageFragment.class.getSimpleName();

    private ImageContract.Presenter mPresenter;
    private List<ImageFile> mData;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.i(TAG, "createView");
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_layout, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.i(TAG, "onViewCreated");
        init();
    }

    private void init() {
        if (mPresenter != null) {
            mPresenter.subscribe();
        }
        mData = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, Constants.GRID_COUNT);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mAdapter = new ImageAdapter(mContext, mData);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private final ContentObserver mImageObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            LogUtils.i(TAG, "video uri change so refresh");
            if (mPresenter != null) {
                mPresenter.getImageFiles();
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i(TAG, "onStart");
        mContext.getContentResolver().registerContentObserver(Constants.VIDEO_URI, true, mImageObserver);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(TAG, "onResume");
        if (mPresenter != null) {
            mPresenter.getImageFiles();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i(TAG, "onPause");
        if (mImageObserver != null) {
            mContext.getContentResolver().unregisterContentObserver(mImageObserver);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.i(TAG, "onDestroyView");
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }


    @Override
    public void setPresenter(ImageContract.Presenter presenter) {
        LogUtils.i(TAG, "setPresenter");
        mPresenter = presenter;
    }

    @Override
    public void refreshAdapter(List<ImageFile> imageFiles) {
        LogUtils.i(TAG, "refreshAdapter" + imageFiles.toString());
        if (mData != null) {
            mData.clear();
            mData.addAll(imageFiles);
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
