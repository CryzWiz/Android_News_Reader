package com.allanarnesen.android_news_reader;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by allan on 10.05.2018.
 * The cols and table names for the app
 * and the SQL-statements needed to create the
 * tables.
 */

public class Tables {
    private static final String TAG = "Tables";

    /**
     * Tables in the project
     */
    public static final String SOURCES_TABLE = "NewsSources";


    /**
     * Cols in Sources_Table
     */
    public static final String SOURCES_COL_ID = "NewsSources_Id";
    public static final String SOURCES_COL_URL = "NewsSources_URL";
    public static final String SOURCES_COL_NAME = "NewsSources_Name";


    /**
     * SQL statement to create Sources_Table
     */
    private static final String SOURCES_TABLE_CREATE = "create table "
            + SOURCES_TABLE
            + " (" + SOURCES_COL_ID + " integer primary key autoincrement, "
            + SOURCES_COL_URL + " text, "
            + SOURCES_COL_NAME + " text, "
            + ");";

    /**
     * Create the tables and activate foreign keys
     */
    public static void onCreate(SQLiteDatabase database) {
        Log.d(TAG, "onCreate");
        database.execSQL(SOURCES_TABLE_CREATE);
        database.execSQL("PRAGMA foreign_keys=ON;");
    }
    /**
     * On upgrade upgrade the database to new version
     */
    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade");
        Log.w(Tables.class.getName(), "Oppgraderer databasen fra versjon "
                + oldVersion + " til " + newVersion
                + ". Alle gamle data vil slettes.");

        database.execSQL("DROP TABLE IF EXISTS " + SOURCES_TABLE);
        onCreate(database);
    }
}
