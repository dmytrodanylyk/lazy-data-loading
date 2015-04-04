package com.dd.model;

import android.database.sqlite.SQLiteOpenHelper;
import com.dd.utils.L;

public class DatabaseConnection {

    private int mOpenCounter;

    private static DatabaseConnection sInstance;
    private SQLiteOpenHelper mDatabaseHelper;
    private Database mDatabase;

    private DatabaseConnection(SQLiteOpenHelper helper) {
        mDatabaseHelper = helper;
    }

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (sInstance == null) {
            sInstance = new DatabaseConnection(helper);
        }
    }

    public static synchronized DatabaseConnection instance() {
        if (sInstance == null) {
            throw new IllegalStateException(DatabaseConnection.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return sInstance;
    }

    public synchronized Database open() {
        if (mOpenCounter == 0) {
            // Opening new database
            mDatabase = new Database(mDatabaseHelper.getWritableDatabase());
        }
        mOpenCounter++;
        L.d("Database open counter: " + mOpenCounter);
        return mDatabase;
    }

    public synchronized void close() {
        mOpenCounter--;
        if (mOpenCounter == 0) {
            // Closing database
            mDatabaseHelper.close();

        }
        L.d("Database open counter: " + mOpenCounter);
    }

}
