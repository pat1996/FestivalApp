package com.example.patri.festivalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "21.05.15_DB";
    private static final int DB_VERSION = 1;

    private static final String SETTINGS_CREATE = "CREATE TABLE IF NOT EXISTS Settings (Username TEXT, Password TEXT)";

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SETTINGS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertIntoTable(String table, Object object) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues objectTable = convertObjectIntoSettings(object);
        db.insert(table, null, objectTable);
    }

    private ContentValues convertObjectIntoSettings(Object object) {
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
