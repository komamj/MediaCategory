package com.koma.mediacategory;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.koma.mediacategory.util.LogUtils;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by koma on 2017/1/14.
 */

public class MediaCategoryApplication extends Application {
    private static final String TAG = MediaCategoryApplication.class.getSimpleName();

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i(TAG, "onCreate");
        sContext = getApplicationContext();
        enabledStrictMode();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public synchronized static Context getContext() {
        return sContext;
    }

    private void enabledStrictMode() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {

            //线程监控，会弹出对话框
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDialog()
                    .build());

            //VM监控
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }

    }
}
