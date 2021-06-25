package com.example.final_projects.Users_Details;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class User_DataBase extends SQLiteOpenHelper {
    public User_DataBase(Context context) {
        super(context, "My_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table UserDetails(name TEXT primary key , bio TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists UserDetails");
    }

    //===========================================================================
    //Method For Insert data into SQLite Database Table (UserDetails)
    public Boolean InsertUserData(String Name, String Bio) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", Name);
        contentValues.put("bio", Bio);
        long result = DB.insert("UserDetails ", null, contentValues);
        if (result == -1) {
            return false;
        } else
            return true;
    }

    //===========================================================================
    //Method For Update data into SQLite Database Table (UserDetails)
    public Boolean UpdateUserData(String Name, String Bio) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", Name);
        contentValues.put("bio", Bio);
        Cursor cursor;
        cursor = DB.rawQuery("Select * from UserDetails where name = ?", new String[]{Name});
        if (cursor.getCount() > 0) {

            long result = DB.update("UserDetails", contentValues, "name=?", new String[]{Name});
            return result != -1;
        } else {
            return false;
        }

    }

    //===========================================================================
    //Method For Delete data into SQLite Database Table (UserDetails)
    public Boolean DeleteDataUser(String Name, String Bio) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor;
        cursor = DB.rawQuery("Select * from UserDetails where name =?", new String[]{Name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("UserDetails ", "name=?", new String[]{Name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    //===========================================================================
    //Method For Get data From SQLite Database Table (UserDetails) Using Cursor
    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor;
        cursor = DB.rawQuery("Select * from UserDetails ", null);
        return cursor;
    }
}
