package com.mobapphome.mahads.tools;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.mobapphome.mahads.types.Program;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Utils {
    public final static String KEY_FILTERED = "key_filtered";
    public final static String KEY_SELECTED = "key_selected";

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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return null;
    }

    public static int getVersionFromLocal() {
        int ret = MAHAdsController.getSharedPref().getInt(Constants.MAH_ADS_VERSION, -1);
        return ret;
    }


    //Program list filtering----------------------------------------------------------------
    private static void programSelect(List<Program> programsSource, List<Program> programsSelectedLocal) {
        Random random = new Random();
        while (programsSource.size() > 0 && programsSelectedLocal.size() < 2) {
            //Log.i("Test", "DBRequester prog filtered count  = " + programsFiltered.size());
            int randomIndex = random.nextInt(programsSource.size());
            //Log.i("Test", "DBRequester random number = " + randomIndex);
            Program progRandom = programsSource.get(randomIndex);
            programsSource.remove(randomIndex);
            if (!programsSelectedLocal.contains(progRandom)) {
                programsSelectedLocal.add(progRandom);
            }
        }
    }

    public static Map<String, List<Program>> filterSelectedPrograms(final Context context, List<Program> programs) {

        Map<String, List<Program>> ret = new HashMap<>();

        Log.i("Test", "Progra size from base = " + programs.size());
        List<Program> programsFiltered = new LinkedList<>();
        List<Program> programsNotInstalledOld = new LinkedList<>();
        List<Program> programsNotInstalledNew = new LinkedList<>();
        List<Program> programsInstalled = new LinkedList<>();

        for (Program c : programs) {
            if (!c.getUri().trim().equals(context.getPackageName().trim())) {
                programsFiltered.add(c);
                if (!Utils.checkPackageIfExists(context, c.getUri().trim())) {
                    if (c.isNewPrgram()) {
                        programsNotInstalledNew.add(c);
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
        programSelect(programsNotInstalledNew, programsSelectedLocal);
        programSelect(programsNotInstalledOld, programsSelectedLocal);
        programSelect(programsInstalled, programsSelectedLocal);


        ret.put(KEY_FILTERED, programsFiltered);
        ret.put(KEY_SELECTED, programsSelectedLocal);

        return ret;
    }


    //Http tools -----------------------------------------------

    static public List<Program> requestPrograms(final Context context, String url)
            throws IOException {
        Document doc = Jsoup
                .connect(url.trim())
                .ignoreContentType(true)
                .timeout(3000)
                // .header("Host", "85.132.44.28")
                .header("Connection", "keep-alive")
                // .header("Content-Length", "111")
                .header("Cache-Control", "max-age=0")
                .header("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                // .header("Origin", "http://85.132.44.28")
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Referer", url.trim())
                // This is Needed
                .header("Accept-Encoding", "gzip,deflate,sdch")
                .header("Accept-Language", "en-US,en;q=0.8,ru;q=0.6")
                // .userAgent("Mozilla")
                .get();

        String jsonStr = doc.body().text();
        Log.i("Test", "Programlist json = " + jsonStr);

        Utils.writeStringToCache(context, jsonStr);

        return jsonToProgramList(jsonStr);
    }


    static public List<Program> jsonToProgramList(String jsonStr) {
        List<Program> ret = new LinkedList<>();
        if (jsonStr == null) {
            Log.i("Test", "JSon is null");
            return ret;
        }
        try {
            JSONObject reader = new JSONObject(jsonStr);
            JSONArray programs = reader.getJSONArray("programs");
            // Log.i("Test", "Programs size = " + programs.length());
            for (int i = 0; i < programs.length(); ++i) {
                try {
                    JSONObject jsonProgram = programs.getJSONObject(i);
                    String name = jsonProgram.getString("name");
                    String desc = jsonProgram.getString("desc");
                    String uri = jsonProgram.getString("uri");
                    String img = jsonProgram.getString("img");
                    String releaseDate = jsonProgram.getString("release_date");
                    ret.add(new Program(0, name, desc, uri, img, releaseDate));
                    //Log.i("Test", "Added = " + name);
                } catch (JSONException e) {
                    Log.i("Test", e.toString());
                }
            }
        } catch (JSONException e) {
            Log.i("Test", e.toString());
        }
        return ret;
    }

    static public int requestProgramsVersion(String url)
            throws IOException {

        int ret = 0;

        Connection.Response response = Jsoup
                .connect(url.trim())
                .ignoreContentType(true)
                .timeout(3000)
                // .header("Host", "85.132.44.28")
                .header("Connection", "keep-alive")
                // .header("Content-Length", "111")
                .header("Cache-Control", "max-age=0")
                .header("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                // .header("Origin", "http://85.132.44.28")
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Referer", url.trim())
                // This is Needed
                .header("Accept-Encoding", "gzip,deflate,sdch")
                .header("Accept-Language", "en-US,en;q=0.8,ru;q=0.6")
                // .userAgent("Mozilla")
                .execute();


        Log.i("Test", "Response content type = " + response.contentType());
        Document doc = response.parse();
        String jsonStr = doc.body().text();
        //Log.i("Test", jsonStr);

        try {
            JSONObject reader = new JSONObject(jsonStr);
            ret = Integer.parseInt(reader.getString("version"));
            MAHAdsController.getSharedPref().edit().putInt(Constants.MAH_ADS_VERSION, ret).apply();
        } catch (JSONException e) {
            Log.i("Test", e.toString());
        } catch (NumberFormatException nfe) {
            Log.i("Test", nfe.toString());
        }
        return ret;
    }
}

