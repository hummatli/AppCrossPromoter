package com.mobapphome.mahads.tools;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Arrays;

import com.mobapphome.mahads.R;
import com.mobapphome.mahads.R.raw;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
 
	public class SqlLite extends SQLiteOpenHelper{
	     
	    public SQLiteDatabase DB;
	    public String DBPath;
	    public static String DBName = "mah_ads_dat32";
	    public static final int version = '1';
	    public static Context currentContext;
	    //public static String tableName = "names";
	     
	 
	    public SqlLite(Context context) {
	        super(context, DBName, null, version);
	        Log.i("Test", "SqlLite constructor");
	        
	        currentContext = context;
	        DBPath = "/data/data/" + context.getPackageName() + "/databases/";
            Log.i("Test", "Out file name = " + DBPath + DBName);

           	//This is for backup db from phone to sd card. When want to use add this permission to Manafest
            //<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />    
            //backupDB(DBPath+DBName, "currencybackup.db");
 
            createDatabase();
	 
	    }
	 
	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        // TODO Auto-generated method stub
	         
	    }
	 
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // TODO Auto-generated method stub
	    	Log.i("Test", "Version changed");
	    	copyDataBase();
	         
	    }
	 
	    private void createDatabase() {
	        boolean dbExists = checkDbExists();
        	Log.i("Test", "Check db is :" + dbExists);
	        if (dbExists) {
	            // do nothing 
	        } else {

	        	DB = currentContext.openOrCreateDatabase(DBName, 0, null);
	        	copyDataBase();
	        }
	         
	         
	    }
	     
	    public void backupDB(String currentDBPath, String backupDBPath){
	    	try {
                File sd = Environment.getExternalStorageDirectory();
                File data = Environment.getDataDirectory();

                if (sd.canWrite()) {
                    //String currentDBPath = "/data/" + getPackageName() + "/databases/yourdatabasename";
                    //String backupDBPath = "backupname.db";
                    File currentDB = new File(currentDBPath);
                    File backupDB = new File(sd, backupDBPath);

                    if (currentDB.exists()) {
                        FileChannel src = new FileInputStream(currentDB).getChannel();
                        FileChannel dst = new FileOutputStream(backupDB).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();
                    }
                }
            } catch (Exception e) {

            }

	    }

	    
	    private void copyDataBase()  {
	        try {
//		    	if(!Arrays.asList(currentContext.getAssets().list("")).contains(db)){
//		    		Log.i("Test", "File db : " + db + " is not exits in assets");
//		    		return;
//		    	}
		    	Log.i("Test", "Copies db");
		    	//This copies from resource folder. Because in lib project we can not access assets folder bu to res can
		    	InputStream mInputStream = currentContext.getResources().openRawResource(R.raw.mahads_db);
		    	//String db = "mahads.db";
	            //InputStream mInputStream = currentContext.getAssets().open(db);
	            String outFileName = DBPath + DBName;
	            OutputStream mOutputStream = new FileOutputStream(outFileName);
	            byte[] buffer = new byte[1024];
	            int length;
	            while ((length = mInputStream.read(buffer)) > 0) {
	                mOutputStream.write(buffer, 0, length);
	            }
	            mOutputStream.flush();
	            mOutputStream.close();
	            mInputStream.close();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        	Toast.makeText(currentContext.getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show(); 	

	        }
	    }    
	    
	    
	    private boolean checkDbExists() {
	        SQLiteDatabase checkDB = null;
	 
	        try {
	            String myPath = DBPath + DBName;
	            checkDB = SQLiteDatabase.openDatabase(myPath, null,
	                    SQLiteDatabase.OPEN_READONLY);
	 
	        } catch (SQLiteException e) {
	 
	            // database does't exist yet.
	 
	        }
	 
	        if (checkDB != null) {
	 
	            checkDB.close();
	 
	        }
	 
	        return checkDB != null ? true : false;
	    }
	}