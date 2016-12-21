package com.mobapphome.mahads.tools;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.mobapphome.mahads.MAHAdsDlgExit;
import com.mobapphome.mahads.MAHAdsDlgPrograms;
import com.mobapphome.mahads.types.MAHRequestResult;

import java.io.IOException;

public class Updater {
    public boolean loading = false;


    public static Updater newInstance() {
        return new Updater();
    }

    public void updateProgramList(final FragmentActivity activity) {
        Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Update info called");

        new AsyncTask<Void, Void, MAHRequestResult>() {

            @Override
            protected MAHRequestResult doInBackground(Void... voids) {
                if (loading) {
                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Accept_3");
                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Loading");
                    return null;
                }

                loading = true;
                MAHRequestResult requestResult = null;
                try {
                    int myVersion = Utils.getVersionFromLocal();
                    int currVersion = HttpUtils.requestProgramsVersion(MAHAdsController.urlForProgramVersion);

                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Version from base  " + myVersion + " Version from web = " + currVersion);
                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "program list url = " + MAHAdsController.urlForProgramList);

                    //Ceck version to see are there any new verion in the web
                    if (myVersion == currVersion) {
                        //Read from cache if the verion has not changed
                        requestResult = HttpUtils.jsonToProgramList(Utils.readStringFromCache(activity));
                        if (requestResult.getProgramsTotal().size() == 0
                                || requestResult.getResultState() == MAHRequestResult.ResultState.ERR_JSON_IS_NULL_OR_EMPTY
                                || requestResult.getResultState() == MAHRequestResult.ResultState.ERR_JSON_HAS_TOTAL_ERROR) {
                            //Read again from the web if upper errors has apears
                            requestResult = HttpUtils.requestPrograms(activity, MAHAdsController.urlForProgramList);
                            Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Programs red from web, In reattemt case");
                        }
                        Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Programs red from local, In version equal case");
                    } else {
                        //Read from the web if upper errors has apears
                        requestResult = HttpUtils.requestPrograms(activity, MAHAdsController.urlForProgramList);
                        Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Programs red from web, In version different case");
                    }
                } catch (IOException e) {
                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Accept_6");
                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, " " + e.getMessage());

                    //Read from the cache if exception throwns. Fro example network error
                    requestResult = HttpUtils.jsonToProgramList(Utils.readStringFromCache(activity));
                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Programs red from local, In exception case");
                }
                Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Programs count = " + requestResult.getProgramsTotal().size());

                Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Request Result state" + requestResult.getResultState());
                Utils.filterMAHRequestResult(activity, requestResult);
                MAHAdsController.setSelectedPrograms(requestResult.getProgramsSelected());

                loading = false;
                return requestResult;
            }

            @Override
            protected void onPostExecute(MAHRequestResult mahRequestResult) {
                super.onPostExecute(mahRequestResult);
                if (mahRequestResult != null) {
                    MAHAdsDlgPrograms fragDlgPrograms = (MAHAdsDlgPrograms) activity.getSupportFragmentManager()
                            .findFragmentByTag(MAHAdsController.TAG_MAH_ADS_DLG_PROGRAMS);
                    if (fragDlgPrograms != null) {
                        fragDlgPrograms.setViewAfterLoad(mahRequestResult);
                    }

                    MAHAdsDlgExit fragDlgExit = (MAHAdsDlgExit) activity.getSupportFragmentManager()
                            .findFragmentByTag(MAHAdsController.TAG_MAH_ADS_DLG_EXIT);
                    if (fragDlgExit != null &&
                            fragDlgExit.getLytProgsPanel().getVisibility() != View.VISIBLE) {
                        fragDlgExit.setUi(mahRequestResult.getProgramsSelected());
                    }
                }
            }
        }.execute();
    }
}
