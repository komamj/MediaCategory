package com.koma.meidacategory.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.format.Formatter;

import com.koma.meidacategory.MediaCategoryApplication;
import com.koma.meidacategory.data.model.AudioFile;
import com.koma.meidacategory.data.model.ImageFile;
import com.koma.meidacategory.data.model.VideoFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koma on 1/17/17.
 */

public class MediaCategoryUtils {
    private static final String TAG = MediaCategoryUtils.class.getSimpleName();
    private static final String ALBUM_URI = "content://media/external/audio/albumart";

    private static final String[] AUDIO_PROJECTION = new String[]{
            MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.IS_MUSIC
    };

    public static List<AudioFile> getAudioFiles() {
        List<AudioFile> audioFiles = new ArrayList<>();
        ContentResolver contentResolver = MediaCategoryApplication.getContext().getContentResolver();
        /** 按照，名称排列，并且名称不为空 **/
        String sortOrder = MediaStore.Audio.Media.TITLE + " COLLATE UNICODE";
        /** 这首歌曲是音乐，标题不为空 */
        String selection = MediaStore.Audio.Media.TITLE + "!='' " + " AND "
                + MediaStore.Audio.Media.IS_MUSIC + "!=0 ";
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(Constants.AUDIO_URI, AUDIO_PROJECTION,
                    selection, null, sortOrder);
            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                do {
                    AudioFile audioFile = new AudioFile();
                    audioFile.setId(cursor.getInt(cursor.getColumnIndex(AUDIO_PROJECTION[0])));
                    audioFile.setTitle(cursor.getString(cursor.getColumnIndex(AUDIO_PROJECTION[1])));
                    audioFile.setArtist(cursor.getString(cursor.getColumnIndex(AUDIO_PROJECTION[2])));
                    audioFile.setAlbumId(cursor.getInt(cursor.getColumnIndex(AUDIO_PROJECTION[3])));
                    audioFiles.add(audioFile);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.i(TAG, "getAudioFiles error : " + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return audioFiles;
    }

    public static Uri getAlbumArtUri(long albumId) {
        return ContentUris.withAppendedId(Uri.parse(ALBUM_URI), albumId);
    }

    private static final String[] VIDEO_PROJECTION = new String[]{
            MediaStore.Video.Media._ID, MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.DATA
    };

    public static List<VideoFile> getVideoFiles() {
        List<VideoFile> videoFiles = new ArrayList<>();
        ContentResolver contentResolver = MediaCategoryApplication.getContext().getContentResolver();
        String selection = MediaStore.Video.Media.TITLE + " != ''";
        String sortOrder = MediaStore.Video.Media.TITLE + " COLLATE UNICODE";
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(Constants.VIDEO_URI, VIDEO_PROJECTION, selection,
                    null, sortOrder);
            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                do {
                    VideoFile videoFile = new VideoFile();
                    videoFile.setId(cursor.getInt(cursor.getColumnIndex(VIDEO_PROJECTION[0])));
                    videoFile.setTitle(cursor.getString(cursor.getColumnIndex(VIDEO_PROJECTION[1])));
                    videoFile.setFilePath(cursor.getString(cursor.getColumnIndex(VIDEO_PROJECTION[2])));
                    videoFiles.add(videoFile);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "getVideoFiles error : " + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return videoFiles;
    }

    private static final String[] IMAGE_PROJECTION = new String[]{
            MediaStore.Images.Media._ID, MediaStore.Images.Media.TITLE,
            MediaStore.Images.Media.DATA
    };

    public static List<ImageFile> getImageFiles() {
        List<ImageFile> imageFiles = new ArrayList<>();
        ContentResolver contentResolver = MediaCategoryApplication.getContext().getContentResolver();
        String selection = MediaStore.Video.Media.TITLE + " != ''";
        String sortOrder = MediaStore.Video.Media.TITLE + " COLLATE UNICODE";
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(Constants.IMAGE_URI, IMAGE_PROJECTION, selection,
                    null, sortOrder);
            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                do {
                    ImageFile imageFile = new ImageFile();
                    imageFile.setId(cursor.getInt(cursor.getColumnIndex(IMAGE_PROJECTION[0])));
                    imageFile.setTitle(cursor.getString(cursor.getColumnIndex(IMAGE_PROJECTION[1])));
                    imageFile.setFilePath(cursor.getString(cursor.getColumnIndex(IMAGE_PROJECTION[2])));
                    imageFiles.add(imageFile);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "getImageFiles error : " + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return imageFiles;
    }
}
