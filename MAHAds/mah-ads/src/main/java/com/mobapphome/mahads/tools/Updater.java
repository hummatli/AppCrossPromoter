package com.mobapphome.mahads.tools;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.mobapphome.mahads.MAHAdsDlgPrograms;
import com.mobapphome.mahads.types.MAHRequestResult;
import com.mobapphome.mahads.types.Program;
import java.io.IOException;
import java.util.List;

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

                    int currVersion = Utils.requestProgramsVersion(MAHAdsController.urlRootOnServer
                            + "program_version.php");

                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Version from base  " + myVersion + " Version from web = " + currVersion);
                    if (myVersion == currVersion) {

                        String jsonFronCache = Utils.readStringFromCache(activity);
                        if (jsonFronCache != null) {
                            programs = Utils.jsonToProgramList(jsonFronCache);
                            programs = Utils.filterSelectedPrograms(activity, programs).get(Utils.KEY_FILTERED);
                            loading = false;
                            return new MAHRequestResult(programs, true);
                        }
                    }

                    programs = Utils.requestPrograms(activity, MAHAdsController.urlRootOnServer
                            + "program_list.php");

                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Programs count out side= " + programs.size());
                    programs = Utils.filterSelectedPrograms(activity, programs).get(Utils.KEY_FILTERED);

                    loading = false;
                    return new MAHRequestResult(programs, true);

                } catch (IOException e) {
                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Accept_6");
                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, " " + e.getMessage());

                    programs = Utils.jsonToProgramList(Utils.readStringFromCache(activity));
                    programs = Utils.filterSelectedPrograms(activity, programs).get(Utils.KEY_FILTERED);
                    loading = false;
                    return new MAHRequestResult(programs, false);
                }
            }

            @Override
            protected void onPostExecute(MAHRequestResult mahRequestResult) {
                super.onPostExecute(mahRequestResult);
                if (mahRequestResult != null) {
                    MAHAdsDlgPrograms fragDlgFacebookFriends = (MAHAdsDlgPrograms) activity.getSupportFragmentManager()
                            .findFragmentByTag(MAHAdsController.TAG_MAH_ADS_DLG_PROGRAMS);
                    if (fragDlgFacebookFriends != null) {
                        fragDlgFacebookFriends.setViewAfterLoad(mahRequestResult);
                    }
                }
            }
        }.execute();
    }
}
