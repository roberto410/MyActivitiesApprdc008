package com.bignerdranch.android.MyActivitiesApp;

import java.util.UUID;

/**
 * Created by Robbie Clark on 24/10/2017.
 *
 * This Class looks after the user profile and its data. It also sets the defaults for the User profile
 */

public class UserProfile {

    private UUID mId;
    private String mName;
    private String mEmail;
    private String mComment;
    private String mGender;
    private String mIdnum;


    public UserProfile(UUID id) {
        setmId(id);
        setmName("Tim");
        setmEmail("Tim@gmail.com");
        setmComment("This is a really nice looking app");
        setmGender("Male");
        setmIdnum("65");
    }

    // Getters and Setters
    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public String getmIdnum() {
        return mIdnum;
    }

    public void setmIdnum(String mIdnum) {
        this.mIdnum = mIdnum;
    }
}
