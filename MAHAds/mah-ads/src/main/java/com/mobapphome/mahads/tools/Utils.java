package com.mobapphome.mahads.tools;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.mobapphome.mahads.R;
import com.mobapphome.mahads.types.MAHRequestResult;
import com.mobapphome.mahads.types.Program;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Utils {

    //General --------------------------------------------------------------------
    public static boolean checkPackageIfExists(Context context, String pckgName) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(pckgName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void writeStringToCache(final Context context, String stringToCache) {
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(MAHAdsController.PROGRAM_LIST_CACHE, Context.MODE_PRIVATE);
            outputStream.write(stringToCache.getBytes());
            outputStream.close();
        } catch (Exception e) {
            //e.printStackTrace();
            Log.d(MAHAdsController.LOG_TAG_MAH_ADS, "IOexception = " + e.getMessage(), e);
        }
    }

    public static String readStringFromCache(final Context context) {
        FileInputStream inputStream;

        try {
            inputStream = context.openFileInput(MAHAdsController.PROGRAM_LIST_CACHE);

            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            inputStream.close();
            return total.toString();
        } catch (Exception e) {
            Log.d(MAHAdsController.LOG_TAG_MAH_ADS, "IOexception = " +e.getMessage(), e);
            //e.printStackTrace();
        }
        return null;
    }

    public static int getVersionFromLocal() {
        int ret = MAHAdsController.getSharedPref().getInt(Constants.MAH_ADS_VERSION, -1);
        return ret;
    }


    public static String getUrlOfImage(String initialUrlForImage) {
        if (initialUrlForImage.startsWith("http://") ||
                initialUrlForImage.startsWith("https://")) {
            return initialUrlForImage;
        } else {
            return MAHAdsController.urlRootOnServer + initialUrlForImage;
        }
    }


    public static String getRootFromUrl(String urlStr) {
        String rootStr = urlStr.substring(0, urlStr.lastIndexOf('/') + 1);
        return rootStr;
    }

    static public void showMarket(Context context, String pckgName){
        Intent marketIntent = new Intent(Intent.ACTION_VIEW);
        marketIntent.setData(Uri.parse("market://details?id=" + pckgName));
        try {
            context.startActivity(marketIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, context.getString(R.string.mah_ads_play_service_not_found), Toast.LENGTH_LONG).show();
            Log.e(MAHAdsController.LOG_TAG_MAH_ADS, context.getString(R.string.mah_ads_play_service_not_found) + e.getMessage());
        }
    }

    //Program list filtering----------------------------------------------------------------
    private static void programSelect(List<Program> programsSource, List<Program> programsSelectedLocal) {
        Random random = new Random();
        while (programsSource.size() > 0 && programsSelectedLocal.size() < 2) {
            //Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "DBRequester prog filtered count  = " + programsFiltered.size());
            int randomIndex = random.nextInt(programsSource.size());
            //Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "DBRequester random number = " + randomIndex);
            Program progRandom = programsSource.get(randomIndex);
            programsSource.remove(randomIndex);
            if (!programsSelectedLocal.contains(progRandom)) {
                programsSelectedLocal.add(progRandom);
            }
        }
    }

    public static MAHRequestResult filterMAHRequestResult(final Context context, MAHRequestResult requestResult) {

        List<Program> programsTotal = requestResult.getProgramsTotal();
        if (programsTotal != null) {
            List<Program> programsFiltered = new LinkedList<>();
            List<Program> programsNotInstalledOld = new LinkedList<>();
            List<Program> programsNotInstalledFresh = new LinkedList<>();
            List<Program> programsInstalled = new LinkedList<>();

            for (Program c : programsTotal) {
                if (!c.getUri().trim().equals(context.getPackageName().trim())) {
                    programsFiltered.add(c);
                    if (!Utils.checkPackageIfExists(context, c.getUri().trim())) {
                        Program.Freshnest freshnest = c.getFreshnest();
                        if (freshnest.equals(Program.Freshnest.NEW)
                                || freshnest.equals(Program.Freshnest.UPDATED)) {
                            programsNotInstalledFresh.add(c);
                        } else {
                            programsNotInstalledOld.add(c);
                        }
                    } else {
                        programsInstalled.add(c);
                    }
                }
            }

            //For generating selected programs start
            List<Program> programsSelectedLocal = new LinkedList<>();
            programSelect(programsNotInstalledFresh, programsSelectedLocal);
            programSelect(programsNotInstalledOld, programsSelectedLocal);
            programSelect(programsInstalled, programsSelectedLocal);


            requestResult.setProgramsFiltered(programsFiltered);
            requestResult.setProgramsSelected(programsSelectedLocal);

            return requestResult;
        }else{
            Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Programs total is null");
            return requestResult;
        }
    }



}

