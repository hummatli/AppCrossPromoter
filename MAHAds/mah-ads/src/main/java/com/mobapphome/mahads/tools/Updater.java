package com.mobapphome.mahads.tools;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.mobapphome.mahads.MAHAdsDlgExit;
import com.mobapphome.mahads.MAHAdsDlgPrograms;
import com.mobapphome.mahads.types.MAHRequestResult;
import com.mobapphome.mahads.types.Program;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
                List<Program> programs = null;
                try {
                    int myVersion = Utils.getVersionFromLocal();

                    int currVersion = Utils.requestProgramsVersion(MAHAdsController.urlForProgramVersion);

                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Version from base  " + myVersion + " Version from web = " + currVersion);
                    if (myVersion == currVersion) {

                        String jsonFronCache = Utils.readStringFromCache(activity);
                        if (jsonFronCache != null) {
                            programs = Utils.jsonToProgramList(jsonFronCache);
                            Map<String, List<Program>> filtered = Utils.filterSelectedPrograms(activity, programs);
                            MAHAdsController.setSelectedPrograms(filtered.get(Utils.KEY_SELECTED));
                            loading = false;
                            return new MAHRequestResult(filtered, true);
                        }
                    }
                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "program list url = " + MAHAdsController.urlForProgramList);

                    programs = Utils.requestPrograms(activity, MAHAdsController.urlForProgramList);

                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Programs count out side= " + programs.size());
                    Map<String, List<Program>> filtered = Utils.filterSelectedPrograms(activity, programs);
                    MAHAdsController.setSelectedPrograms(filtered.get(Utils.KEY_SELECTED));
                    loading = false;
                    return new MAHRequestResult(filtered, true);

                } catch (IOException e) {
                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Accept_6");
                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, " " + e.getMessage());

                    programs = Utils.jsonToProgramList(Utils.readStringFromCache(activity));

                    Map<String, List<Program>> filtered = Utils.filterSelectedPrograms(activity, programs);
                    MAHAdsController.setSelectedPrograms(filtered.get(Utils.KEY_SELECTED));
                    loading = false;
                    return new MAHRequestResult(filtered, false);
                }
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
                    if (fragDlgExit != null) {
                        fragDlgExit.setUi(mahRequestResult.getFilteredProgramsMap().get(Utils.KEY_SELECTED));
                    }
                }
            }
        }.execute();
    }
}
