package edu.nccumis.andy.maptest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "mapsdb.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlMap = "create table marks(id INTEGER PRIMARY KEY AUTOINCREMENT, mark_name text," +
                " mark_info text, longit numeric, latit numeric)";
        db.execSQL(sqlMap);

        String initMap = "insert into marks values(null, 'New Mark','Auto generated mark.', '25" +
                ".0329640', '121.5654270')";
        db.execSQL(initMap);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists marks");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists marks");
        onCreate(db);
    }
}
