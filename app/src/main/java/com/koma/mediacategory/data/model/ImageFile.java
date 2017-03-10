package com.koma.mediacategory.data.model;

/**
 * Created by koma on 1/17/17.
 */

public class ImageFile {
    private int mId;
    private String mTitle;
    private String mFilePath;
    private String mSize;
    private long mDuration;

    public ImageFile(int id, String title, String filePath, String size, long duration) {
        this.mId = id;
        this.mTitle = title;
        this.mFilePath = filePath;
        this.mSize = size;
        this.mDuration = duration;
    }

    public ImageFile() {
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

    public void setSize(String size) {
        this.mSize = size;
    }

    public String getSize() {
        return this.mSize;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public long getDuration() {
        return this.mDuration;
    }
}
