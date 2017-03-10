package com.koma.mediacategory.data.model;

/**
 * Created by koma on 1/17/17.
 */

public class VideoFile {
    private int mId;
    private String mTitle;
    private String mFilePath;

    public VideoFile(int id, String title, String filePath) {
        this.mId = id;
        this.mTitle = title;
        this.mFilePath = filePath;
    }

    public VideoFile() {
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getId() {
        return this.mId;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setFilePath(String filePath) {
        this.mFilePath = filePath;
    }

    public String getFilePath() {
        return this.mFilePath;
    }

}
