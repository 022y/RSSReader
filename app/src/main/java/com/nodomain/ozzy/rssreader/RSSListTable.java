package com.nodomain.ozzy.rssreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RSSListTable {
    private static final String LOG_TAG = "RSSListTable";
    public static final String DB_TABLE = "RSSList";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LINK = "link";
    public static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_TITLE + " text, " +
                    COLUMN_LINK + " text " +
                    ");";
    public static final String DB_INIT_YA =
            "insert into " + DB_TABLE + "(" +
                    COLUMN_TITLE + ", " +
                    COLUMN_LINK +") VALUES (\"Yandex\", \"http://news.yandex.ru/index.rss\");";
    public static final String DB_INIT_LENTA ="insert into " + DB_TABLE + "(" +
                    COLUMN_TITLE + ", " +
                    COLUMN_LINK +") VALUES (\"Lenta\", \"http://lenta.ru/rss\");";

    private final Context mCtx;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;
    public RSSListTable(Context ctx) {
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
    public Cursor getData(String id) {return mDB.query(DB_TABLE, null, RSSListTable.COLUMN_LINK+ " = ?", new String [] {id}, null, null, null);}
    public Cursor getDataByID(String id) {return mDB.query(DB_TABLE, null, RSSListTable.COLUMN_ID+ " = ?", new String [] {id}, null, null, null);}

    // добавить запись в DB_TABLE
    public void addRec(String title, String link) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_LINK,link);
        mDB.insert(DB_TABLE, null, cv);
        Log.d(LOG_TAG, "RSS with title: " + title + " and link: " + link + " added!");
    }

    public void delAllRec() {
        mDB.delete(DB_TABLE,null,null);
    }

    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }
    public void delRecByTitle(String id) {
        mDB.delete(DB_TABLE, COLUMN_TITLE + " = '" + id+"'", null);
    }
    public void delRecByLink(String id) {
        mDB.delete(DB_TABLE, COLUMN_LINK + " = '" + id+"'", null);
    }


}
