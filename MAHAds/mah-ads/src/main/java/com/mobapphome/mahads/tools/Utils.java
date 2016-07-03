package com.mobapphome.mahads.tools;

import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class Utils {
	
	public static boolean checkPackageIfExists(Context context, String pckgName)
	{
	    try{
	        ApplicationInfo info = context.getPackageManager().getApplicationInfo(pckgName, 0 );
	        return true;
	    } catch( PackageManager.NameNotFoundException e ){
	        return false;
	    }
	}
}





