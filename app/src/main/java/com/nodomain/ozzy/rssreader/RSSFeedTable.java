package com.nodomain.ozzy.rssreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RSSFeedTable {
    private static final String LOG_TAG = "RSSFeedTable";
    private static final String DB_TABLE = "RSSFeed";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_RSS_LINK = "rss_link";
    public static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_TITLE + " text, " +
                    COLUMN_DESCRIPTION + " text, " +
                    COLUMN_RSS_LINK + " text, " +
                    COLUMN_LINK + " text, " +
                    "FOREIGN KEY("+COLUMN_RSS_LINK+") REFERENCES "+RSSListTable.DB_TABLE+"("+RSSListTable.COLUMN_LINK+")"+
                    ");";
    private final Context mCtx;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;
    public RSSFeedTable(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DBHelper.DB_NAME, null, DBHelper.DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }
    public Cursor getData(String id) {return mDB.query(DB_TABLE, null, RSSFeedTable.COLUMN_RSS_LINK+ " = ?", new String [] {id}, null, null, null);}

    // добавить запись в DB_TABLE
    public void addRec(String title, String description, String rssLink) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_RSS_LINK,rssLink);
        mDB.insert(DB_TABLE, null, cv);
        Log.d(LOG_TAG, "RSS with title: "+title + " and description: " + description+" added!");
    }

    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    public void delAllRec() {
        mDB.delete(DB_TABLE,null,null);
    }



}