package com.mobapphome.mahads.tools;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.mobapphome.mahads.MAHAdsDlgPrograms;
import com.mobapphome.mahads.R;
import com.mobapphome.mahads.types.Program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Updater {
	UpdaterListener updaterListiner;
	public boolean loading = false;

	public void setUpdaterListiner(UpdaterListener updaterListiner) {
		this.updaterListiner = updaterListiner;
	}

	private int getVersionFromLocal(FragmentActivity act) {
		int ret = MAHAdsController.getSharedPref().getInt(Constants.MAH_ADS_VERSION, -1);
		return ret;
	}

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
					final StringBuffer resultError = new StringBuffer();
					try {
						int myVersion = getVersionFromLocal(act);

						int currVersion = HttpTools
								.requestProgramsVersion(MAHAdsController.urlRootOnServer
										+ "program_version.php");

						Log.i("Test", "Version from base  " + myVersion
								+ " Version from web = " + currVersion);
						if (myVersion == currVersion) {

							List<Program> programs = HttpTools.readProgramsFromJson(readFromCache(act));
							MAHAdsDlgPrograms fragDlgFacebookFriends = (MAHAdsDlgPrograms) act.getSupportFragmentManager()
									.findFragmentByTag(MAHAdsController.TAG_MAH_ADS_DLG_PROGRAMS);
							if (fragDlgFacebookFriends != null) {
								fragDlgFacebookFriends.setViewAfterLoad(programs, true);
							}
							return;
						}
						List<Program> programs = HttpTools
								.requestPrograms(act, MAHAdsController.urlRootOnServer
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

						MAHAdsDlgPrograms fragDlgFacebookFriends = (MAHAdsDlgPrograms) act.getSupportFragmentManager()
								.findFragmentByTag(MAHAdsController.TAG_MAH_ADS_DLG_PROGRAMS);
						if (fragDlgFacebookFriends != null) {
							fragDlgFacebookFriends.setViewAfterLoad(null, false);
						}
						loading = false;
					}
				}
			}
		}).start();
	}

	public static void writeToCache(final Activity act, String stringToCache){
		FileOutputStream outputStream;

		try {
			outputStream = act.openFileOutput(MAHAdsController.PROGRAM_LIST_CACHE, Context.MODE_PRIVATE);
			outputStream.write(stringToCache.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readFromCache(final Activity act){
		FileInputStream inputStream;

		try {
			inputStream = act.openFileInput(MAHAdsController.PROGRAM_LIST_CACHE);

			BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
				total.append(line).append('\n');
			}
			inputStream.close();
			return total.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
