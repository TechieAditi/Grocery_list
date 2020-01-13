package com.elab.grocery_list.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import androidx.annotation.Nullable;

import com.elab.grocery_list.Model.Grocery;
import com.elab.grocery_list.Util.Constant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Databasehandler extends SQLiteOpenHelper {
    private Context ctx;
    public Databasehandler(@Nullable Context context){

        super(context, Constant.dbname, null, Constant.db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_GROCERY_TABLE = "CREATE TABLE " + Constant.tablename+ "("
                + Constant.key_id + " INTEGER PRIMARY KEY," + Constant.key_grocery_item + " TEXT,"
                + Constant.key_qty + " TEXT,"
                + Constant.key_date + " LONG);";

        sqLiteDatabase.execSQL(CREATE_GROCERY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constant.tablename);
        onCreate(sqLiteDatabase);

    }
    //crud opeartion
//adc grocery
    public void addgrocery(Grocery grocery)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.key_grocery_item, grocery.getName());
        values.put(Constant.key_qty, grocery.getQty());
        values.put(Constant.key_date, java.lang.System.currentTimeMillis());

        //Insert the row
        db.insert(Constant.tablename, null, values);

        Log.d("Saved!!", "Saved to DB");
    }
    //get a grocery item
    public Grocery getgrocery(int id)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(Constant.tablename, new String[] {Constant.key_id,
                        Constant.key_grocery_item, Constant.key_qty, Constant.key_date},
                Constant.key_id + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();



            Grocery grocery = new Grocery();
            grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.key_id))));
            grocery.setName(cursor.getString(cursor.getColumnIndex(Constant.key_grocery_item)));
            grocery.setQty(cursor.getString(cursor.getColumnIndex(Constant.key_date)));

            //convert timestamp to something readable
            java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
            String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constant.key_date)))
                    .getTime());

            grocery.setDate(formatedDate);


        return grocery;
    }
    //get all grocery
    public List<Grocery> getallgrocery() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Grocery> groceryList = new ArrayList<>();

        Cursor cursor = db.query(Constant.tablename, new String[] {
                Constant.key_id, Constant.key_grocery_item, Constant.key_qty,
                Constant.key_date}, null, null, null, null, Constant.key_date + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Grocery grocery = new Grocery();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.key_id))));
                grocery.setName(cursor.getString(cursor.getColumnIndex(Constant.key_grocery_item)));
                grocery.setQty(cursor.getString(cursor.getColumnIndex(Constant.key_qty)));

                //convert timestamp to something readable
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constant.key_date)))
                        .getTime());

                grocery.setDate(formatedDate);

                // Add to the groceryList
                groceryList.add(grocery);

            }while (cursor.moveToNext());
        }

        return groceryList;
    }
    //update grocery
    public int updategrocery(Grocery grocery)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.key_grocery_item, grocery.getName());
        values.put(Constant.key_qty, grocery.getQty());
        values.put(Constant.key_date, java.lang.System.currentTimeMillis());//get system time

        //return db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + "=?", new String[] { String.valueOf(grocery.getId())} );
    //}

        return db.update(Constant.tablename, values, Constant.key_id + "=?", new String[] {String.valueOf(grocery.getId())});
    }
    //delete grocery
    public void deletgrocery(int id)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Constant.tablename,Constant.key_id + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
    //count grocery
    public int getgrocerycount()
    {
        String count= "SELECT * FROM " + Constant.tablename;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(count,null);
        return cursor.getCount();

    }

}
