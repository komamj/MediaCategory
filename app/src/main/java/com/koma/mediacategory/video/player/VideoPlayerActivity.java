package com.koma.mediacategory.video.player;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.koma.mediacategory.R;
import com.koma.mediacategory.base.BaseActivity;
import com.koma.mediacategory.util.LogUtils;
import com.koma.mediacategory.video.player.widget.KomaMediaController;
import com.koma.mediacategory.video.player.widget.KomaVideoView;

import butterknife.BindView;

/**
 * Created by koma on 2/4/17.
 */

public class VideoPlayerActivity extends BaseActivity implements VideoPlayerContract.View {
    private static final String TAG = VideoPlayerActivity.class.getSimpleName();

    @BindView(R.id.video_view)
    KomaVideoView mVideoView;
    @BindView(R.id.media_controller)
    KomaMediaController mMediaController;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private VideoPlayerContract.Presenter mPresenter;

    private Uri mUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtils.i(TAG, "onCreate");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_player;
    }

    private void init() {
        setSupportActionBar(mToolbar);

        mPresenter = new VideoPlayerPresenter(this);

        mVideoView.setMediaController(mMediaController);

        mUri = getIntent().getData();

        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setVideoData(mUri);
    }

    private void setVideoData(Uri uri) {
        LogUtils.i(TAG, "setVideoData uri : " + uri.toString());
        int videoHeight, videoWidth;
        try {
            MediaMetadataRetriever retr = new MediaMetadataRetriever();
            retr.setDataSource(this, uri);

            String strH = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            String strW = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            String rotation = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
            retr.release();

            if (!TextUtils.isEmpty(strH) && !TextUtils.isEmpty(strW)) {
                /** 旋转角度为90或270，则长宽调换 */
                /** 旋转角度为0或者180，或为空，则长宽不变 */
                boolean needChange = "90".compareTo(rotation) == 0 || "270".compareTo(rotation) == 0;
                videoHeight = Integer.parseInt(needChange ? strW : strH);
                videoWidth = Integer.parseInt(needChange ? strH : strW);

                if (videoHeight > videoWidth) {
                    setScreenOritation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                } else {
                    setScreenOritation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "setVideoData error : " + e.toString());
        }

        /** 检查视频长宽比是不是与屏幕长宽比一致，如果是，则隐藏toolbar上面的按钮 */
        /** 我们的屏幕480*854，不是严格的16：9, 因此只要将视屏按屏幕比例放大，最后的差别小于一个像素即可 */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
       /* mIsHideButton = (Math.max(mVideoheight, mVideoWidth) * 9 == Math.min(mVideoheight, mVideoWidth) * 16);
        if (!mIsHideButton && !mIsFullScreen && (mVideoheight * mVideoWidth != 0)) {
            if (dm.heightPixels > dm.widthPixels) {
                dm.heightPixels = dm.widthPixels * mVideoheight / mVideoWidth;
            } else {
                dm.widthPixels = dm.heightPixels * mVideoWidth / mVideoheight;
            }
        }*/
        ViewGroup.LayoutParams layoutParams = mVideoView.getLayoutParams();
        layoutParams.height = dm.heightPixels;
        layoutParams.width = dm.widthPixels;
        mVideoView.setLayoutParams(layoutParams);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(TAG, "onResume");
        if (mPresenter != null) {
            mPresenter.subscribe();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i(TAG, "onPause");
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
        mVideoView.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.i(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy");
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_UP)
                && (event.getKeyCode() == KeyEvent.KEYCODE_BACK)) {
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtils.i(TAG, "onConfigurationChanged");
        setVideoData(mUri);
    }

    @Override
    public void preparePlayer() {
        mVideoView.setVideoURI(mUri);
        mVideoView.start();
    }

    @Override
    public void doStartOrPause() {
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
        } else {
            mVideoView.start();
        }
    }

    @Override
    public void setScreenOritation(int activityInfo) {
        if (getResources().getConfiguration().orientation != activityInfo) {
            /** 屏幕方向不是水平方向 */
            /** 视频的宽度大于长度，则需要横屏查看 */
            /** 因为在获取长度和宽度的时候，对旋转的情况做了处理，调换了长度和宽度的值，所以不判断旋转 */
            setRequestedOrientation(activityInfo);
        }
    }

    @Override
    public void setPresenter(VideoPlayerContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
