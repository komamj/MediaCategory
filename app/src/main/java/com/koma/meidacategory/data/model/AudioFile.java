package com.koma.meidacategory.data.model;

/**
 * Created by koma on 2017/1/14.
 */

public class AudioFile {
    /**
     * 在数据库中的id.
     */
    private int mId;

    /**
     * 标题，如果有id3标签，则是标签中的标题，否则为文件名（不包括后缀名）.
     */
    private String mTitle;

    /**
     * 艺术家的名称，没有则 为<unknown>.
     */
    private String mArtistName;

    /**
     * 艺术家的id.
     */
    private int mArtistId;

    /**
     * 专辑名称，.
     */
    private String mAlbumName;

    /**
     * 专辑的id.
     */
    private int mAlbumId;

    /**
     * 专辑中这首歌曲的音轨号.
     */
    private int mTrackId;

    /**
     * 歌曲文件夹的路径.
     */
    private String mFilePath;

    public AudioFile(int id, int artistId, int albumId, int trackId, String title, String artistName, String albumName, String filePath) {
        this.mId = id;
        this.mArtistId = artistId;
        this.mAlbumId = albumId;
        this.mTrackId = trackId;
        this.mTitle = title;
        this.mArtistName = artistName;
        this.mAlbumName = albumName;
        this.mFilePath = filePath;
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


    public String getArtistName() {
        return mArtistName;
    }


    public void setArtistName(String artistName) {
        this.mArtistName = artistName;
    }


    public int getArtistId() {
        return mArtistId;
    }


    public void setArtistId(int artistId) {
        this.mArtistId = artistId;
    }


    public String getAlbumName() {
        return mAlbumName;
    }


    public void setAlbumName(String albumName) {
        this.mAlbumName = albumName;
    }


    public int getAlbumId() {
        return mAlbumId;
    }


    public void setAlbumId(int albumId) {
        this.mAlbumId = albumId;
    }


    public int getTrackId() {
        return mTrackId;
    }


    public void setTrackId(int trackId) {
        this.mTrackId = trackId;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setFilePath(String filePath) {
        this.mFilePath = filePath;
    }
}
