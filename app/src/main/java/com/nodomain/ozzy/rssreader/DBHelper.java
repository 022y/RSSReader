package com.nodomain.ozzy.rssreader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// класс по созданию и управлению БД
public class DBHelper extends SQLiteOpenHelper {


    public static final String DB_NAME = "RSSDB";
    public static final int DB_VERSION = 1;


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    // создаем и заполняем БД
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB.DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}