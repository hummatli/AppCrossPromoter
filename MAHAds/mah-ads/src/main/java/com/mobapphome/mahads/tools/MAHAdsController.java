package com.mobapphome.mahads.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import com.mobapphome.mahads.MAHAdsDlgExit;
import com.mobapphome.mahads.MAHAdsDlgPrograms;
import com.mobapphome.mahads.types.Program;

import java.util.List;

public class MAHAdsController {
	public static final String MAH_ADS_INTERNAL_CALLED = "internal_called";
	public static final String LOG_TAG_MAH_ADS = "mah_ads_log";

	protected static final String PROGRAM_LIST_CACHE = "program_list_cache2";
	protected static final String TAG_MAH_ADS_DLG_PROGRAMS = "tag_mah_ads_dlg_programs";
	protected static final String TAG_MAH_ADS_DLG_EXIT = "tag_mah_ads_dlg_exit";


	public static String urlForProgramVersion;
	public static String urlForProgramList;
	public static String urlRootOnServer;
	private static SharedPreferences sharedPref;
	private static boolean internalCalled = false;
	private static String fontName = null;

	public static List<Program> selectedPrograms;
	private static Updater updater;

	/**
	 * Initializes MAHAds library
	 * @param activity Activity which init calls
	 * @param urlRootOnServer Root of services which programs have listed. Inside of this method:
	 *                        urlForProgramVersion = urlRootOnServer + "program_version.php"
	 *                        urlForProgramList = urlRootOnServer + "program_list.php"
     */
	@Deprecated
	public static void init(@NonNull final FragmentActivity activity, @NonNull String urlRootOnServer) {
		MAHAdsController.init(activity, urlRootOnServer, "program_version.php", "program_list.php");
	}

	/**
	 * Initializes MAHAds library
	 * @param activity Activity which init calls
	 * @param urlRootOnServer Root of services which programs have listed.
	 * @param programVersionUrlEnd Url end for program version
	 * @param urlForProgramListUrlEnd Url end for program list
	 */
	public static void init(@NonNull final FragmentActivity activity, @NonNull String urlRootOnServer, @NonNull String programVersionUrlEnd, @NonNull String urlForProgramListUrlEnd) {
		MAHAdsController.init(activity, urlRootOnServer + programVersionUrlEnd, urlRootOnServer + urlForProgramListUrlEnd);
	}

	/**
	 * Initializes MAHAds library
	 * @param activity Activity which init calls
	 * @param urlForProgramVersion Url for program version
	 * @param urlForProgramList Url for program list. In this case: urlRootOnServer will be set to the root of urlForProgramList
	 */
	public static void init(@NonNull final FragmentActivity activity, @NonNull String urlForProgramVersion, @NonNull String urlForProgramList) {
		MAHAdsController.urlForProgramVersion = urlForProgramVersion;
		MAHAdsController.urlForProgramList = urlForProgramList;
		MAHAdsController.urlRootOnServer = Utils.getRootFromUrl(urlForProgramList);

		sharedPref = activity.getPreferences(Context.MODE_PRIVATE);

		setInternalCalled(activity.getIntent().getBooleanExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, false));

		getUpdater().updateProgramList(activity);
	}

	/**
	 * Gets static updater object created in init method or creates new one if is null
	 * @return updater object
     */
	public static Updater getUpdater() {
		if(updater == null){
			updater = Updater.newInstance();
		}
		return updater;
	}

	/**
	 * Calls ExitDialog to open. If current dialog has opened through MAHAds dialogs
	 * then application will quit not opening ExitDialog
	 * @param activity Activity which method has called
	 */
	public static void callExitDialog(FragmentActivity activity) {
		callExitDialog(activity, true);
	}

	/**
	 * Calls ExitDialog to open. If current dialog has opened through MAHAds dialogs
	 * then application will quit not opening ExitDialog
	 * @param activity Activity which method has called
	 * @param withPopupInfoMenu If true adds popup menu to info button
     */
	public static void callExitDialog(FragmentActivity activity, boolean withPopupInfoMenu) {
		//When is internal call is true then exit dialog will not open.
		//It will be true only program opens through MAHAds components
		if(isInternalCalled()){
			// This makes sure that the container activity has implemented
			// the callback interface. If not, it throws an exception
			try {
				MAHAdsDlgExit.MAHAdsDlgExitListener exitCallback = (MAHAdsDlgExit.MAHAdsDlgExitListener) activity;
				exitCallback.onExitWithoutExitDlg();
			} catch (ClassCastException e) {
				throw new ClassCastException(activity.toString()
						+ " must implement MAHAdsDlgExitListener");
			}
		}else{
			showDlg(activity, MAHAdsDlgExit.newInstance(withPopupInfoMenu), TAG_MAH_ADS_DLG_EXIT);
		}
	}

	/**
	 * Calls ProgramsDialog to open
	 * @param activity Activity which method has called
     */
	public static void callProgramsDialog(FragmentActivity activity) {
		callProgramsDialog(activity, true);
	}

	/**
	 * Calls ProgramsDialog to open
	 * @param activity Activity which method has called
	 * @param withPopupInfoMenu If true adds popup menu to info button
	 */
	public static void callProgramsDialog(FragmentActivity activity, boolean withPopupInfoMenu) {
		showDlg(activity, MAHAdsDlgPrograms.newInstance(withPopupInfoMenu), TAG_MAH_ADS_DLG_PROGRAMS);
	}

	static private void showDlg(FragmentActivity activity, Fragment frag, String fragTag) {

		if (!activity.isFinishing()) {
			FragmentManager fragmentManager = activity.getSupportFragmentManager();
			Fragment fr = fragmentManager.findFragmentByTag(fragTag);
			if (fr != null && !fr.isHidden()) {
				Log.i("test", "showDlgForMilyoncu  dissmesd");
                ((DialogFragment) fr).dismissAllowingStateLoss();
			}

			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.add(frag, fragTag);
			transaction.commitAllowingStateLoss();
		}
	}

	protected static SharedPreferences getSharedPref() {
		return sharedPref;
	}

	public static void setFontTextView(TextView tv) {
		if(fontName == null){
			return;
		}
		try{
			Typeface font = Typeface.createFromAsset(tv.getContext().getAssets(),fontName);
			tv.setTypeface(font);
		}catch(RuntimeException r){
			Log.e("test", "Error " + r.getMessage());
		}
	}

	public static String getFontName() {
		return fontName;
	}

	public static void setFontName(String fontName) {
		MAHAdsController.fontName = fontName;
	}

	private static boolean isInternalCalled() {
		return internalCalled;
	}

	private static void setInternalCalled(boolean internalCalled) {
		MAHAdsController.internalCalled = internalCalled;
	}

	public static List<Program> getSelectedPrograms() {
		return selectedPrograms;
	}

	public static void setSelectedPrograms(List<Program> selectedPrograms) {
		MAHAdsController.selectedPrograms = selectedPrograms;
	}
}
