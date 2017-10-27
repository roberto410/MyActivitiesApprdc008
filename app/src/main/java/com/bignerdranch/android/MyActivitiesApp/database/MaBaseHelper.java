package com.bignerdranch.android.MyActivitiesApp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.MyActivitiesApp.database.MaDbSchema.MaTable;

/**
 * Created by Robbie Clark on 31/08/2017.
 *
 * This Class creates the SQL tables in the database based off variables from MaDbSchema.class
 */

public class MaBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "MyActivityAppBase.db";

    public MaBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    //Creates MaTable Table in the Database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + MaDbSchema.MaTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                MaTable.Cols.UUID + ", " +
                MaTable.Cols.TITLE + ", " +
                MaTable.Cols.DATE + ", " +
                MaTable.Cols.TYPE + ", " +
                MaTable.Cols.COMMENT + ", " +
                MaTable.Cols.DURATION + ", " +
                MaTable.Cols.LONGITUDE + ", " +
                MaTable.Cols.LATITUDE +
                ")"
        );


        //Creates UserProfile Table in the Database
        db.execSQL("create table " + MaDbSchema.UserProfile.NAME + "(" +
                " _id integer primary key autoincrement, " +
                MaDbSchema.UserProfile.Cols.UUID + ", " +
                MaDbSchema.UserProfile.Cols.NAME + ", " +
                MaDbSchema.UserProfile.Cols.EMAIL + ", " +
                MaDbSchema.UserProfile.Cols.COMMENT + ", " +
                MaDbSchema.UserProfile.Cols.GENDER + ", " +
                MaDbSchema.UserProfile.Cols.IDNUM +
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVerison, int newVersion) {

    }
}
