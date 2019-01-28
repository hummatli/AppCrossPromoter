package com.mobapphome.appcrosspromoter.tools

import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.mobapphome.appcrosspromoter.ACPController
import com.mobapphome.appcrosspromoter.ACPDlgExit
import com.mobapphome.appcrosspromoter.ACPDlgPrograms
import java.io.IOException

object Updater {
    private var loading = false

    fun updateProgramList(activity: FragmentActivity, urls: Urls) {
        Log.i(Constants.LOG_TAG_MAH_ADS, "Update info called , loading = " + loading)
        if (loading) {
            Log.i(Constants.LOG_TAG_MAH_ADS, "Accept_3")
            Log.i(Constants.LOG_TAG_MAH_ADS, "Loading")
            return
        }

        val fragDlgPrograms = activity.supportFragmentManager
                .findFragmentByTag(Constants.TAG_MAH_ADS_DLG_PROGRAMS) as ACPDlgPrograms?

        if (fragDlgPrograms != null
                && fragDlgPrograms.isVisible
                && !fragDlgPrograms.isRemoving) {
            fragDlgPrograms.startLoading()
        }

        object : AsyncTask<String, Void, MAHRequestResult>() {

            override fun doInBackground(vararg args: String): MAHRequestResult {
                Log.i(Constants.LOG_TAG_MAH_ADS, "inside of doInBackground , loading = " + loading)

                //Setting loading to true
                loading = true

                //Read from cache
                var requestResult = HttpUtils.jsonToProgramList(readStringFromCache(activity))
                //Here we filter and set ACPController for first time from cache
                filterMAHRequestResult(activity, requestResult)
                ACPController.mahRequestResult = requestResult


                try {
                    val myVersion = getVersionFromLocal(activity)
                    val currVersion = HttpUtils.requestProgramsVersion(activity, args[0])

                    Log.i(Constants.LOG_TAG_MAH_ADS, "Version from base  $myVersion Version from web = $currVersion")
                    Log.i(Constants.LOG_TAG_MAH_ADS, "program list url = " + args[1])

                    //Ceck version to see are there any new verion in the web
                    if (myVersion == currVersion) {
                        //Read from cache if the verion has not changed
                        //requestResult = HttpUtils.jsonToProgramList(Utils.readStringFromCache(activity));
                        if (requestResult.programsTotal.size == 0
                                || requestResult.resultState == MAHRequestResult.ResultState.ERR_JSON_IS_NULL_OR_EMPTY
                                || requestResult.resultState == MAHRequestResult.ResultState.ERR_JSON_HAS_TOTAL_ERROR) {
                            //Read again from the web if upper errors has apears
                            requestResult = HttpUtils.requestPrograms(activity, args[1])
                            Log.i(Constants.LOG_TAG_MAH_ADS, "Programs red from web, In reattemt case")
                        }
                        Log.i(Constants.LOG_TAG_MAH_ADS, "Programs red from local, In version equal case")
                    } else {
                        //Read from the web if versions are different
                        requestResult = HttpUtils.requestPrograms(activity, args[1])
                        Log.i(Constants.LOG_TAG_MAH_ADS, "Programs red from web, In version different case")
                    }
                    //In this place we filter mahrequest from web
                    filterMAHRequestResult(activity, requestResult)
                } catch (e: IOException) {
                    Log.i(Constants.LOG_TAG_MAH_ADS, "Accept_6")
                    Log.i(Constants.LOG_TAG_MAH_ADS, " " + e.message)
                    Log.i(Constants.LOG_TAG_MAH_ADS, "Programs red from local, In exception case")
                }

                Log.i(Constants.LOG_TAG_MAH_ADS, "Programs count = " + requestResult.programsTotal.size)

                Log.i(Constants.LOG_TAG_MAH_ADS, "Request Result state" + requestResult.resultState)

                return requestResult
            }

            override fun onPostExecute(mahRequestResult: MAHRequestResult?) {
                super.onPostExecute(mahRequestResult)
                Log.i(Constants.LOG_TAG_MAH_ADS, "MAHRequestResult isReadFromWeb = " + mahRequestResult!!.isReadFromWeb)

                //It calls twise. Needs to solve. I can not use this "mahRequestResult.isReadFromWeb".
                // Cause in first time needs to show retry button. if I check it does not show
                fragDlgPrograms?.setUI(mahRequestResult, false)

                val fragDlgExit = activity.supportFragmentManager.findFragmentByTag(Constants.TAG_MAH_ADS_DLG_EXIT) as ACPDlgExit?
                if (fragDlgExit != null && mahRequestResult.isReadFromWeb ) {
                    fragDlgExit.setUi(mahRequestResult)
                }


                //In this place we set ACPController's mahrequest from web
                ACPController.mahRequestResult = mahRequestResult

                //Setting loading to false
                loading = false
                Log.i(Constants.LOG_TAG_MAH_ADS, "Set loading to = " + loading)
            }
        }.execute(urls.urlForProgramVersion, urls.urlForProgramList)
    }
}
