package com.mobapphome.mahads.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import com.mobapphome.mahads.MAHAdsDlgExit;
import com.mobapphome.mahads.MAHAdsDlgPrograms;
import com.mobapphome.mahads.types.Program;

import java.util.LinkedList;
import java.util.List;

public class MAHAdsController {
	public static final String MAH_ADS_INTERNAL_CALLED = "internal_called";
	public static String urlRootOnServer;
	private static SharedPreferences sharedPref;
	private static boolean internalCalled = false;
	private static boolean lightTheme = true;
	private static String fontName = null;
	static String PROGRAM_LIST_CACHE = "program_list_cache";
	static String TAG_MAH_ADS_DLG_PROGRAMS = "tag_mah_ads_dlg_programs";
	static String TAG_MAH_ADS_DLG_EXIT = "tag_mah_ads_dlg_exit";


	private static List<Program> programsSelected = new LinkedList<>();

	/**
	 * Initializes MAHAds library
	 * @param activity Activity which init calls
	 * @param urlRootOnServer Root of services which programs have listed
	 * @throws NullPointerException Throughs exception when urlRootOnServer is null and on other cases
     */
	public static void init(final FragmentActivity activity, String urlRootOnServer) throws NullPointerException{
		MAHAdsController.urlRootOnServer = urlRootOnServer;
		if(urlRootOnServer == null){
			throw new NullPointerException("urlRootOnServer not set call init(final Activity act, String urlRootOnServer) constructor");
		}

		sharedPref = activity.getPreferences(Context.MODE_PRIVATE);

		setInternalCalled(activity.getIntent().getBooleanExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, false));

		Updater updater = new Updater();
		updater.updateProgramList(activity);
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
				MAHAdsExitListener exitCallback = (MAHAdsExitListener) activity;
				exitCallback.onExitWithoutExitDlg();
			} catch (ClassCastException e) {
				throw new ClassCastException(activity.toString()
						+ " must implement MAHAdsExitListener");
			}
		}else{
			final FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction(); //get the fragment
			final MAHAdsDlgExit frag = MAHAdsDlgExit.newInstance(withPopupInfoMenu);
			frag.show(ft, TAG_MAH_ADS_DLG_EXIT);
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
		final FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction(); //get the fragment
		final MAHAdsDlgPrograms frag = MAHAdsDlgPrograms.newInstance(withPopupInfoMenu);
		/*#197*/ frag.show(ft, TAG_MAH_ADS_DLG_PROGRAMS);
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

	public static boolean isLightTheme() {
		return lightTheme;
	}

	public static void setLightTheme(boolean lightTheme) {
		MAHAdsController.lightTheme = lightTheme;
	}

	public static List<Program> getProgramsSelected() {
		synchronized (programsSelected) {
			return programsSelected;			
		}
	}

	public static void setProgramsSelected(List<Program> programsSelected) {
		synchronized (MAHAdsController.programsSelected) {
			MAHAdsController.programsSelected = programsSelected;			
		}
	}

	private static boolean isInternalCalled() {
		return internalCalled;
	}

	private static void setInternalCalled(boolean internalCalled) {
		MAHAdsController.internalCalled = internalCalled;
	}
	
	
}
