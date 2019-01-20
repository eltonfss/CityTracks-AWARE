/**
@author: denzil
 */

package com.aware.plugin.google.activity_recognition;

import java.util.HashMap;

import com.aware.*;
import com.aware.utils.DatabaseHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Environment;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Google's Activity Recognition Context Provider. Stored in SDcard in
 * AWARE/plugin_google_activity_recognition.db
 * 
 * @author denzil
 */
public class Google_AR_Provider extends ContentProvider {

    public static final int DATABASE_VERSION = 6;

    /**
     * Provider authority: com.aware.provider.plugin.google.activity_recognition
     */
    public static String AUTHORITY = "com.aware.plugin.google.activity_recognition.provider.gar";

    private static final int GOOGLE_AR = 1;
    private static final int GOOGLE_AR_ID = 2;

    public static final class Google_Activity_Recognition_Data implements BaseColumns {
        private Google_Activity_Recognition_Data() {
        };

        public static final Uri CONTENT_URI = Uri.parse("content://"+ Google_AR_Provider.AUTHORITY + "/plugin_google_activity_recognition");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.aware.plugin.google.activity_recognition";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.aware.plugin.google.activity_recognition";

        public static final String _ID = "_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String DEVICE_ID = "device_id";
        public static final String ACTIVITY_NAME = "activity_name";
        public static final String ACTIVITY_TYPE = "activity_type";
        public static final String CONFIDENCE = "confidence";
        /**
         * JSONArray representation of all the activities and confidence values<br/>
         * [{'activity':'walking','confidence':90},...]
         */
        public static final String ACTIVITIES = "activities";
    }

    public static String DATABASE_NAME = "plugin_google_activity_recognition.db";

    public static final String[] DATABASE_TABLES = {
            "plugin_google_activity_recognition"
    };

    public static final String[] TABLES_FIELDS = {
            Google_Activity_Recognition_Data._ID + " integer primary key autoincrement," + 
            Google_Activity_Recognition_Data.TIMESTAMP + " real default 0," + 
            Google_Activity_Recognition_Data.DEVICE_ID + " text default ''," +
            Google_Activity_Recognition_Data.ACTIVITY_NAME + " text default ''," +
            Google_Activity_Recognition_Data.ACTIVITY_TYPE + " integer default 0," +
            Google_Activity_Recognition_Data.CONFIDENCE + " integer default 0," +
            Google_Activity_Recognition_Data.ACTIVITIES + " text default ''"
    };

    private static UriMatcher sUriMatcher = null;
    private static HashMap<String, String> gARMap = null;

    private DatabaseHelper dbHelper;
    private static SQLiteDatabase database;

    private void initialiseDatabase() {
        if (dbHelper == null)
            dbHelper = new DatabaseHelper(getContext(), DATABASE_NAME, null, DATABASE_VERSION, DATABASE_TABLES, TABLES_FIELDS);
        if (database == null)
            database = dbHelper.getWritableDatabase();
    }
    
    /**
     * Delete entry from the database
     */
    @Override
    public synchronized int delete(Uri uri, String selection, String[] selectionArgs) {
        initialiseDatabase();

        database.beginTransaction();

        int count;
        switch (sUriMatcher.match(uri)) {
            case GOOGLE_AR:
                count = database.delete(DATABASE_TABLES[0], selection,
                        selectionArgs);
                break;
            default:
                database.endTransaction();
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        database.setTransactionSuccessful();
        database.endTransaction();

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case GOOGLE_AR:
                return Google_Activity_Recognition_Data.CONTENT_TYPE;
            case GOOGLE_AR_ID:
                return Google_Activity_Recognition_Data.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    /**
     * Insert entry to the database
     */
    @Override
    public synchronized Uri insert(Uri uri, ContentValues initialValues) {
        initialiseDatabase();

        ContentValues values = (initialValues != null) ? new ContentValues(
                initialValues) : new ContentValues();

        database.beginTransaction();

        switch (sUriMatcher.match(uri)) {
            case GOOGLE_AR:
                long google_AR_id = database.insertWithOnConflict(DATABASE_TABLES[0],
                        Google_Activity_Recognition_Data.ACTIVITY_NAME, values, SQLiteDatabase.CONFLICT_IGNORE);

                if (google_AR_id > 0) {
                    Uri new_uri = ContentUris.withAppendedId(
                            Google_Activity_Recognition_Data.CONTENT_URI,
                            google_AR_id);
                    getContext().getContentResolver().notifyChange(new_uri,
                            null);
                    database.setTransactionSuccessful();
                    database.endTransaction();
                    return new_uri;
                }
                database.endTransaction();
                throw new SQLException("Failed to insert row into " + uri);
            default:
                database.endTransaction();
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public boolean onCreate() {

        Log.i("GOOGLE AR", "GOOGLE AR STARTED");

    	AUTHORITY = getContext().getPackageName() + ".provider.gar";
    	
    	sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Google_AR_Provider.AUTHORITY, DATABASE_TABLES[0],
                GOOGLE_AR);
        sUriMatcher.addURI(Google_AR_Provider.AUTHORITY, DATABASE_TABLES[0]
                + "/#", GOOGLE_AR_ID);

        gARMap = new HashMap<String, String>();
        gARMap.put(Google_Activity_Recognition_Data._ID,
                Google_Activity_Recognition_Data._ID);
        gARMap.put(Google_Activity_Recognition_Data.TIMESTAMP,
                Google_Activity_Recognition_Data.TIMESTAMP);
        gARMap.put(Google_Activity_Recognition_Data.DEVICE_ID,
                Google_Activity_Recognition_Data.DEVICE_ID);
        gARMap.put(Google_Activity_Recognition_Data.ACTIVITY_NAME,
                Google_Activity_Recognition_Data.ACTIVITY_NAME);
        gARMap.put(Google_Activity_Recognition_Data.ACTIVITY_TYPE,
                Google_Activity_Recognition_Data.ACTIVITY_TYPE);
        gARMap.put(Google_Activity_Recognition_Data.CONFIDENCE,
                Google_Activity_Recognition_Data.CONFIDENCE);
        gARMap.put(Google_Activity_Recognition_Data.ACTIVITIES,
                Google_Activity_Recognition_Data.ACTIVITIES);

        return true;
    }

    /**
     * Query entries from the database
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        
    	initialiseDatabase();

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
            case GOOGLE_AR:
                qb.setTables(DATABASE_TABLES[0]);
                qb.setProjectionMap(gARMap);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        try {
            Cursor c = qb.query(database, projection, selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;
        } catch (IllegalStateException e) {
            if (Aware.DEBUG)
                Log.e(Aware.TAG, e.getMessage());

            return null;
        }
    }

    /**
     * Update application on the database
     */
    @Override
    public synchronized int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        
    	initialiseDatabase();

        database.beginTransaction();
    	
        int count;
        switch (sUriMatcher.match(uri)) {
            case GOOGLE_AR:
                count = database.update(DATABASE_TABLES[0], values, selection,
                        selectionArgs);
                break;
            default:
                database.endTransaction();
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        database.setTransactionSuccessful();
        database.endTransaction();

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
