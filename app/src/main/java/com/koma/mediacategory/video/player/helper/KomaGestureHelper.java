package com.koma.mediacategory.video.player.helper;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.koma.mediacategory.util.LogUtils;
import com.koma.mediacategory.video.player.widget.KomaMediaController;

/**
 * Created by koma on 2/14/17.
 */

public class KomaGestureHelper extends GestureDetector.SimpleOnGestureListener {
    private static final String TAG = KomaGestureHelper.class.getSimpleName();

    private static final int UNKNOW = 0x00;
    private static final int SCROLL_VERTICAL_LEFT = 2;
    private static final int SCROLL_VERTICAL_RIGHT = 3;
    private static final int SCROLL_HORIZONAL = 4;

    private int mMode;

    private Context mContext;

    private KomaMediaController mMediaController;

    KomaMediaController.KomaMediaPlayerController mPlayerController;

    public KomaGestureHelper(Context context, KomaMediaController mMediaController,
                             KomaMediaController.KomaMediaPlayerController playerController) {
        mContext = context;
        this.mMediaController = mMediaController;
        this.mPlayerController = playerController;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        mMode = UNKNOW;
        return super.onDown(e);
    }

    /**
     * 滑动
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float mOldX = e1.getX(), mOldY = e1.getY();
        int y = (int) e2.getRawY();
        int x = (int) e2.getRawX();

        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(dm);

        int windowWidth = dm.widthPixels;
        int windowHeight = dm.heightPixels;

        float dx = mOldX - x;
        float dy = mOldY - y;

        switch (mMode) {
            case SCROLL_VERTICAL_LEFT:
            case SCROLL_VERTICAL_RIGHT:
               /* if (Utils.isRTL() ^ (mMode == SCROLL_VERTICAL_LEFT)) {
                    //onVolumeSlide((mOldY - y) / windowHeight);
                } else {
                    //onBrightnessSlide((mOldY - y) / windowHeight);
                }*/
                break;

            case SCROLL_HORIZONAL:
                //onProgressSlide((x - mOldX) / windowWidth);
                break;

            case UNKNOW:
                if (Math.abs(dy) > (Math.abs(dx) * 3)) {
                    /** 如果起始点的x轴坐标小于屏幕坐标的一半，则说明是在左边屏幕滑动 */
                    if (mOldX > (windowWidth * 0.5)) {
                        mMode = SCROLL_VERTICAL_LEFT;
                    } else if (mOldX < (windowWidth * 0.5)) {
                        mMode = SCROLL_VERTICAL_RIGHT;
                    }
                } else if (Math.abs(dx) > (Math.abs(dy) * 3)) {
                    mMode = SCROLL_HORIZONAL;
                }
                break;

            default:
                mMode = UNKNOW;
                break;
        }
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        LogUtils.i(TAG, "onDoubleTap");

        if (!mMediaController.isLocked()) {
            if (mPlayerController.isPlaying()) {
                mPlayerController.pause();
            } else {
                mPlayerController.start();
            }
            return true;
        }

        return super.onDoubleTap(motionEvent);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        LogUtils.i(TAG, "onSingleTapConfirmed");

        if (mMediaController.isLocked()) {
            mMediaController.showLockView();
            return true;
        } else {
            if (mMediaController.isShowing()) {
                mMediaController.hide();
            } else {
                mMediaController.show();
            }
            return true;
        }
    }
}
