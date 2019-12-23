package com.webstarterz.happycheetah.moviesstore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by User on 6/13/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "contacts.db";

    private static final String TABLE_NAME = "contacts_table";
    public static final String COL0 = "ID";
    public static final String COL1 = "NAME";
    public static final String COL2 = "PHONE_NUMBER";
    public static final String COL3 = "IMAGE_URL";


    private static final String USER_TABLE_NAME = "user_table";
    public static final String col0 = "ID";
    public static final String col1 = "USER_ID";



    moviesAlblum moviesAlblum;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " +
                TABLE_NAME + " ( " +
                COL0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL1 + " TEXT, " +
                COL2 + " TEXT, " +
                COL3 + " TEXT )";

        String sql1 = "CREATE TABLE " +
                USER_TABLE_NAME + " ( " +
                col0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                col1 + " TEXT )";

        db.execSQL(sql);
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }



    public boolean addContact(String name,String year,String url) {
        boolean boole;
        SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL1, name);
            contentValues.put(COL2, year);
            contentValues.put(COL3, url);

            long result = db.insert(TABLE_NAME, null, contentValues);

            if (result == -1) {
                boole = false;
            } else {
                boole = true;
            }


        return boole;
    }

    /**
     * Retrieve all contacts from database
     * @return
     */


    public Cursor getAllContacts(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    /**
     * Update a contact where id = @param 'id'
     * Replace the current contact with @param 'contact'
     * @param contact
     * @param id
     * @return
     */
    public boolean updateContact(moviesAlblum contact, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, contact.getName());
        contentValues.put(COL2, contact.getCodeno());
        int update = db.update(TABLE_NAME, contentValues, COL0 + " = ? ", new String[] {String.valueOf(id)} );

        if(update != 1) {
            return false;
        }
        else{
                return true;
            }
    }

    /**
     * Retrieve the contact unique id from the database using @param
     //* @param contact
     * @return
     */
    public Cursor getContactID(String name,String year){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME  +
                " WHERE " + COL2 + " = '" + year + "'";
        return db.rawQuery(sql, null);
    }

    public Integer deleteContact(String str){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "NAME = ?", new String[] {str});
    }

    public boolean insertID(String str){
        boolean boole;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, str);
        long result = db.insert(USER_TABLE_NAME, null, contentValues);

        return true;
    }

    public Cursor getID(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + USER_TABLE_NAME, null);
    }

}







