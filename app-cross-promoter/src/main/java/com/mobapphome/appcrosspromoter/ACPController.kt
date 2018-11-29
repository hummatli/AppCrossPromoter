package com.mobapphome.appcrosspromoter

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.gson.Gson
import com.mobapphome.appcrosspromoter.tools.*


class ACPController private constructor(){

    private var urls: Urls? = null
    private var isInternalCalled = false
    var fontName: String? = null//This variable saves in savedInstanceState


    /**
     * Initializes MAHAds library
     * @param activity Activity which init calls
     * *
     * @param urlRootOnServer Root of services which programs have listed.
     * *
     * @param programVersionUrlEnd Url end for program version
     * *
     * @param urlForProgramListUrlEnd Url end for program list
     */
    @JvmOverloads fun init(activity: FragmentActivity,
                           savedInstanceState: Bundle?,
                           urlRootOnServer: String,
                           programVersionUrlEnd: String,
                           urlForProgramListUrlEnd: String) {
        init(activity, savedInstanceState, urlRootOnServer + programVersionUrlEnd, urlRootOnServer + urlForProgramListUrlEnd)
    }

    /**
     * Initializes MAHAds library
     * @param activity Activity which init calls
     * *
     * @param urlForProgramVersion Url for program version
     * *
     * @param urlForProgramList Url for program list. In this case: urlRootOnServer will be set to the root of urlForProgramList
     */
    @JvmOverloads fun init(activity: FragmentActivity,
                           savedInstanceState: Bundle?,
                           urlForProgramVersion: String = "program_version.php",
                           urlForProgramList: String = "program_list.php") {

        urls = Urls(urlForProgramVersion, urlForProgramList, getRootFromUrl(urlForProgramList))
        isInternalCalled = activity.intent.getBooleanExtra(Constants.MAH_ADS_INTERNAL_CALLED, false)

        if (savedInstanceState != null) {
            val gson = Gson()

            mahRequestResult = gson.fromJson(savedInstanceState.getString("mahRequestResult"), MAHRequestResult::class.java)
            fontName = savedInstanceState.getString("fontName")

            if (mahRequestResult != null) {
                //If mahRequestResult is exists in savedInstanceState then don't need do request service again. return;
                return
            }
        }

        Updater.updateProgramList(activity, urls!!)
    }


    fun onSaveInstanceState(savedInstanceState: Bundle) {
        val gson = Gson()
        savedInstanceState.putString("mahRequestResult", gson.toJson(mahRequestResult))
        savedInstanceState.putString("fontName", fontName)
    }

    /**
     * Calls ExitDialog to open. If current dialog has opened through MAHAds dialogs
     * then application will quit not opening ExitDialog
     * @param activity Activity which method has called
     * *
     * @param btnInfoVisibility If true shows info button
     * *
     * @param btnInfoWithMenu If true adds popup menu to info button
     * *
     * @param btnInfoMenuItemTitle Title of menu item for info button
     * *
     * @param btnInfoActionURL Url to open when clicking to info button or info menu item
     */
    @JvmOverloads fun callExitDialog(activity: FragmentActivity,
                                     btnInfoVisibility: Boolean = true,
                                     btnInfoWithMenu: Boolean = true,
                                     btnInfoMenuItemTitle: String = activity.getString(R.string.acp_info_popup_text),
                                     btnInfoActionURL: String = Constants.MAH_ADS_GITHUB_LINK) {
        //When is internal call is true then exit dialog will not open.
        //It will be true only program opens through MAHAds components
        if (isInternalCalled) {
            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                val exitCallback = activity as ACPDlgExit.ACPDlgExitListener
                exitCallback.onExitWithoutExitDlg()
            } catch (e: ClassCastException) {
                throw ClassCastException(activity.toString() + " must implement ACPDlgExitListener")
            }

        } else {
            showDlg(activity,
                    ACPDlgExit.newInstance(mahRequestResult, urls, fontName, btnInfoVisibility, btnInfoWithMenu, btnInfoMenuItemTitle, btnInfoActionURL),
                    Constants.TAG_MAH_ADS_DLG_EXIT)
        }
    }

    /**
     * Calls ProgramsDialog to open
     * @param activity Activity which method has called
     * *
     * @param btnInfoVisibility If true shows info button
     * *
     * @param btnInfoWithMenu If true adds popup menu to info button
     * *
     * @param btnInfoMenuItemTitle Title of menu item for info button
     * *
     * @param btnInfoActionURL Url to open when clicking to info button or info menu item
     */
    @JvmOverloads fun callProgramsDialog(activity: FragmentActivity,
                                         btnInfoVisibility: Boolean = true,
                                         btnInfoWithMenu: Boolean = true,
                                         btnInfoMenuItemTitle: String = activity.getString(R.string.acp_info_popup_text),
                                         btnInfoActionURL: String = Constants.MAH_ADS_GITHUB_LINK) {
        showDlg(activity,
                ACPDlgPrograms.newInstance(mahRequestResult, urls, fontName, btnInfoVisibility, btnInfoWithMenu, btnInfoMenuItemTitle, btnInfoActionURL),
                Constants.TAG_MAH_ADS_DLG_PROGRAMS)
    }

    companion object {

        @JvmStatic
        var instance: ACPController? = null
        private set
        get() {
            if (field == null) {
                field = ACPController()
            }
            return field
        }

        var mahRequestResult: MAHRequestResult? = null //This variable saves in savedInstanceState


        internal fun showDlg(activity: FragmentActivity, frag: Fragment, fragTag: String) {

            if (!activity.isFinishing) {
                val fragmentManager = activity.supportFragmentManager
                val fr = fragmentManager.findFragmentByTag(fragTag)
                if (fr != null && !fr.isHidden) {
                    Log.i(Constants.LOG_TAG_MAH_ADS, "showDlg  dismissed")
                    (fr as DialogFragment).dismissAllowingStateLoss()
                }

                val transaction = fragmentManager.beginTransaction()
                transaction.add(frag, fragTag)
                transaction.commitAllowingStateLoss()
            }
        }

    }
}
