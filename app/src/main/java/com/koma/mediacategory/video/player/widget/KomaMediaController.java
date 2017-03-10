package com.koma.mediacategory.video.player.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.koma.mediacategory.R;
import com.koma.mediacategory.util.LogUtils;
import com.koma.mediacategory.video.player.helper.KomaGestureHelper;

import java.util.Formatter;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by koma on 2/4/17.
 */

public class KomaMediaController extends FrameLayout {
    private static final String TAG = KomaMediaController.class.getSimpleName();

    private boolean mShowing;
    private boolean mDragging;
    private boolean mLocked;

    private static final int sDefaultTimeout = 3000;
    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;

    private StringBuilder mFormatBuilder;

    private Formatter mFormatter;

    @BindView(R.id.seek_bar)
    SeekBar mSeekBar;

    @BindView(R.id.tv_current_time)
    TextView mCurrentTime;
    @BindView(R.id.tv_total_time)
    TextView mTotalTime;

    @BindView(R.id.btn_next)
    ImageView mNext;

    @OnClick(R.id.btn_next)
    void playNext() {
        LogUtils.i(TAG, "playNext");
    }

    @BindView(R.id.btn_pause)
    ImageView mPauseOrPaly;

    @BindView(R.id.ic_lock)
    ImageView mLockView;

    @OnClick(R.id.ic_lock)
    void lock() {
        if (!mLocked) {
            mLockView.setImageResource(R.drawable.ic_lock_close);
            mLocked = true;
            mToolbar.clearAnimation();
            mToolbar.setVisibility(GONE);
            mMediaControllerView.clearAnimation();
            mMediaControllerView.setVisibility(GONE);
            mShowing = false;
        } else {
            mLockView.setImageResource(R.drawable.ic_lock_open);
            mLocked = false;
            show();
        }
    }

    @BindView(R.id.media_controller_view)
    LinearLayout mMediaControllerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @OnClick(R.id.btn_pause)
    void playOrPause() {
        LogUtils.i(TAG, "playOrPause");
        if (mPlayer.isPlaying()) {
            mPauseOrPaly.setImageResource(R.drawable.ic_play);
            mPlayer.pause();
        } else {
            mPauseOrPaly.setImageResource(R.drawable.ic_pause);
            mPlayer.start();
        }
    }

    @BindView(R.id.btn_prev)
    ImageView mPrev;

    @OnClick(R.id.btn_prev)
    void prev() {
        LogUtils.i(TAG, "prev");
    }

    protected Context mContext;

    private KomaMediaPlayerController mPlayer;

    private GestureDetector mGestureDetector;

    private boolean mGesturing = false;

    private AudioManager mAudioManager;

    private int mMaxVolume;

    /**
     * 隐藏进度条的动画.
     */
    private Animation mHideAnimation;


    public KomaMediaController(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public KomaMediaController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KomaMediaController(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public KomaMediaController(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    private void init() {
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        mFormatBuilder = new StringBuilder();

        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

        mHideAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
        mHideAnimation.setAnimationListener(mAnimationListener);
    }

    private Animation.AnimationListener mAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            //// TODO: 3/6/17
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            LogUtils.i(TAG, "onAnimationEnd mShowing : " + mShowing);
            try {
                mLockView.setVisibility(View.GONE);
                if (mShowing) {
                    mToolbar.setVisibility(View.GONE);
                    mMediaControllerView.setVisibility(View.GONE);
                    mShowing = false;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                LogUtils.i(TAG, "already removed");
                mShowing = false;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            //// TODO: 3/6/17
        }
    };

    @Override
    public void onFinishInflate() {
        LogUtils.i(TAG, "onFinishflate");
        super.onFinishInflate();
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            /** 屏幕方向不是水平方向 */
            LayoutInflater.from(mContext).inflate(R.layout.media_controller, this, true);
        } else {
            LayoutInflater.from(mContext).inflate(R.layout.media_controller, this, true);
        }
        ButterKnife.bind(this, this);
        initListener();
    }

    private void initListener() {
        mSeekBar.setOnSeekBarChangeListener(mSeekListener);
    }

    public void setMediaPlayerController(KomaMediaPlayerController player) {
        mPlayer = player;
        mGestureDetector = new GestureDetector(mContext, new KomaGestureHelper(mContext, this, mPlayer));
    }

    protected SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        public void onStartTrackingTouch(SeekBar bar) {
            show(3600000);

            mDragging = true;

            // By removing these pending progress messages we make sure
            // that a) we won't update the progress while the user adjusts
            // the seekbar and b) once the user is done dragging the thumb
            // we will post one of these messages to the queue again and
            // this ensures that there will be exactly one message queued up.
            mHandler.removeMessages(SHOW_PROGRESS);
        }

        @Override
        public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
            if (!fromuser) {
                // We're not interested in programmatically generated changes to
                // the progress bar's position.
                return;
            }

            long duration = mPlayer.getDuration();
            long newposition = (duration * progress) / 1000L;
            mPlayer.seekTo((int) newposition);
            if (mCurrentTime != null) {
                mCurrentTime.setText(stringForTime((int) newposition));
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar bar) {
            mDragging = false;
            setProgress();
            updatePausePlay();
            show(sDefaultTimeout);

            // Ensure that progress is properly updated in the future,
            // the call to show() does not guarantee this because it is a
            // no-op if we are already showing.
            mHandler.sendEmptyMessage(SHOW_PROGRESS);
        }
    };

    private void updatePausePlay() {
        if (mPauseOrPaly == null)
            return;

        if (mPlayer.isPlaying()) {
            mPauseOrPaly.setImageResource(R.drawable.ic_pause);
        } else {
            mPauseOrPaly.setImageResource(R.drawable.ic_play);
        }
    }

    protected void doPauseResume() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            mPlayer.start();
        }
        updatePausePlay();
    }

    private int setProgress() {
        if (mPlayer == null || mDragging) {
            return 0;
        }
        int position = mPlayer.getCurrentPosition();
        int duration = mPlayer.getDuration();
        if (mSeekBar != null) {
            if (duration > 0) {
                // use long to avoid overflow
                long pos = 1000L * position / duration;
                mSeekBar.setProgress((int) pos);
            }
        }

        if (mTotalTime != null)
            mTotalTime.setText(stringForTime(duration));
        if (mCurrentTime != null)
            mCurrentTime.setText(stringForTime(position));

        return position;
    }

    public void showLockView() {
        if (mLockView.isShown()) {
            mLockView.clearAnimation();

            mLockView.setVisibility(GONE);
        } else {
            mLockView.setVisibility(VISIBLE);

            mHandler.removeMessages(FADE_OUT);
            Message msg = mHandler.obtainMessage(FADE_OUT);
            mHandler.sendMessageDelayed(msg, sDefaultTimeout);
        }
    }

    public void show() {
        show(sDefaultTimeout);
    }

    /**
     * Show the controller on screen. It will go away
     * automatically after 'timeout' milliseconds of inactivity.
     *
     * @param timeout The timeout in milliseconds. Use 0 to show
     *                the controller until hide() is called.
     */
    public void show(int timeout) {
        mLockView.setVisibility(VISIBLE);

        if (!mLocked && !mShowing) {
            // show controller view and toolbar
            mMediaControllerView.setVisibility(VISIBLE);
            mToolbar.setVisibility(VISIBLE);

            setProgress();

            if (mPauseOrPaly != null) {
                mPauseOrPaly.requestFocus();
            }

            mShowing = true;
        }

        updatePausePlay();

        // cause the progress bar to be updated even if mShowing
        // was already true.  This happens, for example, if we're
        // paused with the progress bar showing the user hits play.
        mHandler.sendEmptyMessage(SHOW_PROGRESS);

        if (timeout != 0) {
            mHandler.removeMessages(FADE_OUT);
            Message msg = mHandler.obtainMessage(FADE_OUT);
            mHandler.sendMessageDelayed(msg, timeout);
        }
    }

    protected String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);

        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    public boolean isShowing() {
        return mShowing;
    }

    public boolean isLocked() {
        return mLocked;
    }

    /**
     * Remove the controller from the screen.
     */
    public void hide() {
        if (mLocked) {
            mLockView.clearAnimation();
            mLockView.startAnimation(mHideAnimation);
            return;
        }
        if (mShowing) {
            mToolbar.clearAnimation();
            mMediaControllerView.clearAnimation();

            mHandler.removeMessages(SHOW_PROGRESS);
            mToolbar.startAnimation(mHideAnimation);
            mMediaControllerView.startAnimation(mHideAnimation);
        }
    }

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int pos;
            switch (msg.what) {
                case FADE_OUT:
                    hide();
                    break;
                case SHOW_PROGRESS:
                    pos = setProgress();
                    if (!mDragging && mShowing && mPlayer.isPlaying()) {
                        msg = obtainMessage(SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (pos % 1000));
                    }
                    break;
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (mGestureDetector.onTouchEvent(motionEvent)) {
            mGesturing = true;
        } else if (MotionEvent.ACTION_UP == (motionEvent.getAction() & MotionEvent.ACTION_MASK)) {
            if (mGesturing) {
                //finish the gesture
                finishGesture();
            }
        }
        return true;
    }

    private void finishGesture() {
        mGesturing = false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        final boolean uniqueDown = event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_DOWN;
        if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK
                || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
                || keyCode == KeyEvent.KEYCODE_SPACE) {
            if (uniqueDown) {
               /* doPauseResume();
                show(sDefaultTimeout);
                if (mPauseButton != null) {
                    mPauseButton.requestFocus();
                }*/
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY) {
            if (uniqueDown && !mPlayer.isPlaying()) {
                mPlayer.start();
                /*updatePausePlay();
                show(sDefaultTimeout);*/
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP
                || keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
            if (uniqueDown && mPlayer.isPlaying()) {
                mPlayer.pause();
                /*updatePausePlay();
                show(sDefaultTimeout);*/
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
                || keyCode == KeyEvent.KEYCODE_VOLUME_UP
                || keyCode == KeyEvent.KEYCODE_VOLUME_MUTE
                || keyCode == KeyEvent.KEYCODE_CAMERA) {
            // don't show the controls for volume adjustment
            return super.dispatchKeyEvent(event);
        } else if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            if (uniqueDown) {
                //hide();
            }
            return super.dispatchKeyEvent(event);
        }

        // show(sDefaultTimeout);
        return super.dispatchKeyEvent(event);
    }

    public interface KomaMediaPlayerController {
        /**
         * 开始播放
         */
        void start();

        /**
         * 暂停播放
         */
        void pause();

        /**
         * 获取视频长度
         *
         * @return the duration
         */
        int getDuration();

        /**
         * 获取当前播放进度
         *
         * @return the current position
         */
        int getCurrentPosition();

        /**
         * 调整当前播放进度到pos
         *
         * @param pos the pos
         */
        void seekTo(long pos);

        /**
         * 检查播放状态，是否正在播放
         *
         * @return true, if is playing
         */
        boolean isPlaying();
    }
}
