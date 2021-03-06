package com.koma.mediacategory.util;


import android.util.Log;

import com.koma.mediacategory.BuildConfig;

/**
 * Created by koma on 2017/1/14.
 */

public class LogUtils {
    private static final boolean IS_DEBUG = BuildConfig.LOG_DEBUG;
    private static final String TAG = "MediaCategory";

    public static void i(String tag, String msg) {
        if (IS_DEBUG) {
            Log.i(TAG, buildString(tag, msg));
        }
    }

    public static void e(String tag, String msg) {
        if (IS_DEBUG) {
            Log.e(TAG, buildString(tag, msg));
        }
    }

    private static String buildString(String tag, String msg) {
        StringBuilder sb = new StringBuilder(tag);
        sb.append("----");
        sb.append(msg);
        return sb.toString();
    }
}
