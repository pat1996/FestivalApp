package com.example.patri.festivalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "Festivalapp_DB";
    private static final int DB_VERSION = 1;

    private static final String SETTINGS_CREATE = "CREATE TABLE IF NOT EXISTS Settings (Username TEXT, Password TEXT)";
    private static final String SETTINGS_SELECT = "SELECT * FROM Settings";

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SETTINGS_CREATE);
        System.out.println("Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertIntoTable(String table, Object object) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues objectTable = convertObjectIntoContenValues(object);
        long insert = db.insert(table, null, objectTable);
        System.out.println("Object saved: " + insert);
    }

    public Cursor selectAllFromTable(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        switch (table) {
            case "Settings":
                res = db.rawQuery(SETTINGS_SELECT, null);
                break;
        }
        return res;
    }

    public Cursor selectFromTable(String table, String sql) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(sql, null);
        return res;
    }

    private ContentValues convertObjectIntoContenValues(Object object) {
        ContentValues content = new ContentValues();
        switch (object.getClass().getSimpleName()) {
            case "SettingsDB":
                SettingsDB settings = (SettingsDB) object;
                content.put("Username", settings.getUsername());
                content.put("Password", settings.getPassword());
                break;
        }
        return content;
    }
}
