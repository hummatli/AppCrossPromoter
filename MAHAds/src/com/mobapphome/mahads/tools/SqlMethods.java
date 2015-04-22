package com.mobapphome.mahads.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.mobapphome.mahads.types.Program;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;
import android.util.Log;

public class SqlMethods {
	static DateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
	static long ONE_MONTTH_MILLI_SEC = 1000L* 60 * 60 * 24 * 30;


	//---------------------------------------------Currencies----------------------------------------------
	static public int deleteAllPrograms(Context context){
        SqlLite myDbHelper =  new SqlLite(context);
        SQLiteDatabase db = myDbHelper.getWritableDatabase(); 
        int ret = db.delete("programs","1 = 1", null);
        db.close();
        myDbHelper.close();
        Log.i("Test", "Delete result = " + ret);
        return ret;
	}
	
	
	static public long insertProgram(Context context,Program currency){
		//For insertion new row id must be primery key.
		//For example CREATE TABLE cards ("id" INTEGER  primary key, "name" TEXT)

        SqlLite myDbHelper =  new SqlLite(context);
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
	    ContentValues contentValues = new ContentValues();

	    contentValues.put("name", currency.getName());
	    contentValues.put("desc", currency.getDesc());
	    contentValues.put("uri", currency.getUri());	
	    contentValues.put("img", currency.getImg());
	    contentValues.put("release_date", currency.getRelease_date());
	    long ret = db.insert("programs", null, contentValues);
	    db.close();
	    myDbHelper.close();
	    Log.i("Test", "Program inserted = " + currency.getName() + "Id = " + ret);
	    return ret;
	}
	
	static public List<Program> readAllPrograms(Context context){
        Log.i("Test", "ReadAllCurrency");
        List<Program> retItems = new LinkedList<>();
		SqlLite myDbHelper =  new SqlLite(context);
        SQLiteDatabase db = myDbHelper.getReadableDatabase();  
        
        Cursor cursor = db.rawQuery("SELECT id, name, desc, uri, img, release_date FROM programs", null);
        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String desc = cursor.getString(cursor.getColumnIndex("desc"));
                    String uri = cursor.getString(cursor.getColumnIndex("uri"));
                    String img= cursor.getString(cursor.getColumnIndex("img"));
                    String releaseDate = cursor.getString(cursor.getColumnIndex("release_date"));
                    Program program = new Program(id, name, desc, uri, img, releaseDate);
                    try{
                    	Date releaseDateAsDate = DATE_FORMATTER.parse(releaseDate);
                        program.setReleaseDateAsDate(releaseDateAsDate);
					}catch(java.text.ParseException e){
						Log.i("Test", "Paresing program date Exception: " + e.getMessage());
					}
                    
                    if(program.getReleaseDateAsDate() != null){
                    	long dateToday = new Date().getTime();
                    	long dateRelease = program.getReleaseDateAsDate().getTime();
                    	long diff = dateToday - dateRelease;
                    	//Log.i("Test", "Program dates  = " + dateToday + ", "+ dateRelease +","+diff +","+ONE_MONTTH_MILLI_SEC);
                    	if( diff <= ONE_MONTTH_MILLI_SEC){
                    		program.setNewPrgram(true);
                    		Log.i("Test", "Program new = " + program.getName() + " date = " + program.getReleaseDateAsDate());
                    	}
                }

                    retItems.add(program);
                    //Log.i("Test", "Card id = " + id);
                }while (cursor.moveToNext());
            } 
        }
        cursor.close();
        db.close();
        myDbHelper.close();
        return retItems;
	}
	
	
	//-----------------------------------Values----------------------------------------------------------
	
	static public long insertValue(Context context, String name, String value){
        SqlLite myDbHelper =  new SqlLite(context);
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
	    ContentValues contentValues = new ContentValues();

	    contentValues.put("name", name);
	    contentValues.put("val", value);
	    long ret = db.insert("key_val", null, contentValues);
	    db.close();
	    myDbHelper.close();
	    return ret;
	}
	
	static public String readValue(Context context, String name){
        String value = null;
		SqlLite myDbHelper =  new SqlLite(context);
        SQLiteDatabase db = myDbHelper.getReadableDatabase();  
        
        Cursor cursor = db.rawQuery("SELECT val FROM key_val where name = '" + name + "'", null);
        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    value = cursor.getString(cursor.getColumnIndex("val"));
                }while (cursor.moveToNext());
            } 
        }
        cursor.close();
        db.close();
        myDbHelper.close();
        return value;
	}
	
	static public int updateValue(Context context, String name, String value){
        SqlLite myDbHelper =  new SqlLite(context);
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        
	    contentValues.put("val", value);
	    int ret = db.update("key_val", contentValues, "name = ? ", new String[] { name } );
	    db.close();
	    myDbHelper.close();
	    
	    return ret;
	}
	
	static public int deleteValue(Context context, String name){
        SqlLite myDbHelper =  new SqlLite(context);
        SQLiteDatabase db = myDbHelper.getWritableDatabase(); 
        int ret = db.delete("key_val","name = ?", new String[] { name } );
        db.close();
        myDbHelper.close();
        Log.i("Test", "Delete result = " + ret);
        return ret;
	}
}
