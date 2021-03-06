package com.example.basma.cardsgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by basma on 10/03/18.
 */



    public class DatabaseHelper extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "Players.db";
        public static final String TABLE_NAME = "players_table";
        public static final String COL_1 = "ID";
        public static final String COL_2 = "NAME";
        public static final String COL_3 = "SCORE";
        public static final String COL_4 = "DATE";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SCORE TEXT,DATE INTEGER)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
        }

        public boolean insertData(String name,String score,String date) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2,name);
            contentValues.put(COL_3,score);
            contentValues.put(COL_4,date);
            long result = db.insert(TABLE_NAME,null ,contentValues);
            if(result == -1)
                return false;
            else
                return true;
        }

        public Cursor getAllData() {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
            return res;
        }

        public boolean updateData(String id,String name,String score,String date) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1,id);
            contentValues.put(COL_2,name);
            contentValues.put(COL_3,score);
            contentValues.put(COL_4,date);
            db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
            return true;
        }

        public Integer deleteData (String id) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
        }

}
