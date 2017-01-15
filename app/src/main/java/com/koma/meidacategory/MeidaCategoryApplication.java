package com.koma.meidacategory;

import android.app.Application;
import android.content.Context;

import com.koma.meidacategory.util.LogUtils;

/**
 * Created by koma on 2017/1/14.
 */

public class MeidaCategoryApplication extends Application {
    private static final String TAG = MeidaCategoryApplication.class.getSimpleName();

    private static Context sContext;

    @Override public void onCreate(){
        super.onCreate();
        LogUtils.i(TAG,"onCreate");
        sContext = getApplicationContext();
    }

    public synchronized static Context  getContext(){
        return sContext;
    }
}
