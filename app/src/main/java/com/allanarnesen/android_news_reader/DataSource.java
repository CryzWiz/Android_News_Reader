package com.allanarnesen.android_news_reader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by allan on 10.05.2018.
 * Class containing methods for communication with SQLite for fetching, deleting, updating and creating
 * items in the database
 */

public class DataSource {

    private static final String TAG = "DataSource";
    private static DataSource instance=null;
    private static SQLiteDatabase database=null;
    private static MySQLiteHelper dbHelper=null;
    /**
     * Called once to initialize
     * Initialize a instance of the DataSource,
     * get a db helper, and return a db object if
     * it does not exist
     */
    public static synchronized void initializeInstance(Context context) {
        Log.d(TAG, "initializeInstance");
        if (instance == null) {
            instance = new DataSource();
            dbHelper = new MySQLiteHelper(context);
            database = dbHelper.getWritableDatabase();
        }
    }
    /**
     * If instance is null, throw exception
     */
    private static void checkStatus() {
        Log.d(TAG, "checkStatus");
        if (instance == null) {
            throw new IllegalStateException(DataSource.class.getSimpleName() +
                    " er ikke initialisert, sørg for å kalle initializeInstance(Context) først.");
        }
    }
    /**
     * Used to get access to the db -> Returns this instance
     *
     */
    public static synchronized DataSource getInstance() {
        Log.d(TAG, "getInstance");
        return instance;
    }
    /**
     * If we close the app, close the connection to the db
     */
    public static synchronized void closeDataSource() {
        Log.d(TAG, "closeDataSource");
        if (DataSource.getInstance().database != null && DataSource.getInstance().dbHelper != null)
            DataSource.getInstance().dbHelper.close();
    }
    /**
     * Sources_Table members
     */
    public String[] Sources_Table = {
            Tables.SOURCES_COL_ID,
            Tables.SOURCES_COL_NAME,
            Tables.SOURCES_COL_URL
    };

    /**
     * Empty constructor -> initializer
     */
    public DataSource() {
        Log.d(TAG, "DataSource");
    }

    /**
     * Create a new source in the db
     * @param sourceName
     * @param sourceUrl
     * @return
     */
    public boolean createSourcesRegistration(String sourceName, String sourceUrl) {
        //Log.d(TAG, "createHourRegistration");
        DataSource.checkStatus();
        ContentValues values = new ContentValues();
        values.put(Tables.SOURCES_COL_NAME, sourceName);
        values.put(Tables.SOURCES_COL_URL, sourceUrl);


        long insertId = database.insert(Tables.SOURCES_TABLE, null, values);
        if (insertId>-1)
            return true;
        else
            return false;
    }

    /**
     * Fetch a single source from the db
     * @param id
     * @return
     */
    public NewsSource getSingleSource(int id) {
        Log.d(TAG, "getSingleSource");
        DataSource.checkStatus();

        Cursor cursor = database.query(Tables.SOURCES_TABLE, Sources_Table, "Sources_Id = ?", new String[]{String.valueOf(id)}, null, null, null);
        cursor.moveToFirst();

        NewsSource source = new NewsSource();
        int keyIndex = cursor.getColumnIndexOrThrow(Tables.SOURCES_COL_ID);
        int sourcesNameIndex = cursor.getColumnIndexOrThrow(Tables.SOURCES_COL_NAME);
        int sourcesURLIndex = cursor.getColumnIndexOrThrow(Tables.SOURCES_COL_URL);

        source.setSource_Id(cursor.getInt(keyIndex));
        source.setSource_Name(cursor.getString(sourcesNameIndex));
        source.setSource_URL(cursor.getString(sourcesURLIndex));

        cursor.close();
        return source;
    }

    /**
     * Fetch all the sources we have in the db
     * @param allSources
     */
    public void getAllSource(ArrayList<NewsSource> allSources) {
        Log.d(TAG, "getAllSource");
        DataSource.checkStatus();

        Cursor cursor = database.query(Tables.SOURCES_TABLE, Sources_Table, null, null, null, null, null);
        cursor.moveToFirst();
        allSources.clear();
        while (!cursor.isAfterLast()) {
            allSources.add(cursorToSource(cursor));
            cursor.moveToNext();
        }
        cursor.close();
    }

    /**
     * Create project objects from every row returned
     * and return the object for the ArrayList
     */
    public NewsSource cursorToSource(Cursor cursor) {
        Log.d(TAG, "cursorToSource");
        DataSource.checkStatus();

        NewsSource source = new NewsSource();
        int keyIndex = cursor.getColumnIndexOrThrow(Tables.SOURCES_COL_ID);
        int projectCodeIndex = cursor.getColumnIndexOrThrow(Tables.SOURCES_COL_NAME);
        int projectNameIndex = cursor.getColumnIndexOrThrow(Tables.SOURCES_COL_URL);

        source.setSource_Id(cursor.getInt(keyIndex));
        source.setSource_Name(cursor.getString(projectCodeIndex));
        source.setSource_URL(cursor.getString(projectNameIndex));

        return source;
    }

    /**
     * Delete a specified source from the db
     * TODO:: Needs to be tested and implemented
     */
    public void deleteSource(NewsSource source) {
        //Log.d(TAG, "deleteProject");
        DataSource.checkStatus();

        int id = source.getSource_Id();
        database.delete(Tables.SOURCES_TABLE, Tables.SOURCES_COL_ID + " = " + id, null);
    }
}
