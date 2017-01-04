package com.mobapphome.mahads.tools;

import android.content.Context;
import android.util.Log;

import com.mobapphome.mahads.types.MAHRequestResult;
import com.mobapphome.mahads.types.Program;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by settar on 12/20/16.
 */

public class HttpUtils {

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

        MAHRequestResult requestResult = jsonToProgramList(jsonStr);
        requestResult.setReadFromWeb(true);
        return requestResult;
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
                    Log.d(MAHAdsController.LOG_TAG_MAH_ADS, "Program item in json has a syntax problem " + e.toString(), e);
                    stateForRead = MAHRequestResult.ResultState.ERR_SOME_ITEMS_HAS_JSON_SYNTAX_ERROR;
                }
            }
            return new MAHRequestResult(ret, stateForRead);
        } catch (JSONException e) {
            Log.d(MAHAdsController.LOG_TAG_MAH_ADS, e.toString(), e);
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
            Log.d(MAHAdsController.LOG_TAG_MAH_ADS, e.toString(), e);
        } catch (NumberFormatException nfe) {
            Log.d(MAHAdsController.LOG_TAG_MAH_ADS, nfe.toString(),nfe);
        }
        return ret;
    }
}
