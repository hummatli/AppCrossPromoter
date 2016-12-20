package com.mobapphome.mahads.tools;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.mobapphome.mahads.types.MAHRequestResult;
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
            Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Progra size from base = " + programsTotal.size());
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


    //Http tools -----------------------------------------------

    static public MAHRequestResult requestPrograms(final Context context, String url)
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
        Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Programlist json = " + jsonStr);

        Utils.writeStringToCache(context, jsonStr);

        return jsonToProgramList(jsonStr);
    }


    static public MAHRequestResult jsonToProgramList(String jsonStr) {

        List<Program> ret = new LinkedList<>();
        if (jsonStr == null
                || jsonStr.isEmpty()) {
            Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Json is null or empty");
            return new MAHRequestResult(ret, MAHRequestResult.ResultState.ERR_JSON_IS_NULL_OR_EMPTY);
        }

        try {
            MAHRequestResult.ResultState stateForRead = MAHRequestResult.ResultState.SUCCESS;
            JSONObject reader = new JSONObject(jsonStr);
            JSONArray programs = reader.getJSONArray("programs");
            // Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Programs size = " + programs.length());
            for (int i = 0; i < programs.length(); ++i) {
                try {
                    JSONObject jsonProgram = programs.getJSONObject(i);
                    String name = jsonProgram.getString("name");
                    String desc = jsonProgram.getString("desc");
                    String uri = jsonProgram.getString("uri");
                    String img = jsonProgram.getString("img");
                    String releaseDate = jsonProgram.optString("release_date");
                    String updateDate = jsonProgram.optString("update_date");
                    ret.add(new Program(0, name, desc, uri, img, releaseDate, updateDate));
                    //Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Added = " + name);
                } catch (JSONException e) {
                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Program item in json has a syntax problem " + e.toString());
                    stateForRead = MAHRequestResult.ResultState.ERR_SOME_ITEMS_HAS_JSON_SYNTAX_ERROR;
                }
            }
            return new MAHRequestResult(ret, stateForRead);
        } catch (JSONException e) {
            Log.i(MAHAdsController.LOG_TAG_MAH_ADS, e.toString());
            return new MAHRequestResult(ret, MAHRequestResult.ResultState.ERR_JSON_HAS_TOTAL_ERROR);
        }
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


        Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Response content type = " + response.contentType());
        Document doc = response.parse();
        String jsonStr = doc.body().text();
        //Log.i(MAHAdsController.LOG_TAG_MAH_ADS, jsonStr);

        try {
            JSONObject reader = new JSONObject(jsonStr);
            ret = Integer.parseInt(reader.getString("version"));
            MAHAdsController.getSharedPref().edit().putInt(Constants.MAH_ADS_VERSION, ret).apply();
        } catch (JSONException e) {
            Log.i(MAHAdsController.LOG_TAG_MAH_ADS, e.toString());
        } catch (NumberFormatException nfe) {
            Log.i(MAHAdsController.LOG_TAG_MAH_ADS, nfe.toString());
        }
        return ret;
    }
}

