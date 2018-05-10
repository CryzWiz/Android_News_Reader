package com.allanarnesen.android_news_reader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by allan on 10.05.2018.
 * Helper for SQLite db
 */

public class MySQLiteHelper  extends SQLiteOpenHelper {

    //Databasespesifikt:
    private static final String DATABASE_NAME = "NewsRoom.db";
    private static final int DATABASE_VERSION = 1;


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Tables.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Tables.onUpgrade(db, oldVersion, newVersion);
    }
}
