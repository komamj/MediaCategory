package com.koma.meidacategory.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.koma.meidacategory.MediaCategoryApplication;
import com.koma.meidacategory.data.model.AudioFile;

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
            MediaStore.Audio.Media.IS_MUSIC,
    };

    public static List<AudioFile> getAudioFiles() {
        List<AudioFile> audioFiles = new ArrayList<>();
        ContentResolver contentResolver = MediaCategoryApplication.getContext().getContentResolver();
        /** 按照，名称排列，并且名称不为空 **/
        String sortOrder = MediaStore.Audio.Media.TITLE + " COLLATE UNICODE";
        /** 这首歌曲是音乐，标题不为空 */
        String whereClause = MediaStore.Audio.Media.TITLE + "!='' " + " AND "
                + MediaStore.Audio.Media.IS_MUSIC + "!=0 ";
        Cursor cursor = contentResolver.query(Constants.AUDIO_URI, AUDIO_PROJECTION,
                whereClause, null, sortOrder);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        AudioFile audioFile = new AudioFile();
                        audioFile.setId(cursor.getInt(cursor.getColumnIndex(AUDIO_PROJECTION[0])));
                        audioFile.setTitle(cursor.getString(cursor.getColumnIndex(AUDIO_PROJECTION[1])));
                        audioFile.setArtist(cursor.getString(cursor.getColumnIndex(AUDIO_PROJECTION[2])));
                        audioFile.setAlbumId(cursor.getInt(cursor.getColumnIndex(AUDIO_PROJECTION[3])));
                        audioFiles.add(audioFile);
                        LogUtils.i(TAG, "getAudioFiles Title : " + audioFile.getTitle());
                    } while (cursor.moveToNext());
                }

            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.i(TAG, "getAudioFiles error : " + e.toString());
            } finally {
                cursor.close();
            }
        }

        return audioFiles;
    }

    public static Uri getAlbumArtUri(long albumId) {
        return ContentUris.withAppendedId(Uri.parse(ALBUM_URI), albumId);
    }
}
