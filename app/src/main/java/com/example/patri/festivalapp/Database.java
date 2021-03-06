package com.example.patri.festivalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "Festivalapp_DB.sqlite";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "classTable";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_PRICE = "price";
    public static final String ID = "id";


    private static final String COST_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_NAME_NAME + " TEXT, " +
            COLUMN_NAME_PRICE + " REAL " + ");";
    private static final String COST_SELECT = "SELECT * FROM Cost;";
    private static final String COST_DROP = "DROP TABLE IF EXISTS Cost;";

    private static final String PACKINGLIST_CREATE = "CREATE TABLE IF NOT EXISTS PackingList" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "Name TEXT," +
            "IsChecked BOOLEAN);";
    private static final String PACKINGLIST_SELECT = "SELECT * FROM PackingList;";
    private static final String PACKINGLIST_INSERT_DEFAULT = "INSERT INTO PackingList (Name, IsChecked) VALUES " +
            "('Ticket', 0)," +
            "('Dosenravioli', 0)," +
            "('Grillfleisch', 0)," +
            "('Zigaretten', 0)," +
            "('Kekse', 0)," +
            "('Muesli', 0)," +
            "('Milchbrötchen', 0)," +
            "('Nutella', 0)," +
            "('Salzstangen', 0)," +
            "('Erdnuesse', 0)," +
            "('Milch', 0)," +
            "('Asbach', 0)," +
            "('Havana', 0)," +
            "('Cola', 0)," +
            "('Goas-Mass', 0)," +
            "('Vodka-Lemon', 0)," +
            "('Apple Cider', 0)," +
            "('Wein', 0)," +
            "('Bier', 0)," +
            "('Wasser', 0)," +
            "('Hugo', 0)," +
            "('43er Schnaps', 0)," +
            "('Duschgel', 0)," +
            "('Handtuch', 0)," +
            "('Zahnpasta', 0)," +
            "('Zahnbürste', 0)," +
            "('Kamm', 0)," +
            "('Socken', 0)," +
            "('Unterhosen', 0)," +
            "('T-Shirt', 0)," +
            "('Hosen, lang', 0)," +
            "('Hosen, kurz', 0)," +
            "('Regenschirm', 0)," +
            "('Hut', 0)," +
            "('Sonnenbrille', 0)," +
            "('Muetze', 0)," +
            "('Capmingstuhl', 0)," +
            "('Zelt', 0)," +
            "('Schlafsack', 0)," +
            "('Decke', 0)," +
            "('Kissen', 0)," +
            "('Messer', 0)," +
            "('Gabel', 0)," +
            "('Teller', 0)," +
            "('Topf', 0)," +
            "('Powerbank', 0)," +
            "('Tisch', 0)," +
            "('Pavilion', 0)," +
            "('Gaskocher', 0)," +
            "('Grill', 0)," +
            "('Grillkohle', 0)";

    private static final String PACKINGLIST_DROP = "DROP TABLE IF EXISTS PackingList;";

    private static final String COUNTDOWN_CREATE = "CREATE TABLE IF NOT EXISTS CountdownTable" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "festivalDate LONG);";
    private static final String COUNTDOWN_SELECT = "SELECT * FROM CountdownTable";
    private static final String COUNTDOWN_DROP = "DROP TABLE IF EXISTS CountdownTable";

    private static final String WEATHER_CREATE = "CREATE TABLE IF NOT EXISTS WeatherTable" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "City TEXT);";
    private static final String WEATHER_SELECT = "SELECT * FROM WeatherTable";
    private static final String WEATHER_DROP = "DROP TABLE IF EXISTS WeatherTable";


    private Context context;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COST_CREATE);
        db.execSQL(PACKINGLIST_CREATE);
        db.execSQL(PACKINGLIST_INSERT_DEFAULT);
        db.execSQL(COUNTDOWN_CREATE);
        db.execSQL(WEATHER_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void createTable(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        switch (table) {
            case "Cost":
                db.execSQL(COST_CREATE);
                break;
            case "PackingList":
                db.execSQL(PACKINGLIST_CREATE);
                break;
            case "CountdownTable":
                db.execSQL(COUNTDOWN_CREATE);
                break;
            case "WeatherTable":
                db.execSQL(WEATHER_CREATE);
                break;
        }
    }

    public void insertIntoTable(String table, Object object) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues objectTable = convertObjectIntoContentValues(object);

        //Damit immer nur ein Datum bzw. eine Stadt für das nächste Festival in der Tabelle steht
        if (table.equals("CountdownTable")) {
            db.execSQL(COUNTDOWN_DROP);
            db.execSQL(COUNTDOWN_CREATE);
        }
        if (table.equals("WeatherTable")) {
            db.execSQL(WEATHER_DROP);
            db.execSQL(WEATHER_CREATE);
        }

        db.insert(table, null, objectTable);
    }

    public Cursor selectAllFromTable(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        switch (table) {
            case "Cost":
                res = db.rawQuery(COST_SELECT, null);
                break;
            case "PackingList":
                res = db.rawQuery(PACKINGLIST_SELECT, null);
                break;
            case "CountdownTable":
                res = db.rawQuery(COUNTDOWN_SELECT, null);
                break;
            case "WeatherTable":
                res = db.rawQuery(WEATHER_SELECT, null);
                break;
        }
        return res;
    }

    public Cursor selectFromTable(String table, String sql) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(sql, null);
        return res;
    }

    public void dropTable(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        switch (table) {
            case "Cost":
                db.execSQL(COST_DROP);
                break;
            case "PackingList":
                db.execSQL(PACKINGLIST_DROP);
                break;
            case "CountdownTable":
                db.execSQL(COUNTDOWN_DROP);
                break;
            case "WeatherTable":
                db.execSQL(WEATHER_DROP);
                break;
        }
    }

    private ContentValues convertObjectIntoContentValues(Object object) {
        ContentValues content = new ContentValues();
        switch (object.getClass().getSimpleName()) {
            case "Cost":
                Cost cost = (Cost) object;
                content.put("name", cost.getName());
                content.put("price", cost.getPrice());
                break;
            case "PackingListItemDB":
                PackingListItemDB packingListItem = (PackingListItemDB) object;
                content.put("Name", packingListItem.getName());
                content.put("IsChecked", packingListItem.isChecked());
                break;
            case "GregorianCalendar":
                Calendar date = (Calendar) object;
                content.put("festivalDate", date.getTimeInMillis());
                break;
            case "CityDB":
                CityDB city = (CityDB) object;
                content.put("City", city.getCity());
                break;
        }
        return content;
    }

    // Method for inserting a new cost entry
    public long insertCostEntry(String name, Double price) {
        Database cDB = new Database(context);
        SQLiteDatabase db = cDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Id is automatically generated (see SQL_CREATE_ENTRIES)
        values.put(COLUMN_NAME_NAME, name);
        values.put(COLUMN_NAME_PRICE, price);
        return db.insert(TABLE_NAME, COLUMN_NAME_NAME, values);
    }

    // Method to read the cost data
    public ArrayList<Cost> readCostData() {
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

        ArrayList<Cost> costEntries = new ArrayList();
        for (int i = 0; i < rowCount; ++i) {
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME));
            double price = cursor.getDouble(cursor.getColumnIndex(String.valueOf(COLUMN_NAME_PRICE)));
            Cost cost = new Cost(name, price);
            cost.setId(id);
            costEntries.add(cost);
            cursor.moveToNext();
        }
        return costEntries;
    }

    // Method to read the entries by ID
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

    // Method to update a cost entry
    public long updateEntry(int id, String name, double price) {
        Database cDB = new Database(context);
        SQLiteDatabase db = cDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Id and date are automatically generated (see SQL_CREATE_ENTRIES)
        values.put(COLUMN_NAME_NAME, name);
        values.put(String.valueOf(COLUMN_NAME_PRICE), price);
        // Define 'where' part of query
        String selection = ID + " = ?";
        Log.d("Database", "Update entry with ID: " + id);
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(id)};
        return db.update(TABLE_NAME, values, selection, selectionArgs);
    }

    // Method to remove a cost entry
    public int removeEntry(int id) {
        Database cDB = new Database(context);
        SQLiteDatabase db = cDB.getWritableDatabase();
        // Define 'where' part of query
        String selection = ID + " = ?";
        Log.d("Database", "Delete entry with ID: " + id);
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(id)};
        // Issue SQL statement.
        return db.delete(TABLE_NAME, selection, selectionArgs);
    }
}
