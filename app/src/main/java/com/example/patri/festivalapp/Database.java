package com.example.patri.festivalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static android.os.Build.ID;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "Festivalapp_DB.sqlite";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "classTable";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_PRICE = "price";
    public static final String ID = "id";

    private static final String SETTINGS_CREATE = "CREATE TABLE IF NOT EXISTS Settings (Username TEXT, Password TEXT)";
    private static final String COST_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_NAME_NAME + " TEXT, " + COLUMN_NAME_PRICE + " REAL " + ");";

    private Context context;
    private static final String SETTINGS_SELECT = "SELECT * FROM Settings";

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SETTINGS_CREATE);
        System.out.println("Database created");
        db.execSQL(COST_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
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

    public long insertCostEntry(String name, Double price) {
        Database cDB = new Database(context);
        SQLiteDatabase db = cDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Id and date are automatically generated (see SQL_CREATE_ENTRIES)
        values.put(COLUMN_NAME_NAME, name);
        values.put(COLUMN_NAME_PRICE, price);
        return db.insert(TABLE_NAME, null, values);
    }

    public ArrayList<Cost> readCostData () {
        Database cDB = new Database(context);
        SQLiteDatabase db = cDB.getWritableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection =
                {
                        ID,
                        String.valueOf(COLUMN_NAME_NAME),
                        String.valueOf(COLUMN_NAME_PRICE)
                };
        // How you want the results sorted in the resulting Cursor
        String sortOrder = COLUMN_NAME_PRICE + " DESC";
        Cursor cursor = db.query(TABLE_NAME, // The table to query
                projection, // The columns to return
                null, // The columns for the WHERE clause
                null, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                sortOrder // The sort order
        );
        cursor.moveToFirst();
        int rowCount = cursor.getCount();

        System.out.println(" >>> rowCount = "+rowCount);
        System.out.println(" >>> number of columns = "+cursor.getColumnNames().length);
        System.out.println(" >>> getColumnName = "+(cursor.getColumnName(0)));
        System.out.println(" >>> getColumnName = "+(cursor.getColumnName(1)));
        System.out.println(" >>> getColumnName = "+(cursor.getColumnName(2)));

        ArrayList<Cost> costEntries = new ArrayList();
        for (int i = 0; i < rowCount; ++i) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)));
            System.out.println(" >>> ("+i+") _ID   = "+id);

            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME));
            System.out.println(" >>> ("+i+") name  = "+name);

            double price = cursor.getDouble(cursor.getColumnIndex(String.valueOf(COLUMN_NAME_PRICE)));
            System.out.println(" >>> ("+i+") price = "+price);
            Cost cost = new Cost (name, price);
            cost.setId(id);
            costEntries.add(cost);
            cursor.moveToNext();
        }
        return costEntries;
    }

    public Cost readEntryById(int id) {
        Database cDB = new Database(context);
        SQLiteDatabase db = cDB.getWritableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {ID, COLUMN_NAME_NAME,
                String.valueOf(COLUMN_NAME_PRICE)};
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME, // The table to query
                projection, // The columns to return
                ID + " = ?", // The columns for the WHERE clause
                selectionArgs, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // No sorting required for only one entry
        );
        boolean success = false;
        if (cursor != null) {
            success = cursor.moveToFirst();
        }
        if (!success) {
            return null;
        }

        String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME));
        Double price = cursor.getDouble(cursor.getColumnIndex(String.valueOf(COLUMN_NAME_PRICE)));
        cursor.close();
        Cost cost = new Cost(name, price);
        cost.setId(id);
        return cost;
    }

    public long updateEntry(int id, String name, double price) {
        Database cDB = new Database(context);
        SQLiteDatabase db = cDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Id and date are automatically generated (see SQL_CREATE_ENTRIES)
        values.put(COLUMN_NAME_NAME, name);
        values.put(String.valueOf(COLUMN_NAME_PRICE), price);
        // Define 'where' part of query
        String selection = ID + " = ?";
        Log.d("CostDB", "Update entry with ID: " + id);
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(id)};
        return db.update(TABLE_NAME, values, selection, selectionArgs);
    }

    public int removeEntry(int id) {
        Database cDB = new Database(context);
        SQLiteDatabase db = cDB.getWritableDatabase();
        // Define 'where' part of query
        String selection = ID + " = ?";
        Log.d("CostDB", "Delete entry with ID: " + id);
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(id)};
        // Issue SQL statement.
        return db.delete(TABLE_NAME, selection, selectionArgs);
    }

}
