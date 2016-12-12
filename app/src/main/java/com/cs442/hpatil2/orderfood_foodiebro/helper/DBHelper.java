package com.cs442.hpatil2.orderfood_foodiebro.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cs442.hpatil2.orderfood_foodiebro.constants.Constants;
import com.cs442.hpatil2.orderfood_foodiebro.model.Orders;

import java.util.ArrayList;

/**
 * Created by HarshPatil on 10/6/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    Context context;

    public DBHelper(Context context){
        super(context, Constants.DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists orders (id integer primary key, items text, itemNames text, date text, amount text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.ORDER_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertOrder(String itemIds, String itemNames, String date, float orderAmount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.ORDER_ITEM_IDS, itemIds);
        contentValues.put(Constants.ORDER_ITEM_NAMES, itemNames);
        contentValues.put(Constants.ORDER_DATE, date);
        contentValues.put(Constants.ORDER_AMOUNT, orderAmount);
        db.insert(Constants.ORDER_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + Constants.ORDER_TABLE_NAME +" where id="+id, null);
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, Constants.ORDER_TABLE_NAME);
        return numRows;
    }

    public Integer deleteOrders (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Constants.ORDER_TABLE_NAME, "id = ? ", new String[] { Integer.toString(id) });
    }

    public void deleteDatabaseAndTable (){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS orders");
        context.deleteDatabase(Constants.DATABASE_NAME);
    }

    public ArrayList<Orders> getAllOrders()
    {
        ArrayList<Orders> orderList = new ArrayList<Orders>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet =  db.rawQuery( "select * from " + Constants.ORDER_TABLE_NAME, null);
        resultSet.moveToLast();

        while(resultSet.isBeforeFirst() == false){
            Orders orders = new Orders();
            orders.setItemIds(resultSet.getString(resultSet.getColumnIndex(Constants.ORDER_ITEM_IDS)));
            orders.setNames(resultSet.getString(resultSet.getColumnIndex(Constants.ORDER_ITEM_NAMES)));
            orders.setTotalOrderAmount(Float.parseFloat(resultSet.getString(resultSet.getColumnIndex(Constants.ORDER_AMOUNT))));
            orders.setDate(resultSet.getString(resultSet.getColumnIndex(Constants.ORDER_DATE)));
            orderList.add(orders);
            resultSet.moveToPrevious();
        }
        return orderList;
    }
}
