package com.bignerdranch.android.MyActivitiesApp.database;


/**
 * Created by Robbie Clark on 31/08/2017.
 *
 * This class defines the strings for the databse, acts as database schema
 */

public class MaDbSchema {

    //Sets table column Strings for MaTable
    public static final class MaTable {
        public static final String NAME = "my_activities";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String TYPE = "type";
            public static final String COMMENT = "comment";
            public static final String DURATION = "duration";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
        }

    }

    //Sets table column Strings for UserProfile
    public static final class UserProfile {
        public static final String NAME = "user_profile";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String EMAIL = "email";
            public static final String COMMENT = "comment";
            public static final String GENDER = "gender";
            public static final String IDNUM = "idnum";
        }

    }
}
