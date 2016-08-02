package com.mobapphome.mahads.tools;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mobapphome.mahads.MAHAdsDlgExit;
import com.mobapphome.mahads.MAHAdsDlgPrograms;
import com.mobapphome.mahads.ProgramItmAdptPrograms;
import com.mobapphome.mahads.types.Program;

public class MAHAdsController {
	public static final String MAH_ADS_INTERNAL_CALLED = "internal_called";
	public static String urlRootOnServer;
	private static SharedPreferences sharedPref;
	private static boolean internalCalled = false;
	private static boolean lightTheme = true;
	private static String fontName = null;


	private static List<Program> programsSelected = new LinkedList<>();

	/**
	 * Initializes MAHAds library
	 * @param activity Activity which init calls
	 * @param urlRootOnServer Root of services which programs have listed
	 * @throws NullPointerException Throughs exception when urlRootOnServer is null and on other cases
     */
	public static void init(final Activity activity, String urlRootOnServer) throws NullPointerException{
		MAHAdsController.urlRootOnServer = urlRootOnServer;
		if(urlRootOnServer == null){
			throw new NullPointerException("urlRootOnServer not set call init(final Activity act, String urlRootOnServer) constructor");
		}

		sharedPref = activity.getPreferences(Context.MODE_PRIVATE);

		setInternalCalled(activity.getIntent().getBooleanExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, false));

		Updater updater = new Updater();
		updater.setUpdaterListiner(new UpdaterListener() {

			@Override
			public void onSuccsess() {
				new DBRequester(new DBRequesterListener() {

					@Override
					public void onReadPrograms(final List<Program> programs) {
						//Do nothing
					}
				}).readPrograms(activity);
			}

			@Override
			public void onError(final String errorStr) {
				new DBRequester(new DBRequesterListener() {

					@Override
					public void onReadPrograms(final List<Program> programs) {
						//Do nothing
					}
				}).readPrograms(activity);
			}
		});
		updater.updateProgramList(activity);
	}

	/**
	 * Calls ExitDialog to open. If current dialog has opened through MAHAds dialogs
	 * then application will quit not opening ExitDialog
	 * @param activity Activity which method has called
     */
	public static void callExitDialog(FragmentActivity activity) {
		//When is internal call is true then exit dialog will not open.
		//It will be true only program opens through MAHAds components
		if(isInternalCalled()){
			activity.onBackPressed();
		}else{
			final FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction(); //get the fragment
			final MAHAdsDlgExit frag = MAHAdsDlgExit.newInstance();
			frag.show(ft, "AdsDialogExit");
		}
	}

	/**
	 * Calls ProgramsDialog to open
	 * @param activity Activity which method has called
     */
	public static void callProgramsDialog(FragmentActivity activity) {
		final FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction(); //get the fragment
		final MAHAdsDlgPrograms frag = MAHAdsDlgPrograms.newInstance();
		frag.show(ft, "AdsDialogPrograms");
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
