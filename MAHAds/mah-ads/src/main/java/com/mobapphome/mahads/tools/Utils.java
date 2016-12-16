package com.mobapphome.mahads.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class Utils {

    public static boolean checkPackageIfExists(Context context, String pckgName) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(pckgName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void writeStringToCache(final Activity act, String stringToCache) {
        FileOutputStream outputStream;

        try {
            outputStream = act.openFileOutput(MAHAdsController.PROGRAM_LIST_CACHE, Context.MODE_PRIVATE);
            outputStream.write(stringToCache.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readStringFromCache(final Activity act) {
        FileInputStream inputStream;

        try {
            inputStream = act.openFileInput(MAHAdsController.PROGRAM_LIST_CACHE);

            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            inputStream.close();
            return total.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

