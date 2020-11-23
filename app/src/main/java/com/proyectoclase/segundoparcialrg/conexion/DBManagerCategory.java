package com.proyectoclase.segundoparcialrg.conexion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
public class DBManagerCategory {
    private DataBaseHelper helper;
    private Context context;
    private SQLiteDatabase database;
    public DBManagerCategory(Context context) {
        this.context = context;
    }
    public DBManagerCategory open() throws SQLException {
        helper = new DataBaseHelper(this.context);
        database = helper.getWritableDatabase();
        return this;
    }
    public void close() {
        helper.close();
    }
    public void insert(String name) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NAME_CATEGOTY, name);
        database.insert(DataBaseHelper.TABLE_NAME_CATEGOIES, null, values);
        System.out.println(findAll().moveToNext());;
    }
    public Cursor findAll() {
        String[] columns = new String[]{DataBaseHelper.ID_CATEGORY, DataBaseHelper.NAME_CATEGOTY};
        //Cursor cursor = database.query(DataBaseHelper.TABLE_NAME_CATEGOIES, columns, null, null, null, null, null);
        Cursor cursor = database.rawQuery("SELECT * FROM CATEGORY",null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void Update(String old,String name){
        String selection = DataBaseHelper.NAME_CATEGOTY+" LIKE ? ";
        String args[] = {old};
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NAME_CATEGOTY,name);
        int num = database.update(DataBaseHelper.TABLE_NAME_CATEGOIES,values,selection,args);
    }

    public void delete(String old){
        String selection = DataBaseHelper.NAME_CATEGOTY+" LIKE ? ";
        String args[] = {old};

        database.delete(DataBaseHelper.TABLE_NAME_CATEGOIES,selection,args);


    }
}
