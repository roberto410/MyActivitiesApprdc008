package com.bignerdranch.android.MyActivitiesApp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.MyActivitiesApp.Ma;
import com.bignerdranch.android.MyActivitiesApp.UserProfile;
import com.bignerdranch.android.MyActivitiesApp.database.MaDbSchema.MaTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Robbie Clark on 21/09/2017.
 *
 * This Class gets data from the database
 */

public class MaCursorWrapper extends CursorWrapper {

    public MaCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    // Gets Ma.class data
    public Ma getMa() {
        String uuidString = getString(getColumnIndex(MaTable.Cols.UUID));
        String title = getString(getColumnIndex(MaTable.Cols.TITLE));
        long date = getLong(getColumnIndex(MaTable.Cols.DATE));
        String type = getString(getColumnIndex(MaTable.Cols.TYPE));
        String duration = getString(getColumnIndex(MaTable.Cols.DURATION));
        String comment = getString(getColumnIndex(MaTable.Cols.COMMENT));
        String latitude = getString(getColumnIndex(MaTable.Cols.LATITUDE));
        String longitude = getString(getColumnIndex(MaTable.Cols.LONGITUDE));

        Ma ma = new Ma(UUID.fromString(uuidString));
        ma.setTitle(title);
        ma.setDate(new Date(date));
        ma.setType(type);
        ma.setComment(comment);
        ma.setDuration(duration);
        ma.setLatitude(latitude);
        ma.setLongitude(longitude);

        return ma;
    }


    // Gets UserProfile.class data
    public UserProfile getUserProfile() {
        String uuidString = getString(getColumnIndex(MaDbSchema.UserProfile.Cols.UUID));
        String nameString = getString(getColumnIndex(MaDbSchema.UserProfile.Cols.NAME));
        String emailString = getString(getColumnIndex(MaDbSchema.UserProfile.Cols.EMAIL));
        String commentString = getString(getColumnIndex(MaDbSchema.UserProfile.Cols.COMMENT));
        String genderString = getString(getColumnIndex(MaDbSchema.UserProfile.Cols.GENDER));
        String idnumString = getString(getColumnIndex(MaDbSchema.UserProfile.Cols.IDNUM));

        UserProfile up = new UserProfile(UUID.fromString(uuidString));
        up.setmName(nameString);
        up.setmEmail(emailString);
        up.setmComment(commentString);
        up.setmGender(genderString);
        up.setmIdnum(idnumString);

        return up;
    }








}