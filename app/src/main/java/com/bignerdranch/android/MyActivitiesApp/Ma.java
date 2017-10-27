package com.bignerdranch.android.MyActivitiesApp;

import java.util.Date;
import java.util.UUID;

/**
 * Created by rdc008 on 24/08/2017.
 *
 * This class is an Ma (MyActivity) it represents the data of an activity.
 */

public class Ma {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private String mType;
    private String mDuration;
    private String mComment;
    private String mLatitude;
    private String mLongitude;

    public Ma() {
        this(UUID.randomUUID());
    }

    public Ma(UUID id) {
        mId = id;
        mDate = new Date();
    }

    //Getters and Setters
    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String lat) {
        mLatitude = lat;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return  mTitle;
    }

    public void setTitle (String title) {
        mTitle = title;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
