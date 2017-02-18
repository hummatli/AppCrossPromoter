package com.mobapphome.mahads.tools;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.mobapphome.mahads.MAHAdsController;
import com.mobapphome.mahads.MAHAdsDlgExit;
import com.mobapphome.mahads.MAHAdsDlgPrograms;
import com.mobapphome.mahads.types.MAHRequestResult;
import com.mobapphome.mahads.types.Urls;

import java.io.IOException;

public class Updater {
    static private boolean loading = false;

    static public void updateProgramList(final FragmentActivity activity, Urls urls) {
        Log.i(Constants.LOG_TAG_MAH_ADS, "Update info called , loading = " + loading);
        if (loading) {
            Log.i(Constants.LOG_TAG_MAH_ADS, "Accept_3");
            Log.i(Constants.LOG_TAG_MAH_ADS, "Loading");
            return;
        }

        MAHAdsDlgPrograms fragDlgPrograms = (MAHAdsDlgPrograms) activity.getSupportFragmentManager()
                .findFragmentByTag(Constants.TAG_MAH_ADS_DLG_PROGRAMS);

        if (fragDlgPrograms != null) {
            fragDlgPrograms.startLoading();
        }

        new AsyncTask<String, Void, MAHRequestResult>() {

            @Override
            protected MAHRequestResult doInBackground(String... args) {
                Log.i(Constants.LOG_TAG_MAH_ADS, "inside of doInBackground , loading = " + loading);

                //Setting loading to true
                loading = true;

                //Read from cache
                MAHRequestResult requestResult = HttpUtils.jsonToProgramList(Utils.readStringFromCache(activity));

                try {
                    int myVersion = Utils.getVersionFromLocal(activity);
                    int currVersion = HttpUtils.requestProgramsVersion(activity, args[0]);

                    Log.i(Constants.LOG_TAG_MAH_ADS, "Version from base  " + myVersion + " Version from web = " + currVersion);
                    Log.i(Constants.LOG_TAG_MAH_ADS, "program list url = " + args[1]);

                    //Ceck version to see are there any new verion in the web
                    if (myVersion == currVersion) {
                        //Read from cache if the verion has not changed
                        //requestResult = HttpUtils.jsonToProgramList(Utils.readStringFromCache(activity));
                        if (requestResult.getProgramsTotal().size() == 0
                                || requestResult.getResultState() == MAHRequestResult.ResultState.ERR_JSON_IS_NULL_OR_EMPTY
                                || requestResult.getResultState() == MAHRequestResult.ResultState.ERR_JSON_HAS_TOTAL_ERROR) {
                            //Read again from the web if upper errors has apears
                            requestResult = HttpUtils.requestPrograms(activity, args[1]);
                            Log.i(Constants.LOG_TAG_MAH_ADS, "Programs red from web, In reattemt case");
                        }
                        Log.i(Constants.LOG_TAG_MAH_ADS, "Programs red from local, In version equal case");
                    } else {
                        //Read from the web if versions are different
                        requestResult = HttpUtils.requestPrograms(activity, args[1]);
                        Log.i(Constants.LOG_TAG_MAH_ADS, "Programs red from web, In version different case");
                    }
                } catch (IOException e) {
                    Log.i(Constants.LOG_TAG_MAH_ADS, "Accept_6");
                    Log.i(Constants.LOG_TAG_MAH_ADS, " " + e.getMessage());

                    //Read from the cache if exception throwns. Fro example network error
                    //requestResult = HttpUtils.jsonToProgramList(Utils.readStringFromCache(activity));
                    Log.i(Constants.LOG_TAG_MAH_ADS, "Programs red from local, In exception case");
                }
                Log.i(Constants.LOG_TAG_MAH_ADS, "Programs count = " + requestResult.getProgramsTotal().size());

                Log.i(Constants.LOG_TAG_MAH_ADS, "Request Result state" + requestResult.getResultState());

                Utils.filterMAHRequestResult(activity, requestResult);
                return requestResult;
            }

            @Override
            protected void onPostExecute(MAHRequestResult mahRequestResult) {
                super.onPostExecute(mahRequestResult);
                Log.i(Constants.LOG_TAG_MAH_ADS, "MAHRequestResult isReadFromWeb = " + mahRequestResult.isReadFromWeb());


                if (mahRequestResult != null) {

                    MAHAdsDlgPrograms fragDlgPrograms = (MAHAdsDlgPrograms) activity.getSupportFragmentManager()
                            .findFragmentByTag(Constants.TAG_MAH_ADS_DLG_PROGRAMS);
                    if (fragDlgPrograms != null) {
                        fragDlgPrograms.setUI(mahRequestResult, false);
                    }

                    MAHAdsDlgExit fragDlgExit = (MAHAdsDlgExit) activity.getSupportFragmentManager()
                            .findFragmentByTag(Constants.TAG_MAH_ADS_DLG_EXIT);
                    if (fragDlgExit != null
                            //&& mahRequestResult.isReadFromWeb()
                            ) {
                        fragDlgExit.setUi(mahRequestResult);
                    }
                }


                MAHAdsController.setMahRequestResult(mahRequestResult);

                //Setting loading to false
                loading = false;
                Log.i(Constants.LOG_TAG_MAH_ADS, "Set loading to = " + loading);
            }
        }.execute(urls.getUrlForProgramVersion(), urls.getUrlForProgramList());
    }
}
