package com.koma.mediacategory.data.model;

import java.util.Date;

/**
 * Created by koma on 2017/1/14.
 */

public class MediaFile {
    private String mTitle;
    private Date mModifiedTime;
    private long mSize;
    private String mParent;

    public MediaFile(String title, String parent, long size, Date modifiedTime) {
        mTitle = title;
        mSize = size;
        mModifiedTime = modifiedTime;
        mParent = parent;
    }

    public void setFileName(String fileName) {
        mTitle = fileName;
    }

    public String getFileName() {
        return mTitle;
    }

    public void setFileSize(long fileSize) {
        mSize = fileSize;
    }

    public long getFileSize() {
        return mSize;
    }

    public void setFileModifiedTime(Date modifiedTime) {
        mModifiedTime = modifiedTime;
    }

    public Date getFileModifiedTime() {
        return mModifiedTime;
    }

    public void setParent(String parent) {
        mParent = parent;
    }

    public String getParent() {
        return mParent;
    }
}
