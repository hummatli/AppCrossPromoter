package com.mobapphome.mahads.tools

import android.content.Context
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import org.jsoup.Jsoup

import java.io.IOException
import java.util.LinkedList

/**
 * Created by settar on 12/20/16.
 */

object HttpUtils {

    @Throws(IOException::class)
    fun requestPrograms(context: Context, url: String): MAHRequestResult {
        val doc = Jsoup
                .connect(url.trim { it <= ' ' })
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
                .header("Referer", url.trim { it <= ' ' })
                // This is Needed
                .header("Accept-Encoding", "gzip,deflate,sdch")
                .header("Accept-Language", "en-US,en;q=0.8,ru;q=0.6")
                // .userAgent("Mozilla")
                .get()

        val jsonStr = doc.body().text()
        Log.i(Constants.LOG_TAG_MAH_ADS, "Programlist json = " + jsonStr)

        writeStringToCache(context, jsonStr)

        val requestResult = jsonToProgramList(jsonStr)
        requestResult.isReadFromWeb = true
        return requestResult
    }


    fun jsonToProgramList(jsonStr: String?): MAHRequestResult {

        val ret = LinkedList<Program>()
        if (jsonStr == null || jsonStr.isEmpty()) {
            Log.i(Constants.LOG_TAG_MAH_ADS, "Json is null or empty")
            return MAHRequestResult(ret, MAHRequestResult.ResultState.ERR_JSON_IS_NULL_OR_EMPTY)
        }

        try {
            var stateForRead: MAHRequestResult.ResultState = MAHRequestResult.ResultState.SUCCESS
            val reader = JSONObject(jsonStr)
            val programs = reader.getJSONArray("programs")
            // Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Programs size = " + programs.length());
            for (i in 0..programs.length() - 1) {
                try {
                    val jsonProgram = programs.getJSONObject(i)
                    val name = jsonProgram.getString("name")
                    val desc = jsonProgram.getString("desc")
                    val uri = jsonProgram.getString("uri")
                    val img = jsonProgram.getString("img")
                    val releaseDate = jsonProgram.optString("release_date")
                    val updateDate = jsonProgram.optString("update_date")
                    ret.add(Program(0, name, desc, uri, img, releaseDate, updateDate))
                    //Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Added = " + name);
                } catch (e: JSONException) {
                    Log.d(Constants.LOG_TAG_MAH_ADS, "Program item in json has a syntax problem " + e.toString(), e)
                    stateForRead = MAHRequestResult.ResultState.ERR_SOME_ITEMS_HAS_JSON_SYNTAX_ERROR
                }

            }
            return MAHRequestResult(ret, stateForRead)
        } catch (e: JSONException) {
            Log.d(Constants.LOG_TAG_MAH_ADS, e.toString(), e)
            return MAHRequestResult(ret, MAHRequestResult.ResultState.ERR_JSON_HAS_TOTAL_ERROR)
        }

    }

    @Throws(IOException::class)
    fun requestProgramsVersion(context: Context, url: String): Int {

        var ret = 0

        val response = Jsoup
                .connect(url.trim { it <= ' ' })
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
                .header("Referer", url.trim { it <= ' ' })
                // This is Needed
                .header("Accept-Encoding", "gzip,deflate,sdch")
                .header("Accept-Language", "en-US,en;q=0.8,ru;q=0.6")
                // .userAgent("Mozilla")
                .execute()


        Log.i(Constants.LOG_TAG_MAH_ADS, "Response content type = " + response.contentType())
        val doc = response.parse()
        val jsonStr = doc.body().text()
        //Log.i(MAHAdsController.LOG_TAG_MAH_ADS, jsonStr);

        try {
            val reader = JSONObject(jsonStr)
            ret = Integer.parseInt(reader.getString("version"))
            getSharedPref(context).edit().putInt(Constants.MAH_ADS_VERSION, ret).apply()
        } catch (e: JSONException) {
            Log.d(Constants.LOG_TAG_MAH_ADS, e.toString(), e)
        } catch (nfe: NumberFormatException) {
            Log.d(Constants.LOG_TAG_MAH_ADS, nfe.toString(), nfe)
        }

        return ret
    }
}
