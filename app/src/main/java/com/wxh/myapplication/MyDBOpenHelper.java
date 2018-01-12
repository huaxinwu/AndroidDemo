package com.wxh.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wxh on 2018/1/9 11:00
 */
public class MyDBOpenHelper extends SQLiteOpenHelper {

    /**
     *  实现父类构造器
     * @param context
     * @param name 数据库文件名
     * @param factory
     * @param version 版本号
     */
    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "my.db", null, 1);
    }

    /**
     *  数据库第一次创建时被调用
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE person(personid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20))");
    }

    /**
     *  软件版本号发生改变时调用
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE person ADD phone VARCHAR(12) NULL");
    }
}
