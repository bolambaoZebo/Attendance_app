package com.example.attendance_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBcontroller extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "attndanceIncrement04.db";


    public static final String ATTENDANCE_TABLE = "login";
    public static final String Col_id = "id";
    public static final String Col_name = "firstname";
    public static final String Col_middle = "middlename";
    public static final String Col_last = "lastname";
    public static final String Col_age = "age";
    public static final String Col_gender = "gender";


    public static final String STUDENTQR_TABLE = "Scode";
    public static final String Col_slid = "id";
    public static final String Col_sltitle = "Seminar_title";
    public static final String Col_sldate = "Seminar_date";
    private static final String TAG = "TAG" ;


    public  DBcontroller(Context context){
        super(context,DATABASE_NAME,null,1);
        SQLiteDatabase db = this.getWritableDatabase();

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ ATTENDANCE_TABLE+"(id INTEGER PRIMARY KEY,firstname TEXT,middlename TEXT,lastname TEXT)");
        db.execSQL("CREATE TABLE "+ STUDENTQR_TABLE+"(id INTEGER PRIMARY KEY  AUTOINCREMENT,Seminar_title TEXT,Seminar_date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +ATTENDANCE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +STUDENTQR_TABLE);

        onCreate(db);
    }

    public  boolean insertReg(String id,String fname,String midname,String lname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Col_id,id);
        contentValues.put(Col_name,fname);
        contentValues.put(Col_middle,midname);
        contentValues.put(Col_last,lname);

        long result = db.insert(ATTENDANCE_TABLE,null,contentValues);
        db.close();
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }//end of insert method


    //insert method for the student attended seminars
    public  boolean insertATTDSEMINAR(String title,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Col_sltitle,title);
        contentValues.put(Col_sldate,date);

        long result = db.insert(STUDENTQR_TABLE,null,contentValues);
        db.close();
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getLogIndata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ATTENDANCE_TABLE,null);
        return res;
    }//end of getLogIndata method


    //method for getting all data from STUDENTQR_table
    public Cursor getStudnetlist(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+STUDENTQR_TABLE,null);
        return res;
    }//end of getStudentlist method



    public ArrayList<SemiAttended> getAllSeminar() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SemiAttended> array_list = new ArrayList<>();
        Cursor cursor = db.rawQuery(" SELECT*FROM " +STUDENTQR_TABLE,null);


                while(cursor.moveToNext()){
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    String date = cursor.getString(2);
                    SemiAttended semiAttended = new SemiAttended(id,title,date);

                    array_list.add(semiAttended);
                }



        return array_list;
    }


    public void deleteList(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM " + STUDENTQR_TABLE + " WHERE "
                + Col_slid + " = '" + id + "'";

        Log.d(TAG, "deleteName: query: " + query);

        Log.d(TAG, "deleteName: Deleting from database.");

        db.execSQL(query);
    }
}
