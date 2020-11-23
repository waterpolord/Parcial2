package com.proyectoclase.segundoparcialrg.conexion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManagerProduct {
    private DataBaseHelper helper;
    private Context context;
    private SQLiteDatabase database;
    public DBManagerProduct(Context context) {
        this.context = context;
    }
    public DBManagerProduct open() throws SQLException {
        helper = new DataBaseHelper(this.context);
        database = helper.getWritableDatabase();
        return this;
    }
    public void close() {
        helper.close();
    }
    public void insert(String name,Double price,Integer id) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NAME_PRODUCT, name);
        values.put(DataBaseHelper.PRICE,price);
        values.put(DataBaseHelper.ID_CATEGORY,id);
        database.insert(DataBaseHelper.TABLE_NAME_PRODUCT, null, values);
    }

    public Cursor findAllProductsAndCategories() {
        String[] columns = new String[]{DataBaseHelper.ID_CATEGORY, DataBaseHelper.NAME_CATEGOTY};
        //Cursor cursor = database.query(DataBaseHelper.TABLE_NAME_CATEGOIES, columns, null, null, null, null, null);
        Cursor cursor = database.rawQuery("SELECT PRODUCT.name,PRODUCT.price,CATEGORY.name FROM PRODUCT INNER JOIN CATEGORY ON CATEGORY.id_category = PRODUCT.id_category " +
                "ORDER BY CATEGORY.name",null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void update(String name, Double price, Integer cat, String old){
        String selection = DataBaseHelper.NAME_PRODUCT+" LIKE ? ";
        String args[] = {old};
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NAME_PRODUCT,name);
        values.put(DataBaseHelper.PRICE,price);
        values.put(DataBaseHelper.ID_CATEGORY,cat);
        int num = database.update(DataBaseHelper.TABLE_NAME_PRODUCT,values,selection,args);


    }

    public void delete(String old){
        String selection = DataBaseHelper.NAME_PRODUCT+" LIKE ? ";
        String args[] = {old};

        database.delete(DataBaseHelper.TABLE_NAME_PRODUCT,selection,args);


    }




}
