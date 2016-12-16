package com.mobapphome.mahads.tools;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.mobapphome.mahads.MAHAdsDlgPrograms;
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

public class Updater {
    public boolean loading = false;

    public void updateProgramList(final FragmentActivity act) {
        Log.i("Test", "Update info called");
        new Thread(new Runnable() {

            @Override
            public void run() {
                synchronized (Updater.class) {
                    if (loading) {
                        Log.i("Test", "Accept_3");
                        Log.i("Test", "Loading");
                        return;
                    }
                    loading = true;
                    List<Program> programs = null;
                    try {
                        int myVersion = getVersionFromLocal(act);

                        int currVersion = requestProgramsVersion(MAHAdsController.urlRootOnServer
                                + "program_version.php");

                        Log.i("Test", "Version from base  " + myVersion
                                + " Version from web = " + currVersion);
                        if (myVersion == currVersion) {

                            String jsonFronCache = Utils.readStringFromCache(act);
                            if (jsonFronCache != null) {
                                programs = jsonToProgramList(jsonFronCache);
                                MAHAdsDlgPrograms fragDlgFacebookFriends = (MAHAdsDlgPrograms) act.getSupportFragmentManager()
                                        .findFragmentByTag(MAHAdsController.TAG_MAH_ADS_DLG_PROGRAMS);
                                if (fragDlgFacebookFriends != null) {
                                    fragDlgFacebookFriends.setViewAfterLoad(programs, true);
                                }
                                return;
                            }
                        }

                        programs = requestPrograms(act, MAHAdsController.urlRootOnServer
                                + "program_list.php");

                        Log.i("Test",
                                "Programs count out side= " + programs.size());

                        MAHAdsDlgPrograms fragDlgFacebookFriends = (MAHAdsDlgPrograms) act.getSupportFragmentManager()
                                .findFragmentByTag(MAHAdsController.TAG_MAH_ADS_DLG_PROGRAMS);
                        if (fragDlgFacebookFriends != null) {
                            fragDlgFacebookFriends.setViewAfterLoad(programs, true);
                        }
                        loading = false;
                    } catch (IOException e) {
                        Log.i("Test", "Accept_6");

                        Log.i("Test", " " + e.getMessage());

                        programs = jsonToProgramList(Utils.readStringFromCache(act));

                        MAHAdsDlgPrograms fragDlgFacebookFriends = (MAHAdsDlgPrograms) act.getSupportFragmentManager()
                                .findFragmentByTag(MAHAdsController.TAG_MAH_ADS_DLG_PROGRAMS);

                        if (fragDlgFacebookFriends != null) {
                            fragDlgFacebookFriends.setViewAfterLoad(programs, false);
                        }
                        loading = false;
                    }


                }
            }
        }).start();
    }

//    public void updateProgramList(final FragmentActivity act) {
//        Log.i("Test", "Update info called");
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                synchronized (Updater.class) {
//                    if (loading) {
//                        Log.i("Test", "Accept_3");
//                        Log.i("Test", "Loading");
//                        return;
//                    }
//                    loading = true;
//                    List<Program> programs = null;
//                    try {
//                        int myVersion = getVersionFromLocal(act);
//
//                        int currVersion = requestProgramsVersion(MAHAdsController.urlRootOnServer
//                                + "program_version.php");
//
//                        Log.i("Test", "Version from base  " + myVersion
//                                + " Version from web = " + currVersion);
//                        if (myVersion == currVersion) {
//
//                            String jsonFronCache = Utils.readStringFromCache(act);
//                            if (jsonFronCache != null) {
//                                programs = jsonToProgramList(jsonFronCache);
//                                MAHAdsDlgPrograms fragDlgFacebookFriends = (MAHAdsDlgPrograms) act.getSupportFragmentManager()
//                                        .findFragmentByTag(MAHAdsController.TAG_MAH_ADS_DLG_PROGRAMS);
//                                if (fragDlgFacebookFriends != null) {
//                                    fragDlgFacebookFriends.setViewAfterLoad(programs, true);
//                                }
//                                return;
//                            }
//                        }
//
//                        programs = requestPrograms(act, MAHAdsController.urlRootOnServer
//                                + "program_list.php");
//
//                        Log.i("Test",
//                                "Programs count out side= " + programs.size());
//
//                        MAHAdsDlgPrograms fragDlgFacebookFriends = (MAHAdsDlgPrograms) act.getSupportFragmentManager()
//                                .findFragmentByTag(MAHAdsController.TAG_MAH_ADS_DLG_PROGRAMS);
//                        if (fragDlgFacebookFriends != null) {
//                            fragDlgFacebookFriends.setViewAfterLoad(programs, true);
//                        }
//                        loading = false;
//                    } catch (IOException e) {
//                        Log.i("Test", "Accept_6");
//
//                        Log.i("Test", " " + e.getMessage());
//
//                        programs = jsonToProgramList(Utils.readStringFromCache(act));
//
//                        MAHAdsDlgPrograms fragDlgFacebookFriends = (MAHAdsDlgPrograms) act.getSupportFragmentManager()
//                                .findFragmentByTag(MAHAdsController.TAG_MAH_ADS_DLG_PROGRAMS);
//
//                        if (fragDlgFacebookFriends != null) {
//                            fragDlgFacebookFriends.setViewAfterLoad(programs, false);
//                        }
//                        loading = false;
//                    }
//
//
//                }
//            }
//        }).start();
//    }

    private int getVersionFromLocal(FragmentActivity act) {
        int ret = MAHAdsController.getSharedPref().getInt(Constants.MAH_ADS_VERSION, -1);
        return ret;
    }

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
        Log.i("Test", "Programlist json = " + jsonStr);

        ret = jsonToProgramList(jsonStr);
        Utils.writeStringToCache(act, jsonStr);

        return ret;
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
