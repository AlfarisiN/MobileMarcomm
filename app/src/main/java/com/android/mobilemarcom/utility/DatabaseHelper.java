package com.android.mobilemarcom.utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Eric on 16/10/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    final static String DATABASE_NAME = "database_bootcamp.db";
    final static int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE `bootcamp` (" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "`code` TEXT," +
                " `name` TEXT, " +
                " `type` TEXT " +
                ");";

        db.execSQL(query);
        System.out.println("Tabel sudah di create");

//        String queryInsertTabel = "INSERT INTO `bootcamp`(`code`,`name`,`type`) VALUES (" +
//                "Q001" +
//                "," +
//                "'Bootcamp'" +
//                "," +
//                "'L'" +
//                ");";
//        db.execSQL(queryInsertTabel);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
