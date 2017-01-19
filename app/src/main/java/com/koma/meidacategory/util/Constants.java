package com.koma.meidacategory.util;

import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by koma on 1/17/17.
 */

public class Constants {
    public static final Uri AUDIO_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    public static final Uri VIDEO_URI = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    public static final Uri IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    //Image animation time
    public static final int ANIMATION_TIME = 1 * 1000;
    //Grid count
    public static final int GRID_COUNT = 2;
}
