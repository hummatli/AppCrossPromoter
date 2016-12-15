package com.mobapphome.mahads.tools;

import android.app.Activity;
import android.util.Log;

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

public class HttpTools {

    static public List<Program> requestPrograms(final Activity act, String url)
            throws IOException {

        List<Program> ret = new LinkedList<>();

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
        //Log.i("Test", jsonStr);

        ret = readProgramsFromJson(jsonStr);
        Updater.writeToCache(act, jsonStr);


        return ret;

    }


    static public List<Program> readProgramsFromJson(String jsonStr){
        List<Program> ret = new LinkedList<>();
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
