package com.koma.meidacategory.util;

import java.util.Locale;

/**
 * Created by koma on 1/19/17.
 */

public class Utils {
    /**
     * Format duration.
     *
     * @param duration the duration
     * @return the string
     */
    public static String formatDuration(long duration) {
        long ss = duration / 1000 % 60;
        long mm = duration / 60000 % 60;
        long hh = duration / 3600000;
        if (duration < 60 * 60 * 1000) {
            return String.format(Locale.getDefault(), "%02d:%02d", mm, ss);
        } else {
            return String.format(Locale.getDefault(), "%02d:%02d:%02d", hh, mm, ss);
        }
    }
}
