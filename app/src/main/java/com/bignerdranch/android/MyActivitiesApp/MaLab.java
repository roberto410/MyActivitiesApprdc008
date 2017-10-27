package com.bignerdranch.android.MyActivitiesApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.bignerdranch.android.MyActivitiesApp.database.MaBaseHelper;
import com.bignerdranch.android.MyActivitiesApp.database.MaCursorWrapper;
import com.bignerdranch.android.MyActivitiesApp.database.MaDbSchema;
import com.bignerdranch.android.MyActivitiesApp.database.MaDbSchema.MaTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rdc008 on 24/08/2017.
 *
 * This file helps the fragments and database communicate
 */

public class MaLab {

    private static MaLab sMaLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static MaLab getMa(Context context) {
        if (sMaLab == null) {
            sMaLab = new MaLab(context);
        }

        return sMaLab;
    }


    private MaLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new MaBaseHelper(mContext).getWritableDatabase();
    }

    public void addMa(Ma c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(MaTable.NAME, null, values);
    }

    public void addUserProfile(UserProfile c) {
        ContentValues values = getUpContentValues(c);
        mDatabase.insert(MaDbSchema.UserProfile.NAME, null, values);
    }

    public void deleteMa(Ma c) {
        mDatabase.delete(MaTable.NAME, "uuid = ?", new String[] {c.getId().toString()} );
    }

    public List<Ma> getMas(){
        List<Ma> Mas = new ArrayList<>();
        MaCursorWrapper cursor = queryMas(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Mas.add(cursor.getMa());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return Mas;
    }

    public Ma getMa(UUID id) {
        MaCursorWrapper cursor = queryMas(
                MaTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getMa();
        } finally {
            cursor.close();
        }
    }

    public UserProfile getUserProfile(UUID id) {
        MaCursorWrapper cursor = queryUps(
                MaDbSchema.UserProfile.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();

            return cursor.getUserProfile();
        } finally {
            cursor.close();
        }
    }




    public File getPhotoFile(Ma ma) {
        File externalFilesDir = mContext
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, ma.getPhotoFilename());
    }

    public void updateMa(Ma ma) {
        String uuidString = ma.getId().toString();
        ContentValues values = getContentValues(ma);
        mDatabase.update(MaTable.NAME, values,
                MaTable.Cols.UUID + " = ?",
                new String[] { uuidString });
        }

    public void updateUserProfile(UserProfile up) {
        String uuidString = up.getmId().toString();
        ContentValues values = getUpContentValues(up);
        mDatabase.update(MaDbSchema.UserProfile.NAME, values,
                MaTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private static ContentValues getContentValues(Ma ma) {
        ContentValues values = new ContentValues();
        values.put(MaTable.Cols.UUID, ma.getId().toString());
        values.put(MaTable.Cols.TITLE, ma.getTitle());
        values.put(MaTable.Cols.DATE, ma.getDate().getTime());
        values.put(MaTable.Cols.TYPE, ma.getType());
        values.put(MaTable.Cols.COMMENT, ma.getComment());
        values.put(MaTable.Cols.DURATION, ma.getDuration());
        values.put(MaTable.Cols.LATITUDE, ma.getLatitude());
        values.put(MaTable.Cols.LONGITUDE, ma.getLongitude());

        return values;
    }

    private static ContentValues getUpContentValues(UserProfile up) {
        ContentValues values = new ContentValues();
        values.put(MaDbSchema.UserProfile.Cols.UUID, up.getmId().toString());
        values.put(MaDbSchema.UserProfile.Cols.NAME, up.getmName());
        values.put(MaDbSchema.UserProfile.Cols.EMAIL, up.getmEmail());
        values.put(MaDbSchema.UserProfile.Cols.COMMENT, up.getmComment());
        values.put(MaDbSchema.UserProfile.Cols.GENDER, up.getmGender());
        values.put(MaDbSchema.UserProfile.Cols.IDNUM, up.getmIdnum());
        return values;
    }

    private MaCursorWrapper queryMas(String whereClause, String[] whereArgs) {
    Cursor cursor = mDatabase.query(
                MaTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new MaCursorWrapper(cursor);
    }

    private MaCursorWrapper queryUps(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                MaDbSchema.UserProfile.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new MaCursorWrapper(cursor);
    }


}
