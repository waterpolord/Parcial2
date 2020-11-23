package com.proyectoclase.segundoparcialrg.conexion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "SHOP.DB";
    private static final int DB_VERSION = 1;
    // tabla category
    public static final String TABLE_NAME_CATEGOIES = "CATEGORY";
    public static final String NAME_CATEGOTY = "name";
    public static final String ID_CATEGORY = "id_category";
    // tabla product
    public static final String TABLE_NAME_PRODUCT = "PRODUCT";
    public static final String NAME_PRODUCT = "name";
    public static final String ID_PRODUCT = "id_product";
    public static final String PRICE = "price";


    private static final String TABLE_CATEGORY = "CREATE TABLE " + TABLE_NAME_CATEGOIES +
            "( " + ID_CATEGORY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME_CATEGOTY + " TEXT NOT NULL) ";

    private static final String TABLE_PRODUCT = "CREATE TABLE " + TABLE_NAME_PRODUCT +
            "( " + ID_PRODUCT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME_PRODUCT + " TEXT NOT NULL, "+PRICE+" REAL NOT NULL, "+ID_CATEGORY+" INTEGER NOT NULL," +
            " FOREIGN KEY ("+ID_CATEGORY+") REFERENCES "+TABLE_NAME_CATEGOIES+"("+ID_CATEGORY+") ) ";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CATEGORY);
        db.execSQL(TABLE_PRODUCT);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CATEGORY");
        db.execSQL("DROP TABLE IF EXISTS PRODUCT");
        onCreate(db);
    }
}
