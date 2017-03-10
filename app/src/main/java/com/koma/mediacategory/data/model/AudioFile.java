package com.koma.mediacategory.data.model;

/**
 * Created by koma on 2017/1/14.
 */

public class AudioFile {
    private int mId;
    private String mTitle;
    private String mArtist;
    private int mAlbumId;

    public AudioFile() {
    }

    public AudioFile(int id, int albumId, String title, String artist, String path) {
        this.mId = id;
        this.mAlbumId = albumId;
        this.mTitle = title;
        this.mArtist = artist;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artistName) {
        this.mArtist = artistName;
    }

    public int getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(int albumId) {
        this.mAlbumId = albumId;
    }
}
