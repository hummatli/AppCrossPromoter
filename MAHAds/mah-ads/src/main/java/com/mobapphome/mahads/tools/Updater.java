package com.mobapphome.mahads.tools;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.mobapphome.mahads.R;
import com.mobapphome.mahads.types.Program;

public class Updater {
	UpdaterListener updaterListiner;
	public boolean loading = false;

	public void setUpdaterListiner(UpdaterListener updaterListiner) {
		this.updaterListiner = updaterListiner;
	}

	private int getVersionFromBase(Activity act) {
		int ret = MAHAdsController.getSharedPref().getInt(Constants.MAH_ADS_VERSION, -1);
		return ret;
	}

	public void updateProgramList(final Activity act) {
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
						int myVersion = getVersionFromBase(act);

						int currVersion = HttpTools
								.requestProgramsVersion(MAHAdsController.urlRootOnServer
										+ "program_version.php");

						Log.i("Test", "Version from base  " + myVersion
								+ " Version from web = " + currVersion);
						if (myVersion == currVersion) {
							if (updaterListiner != null) {
								updaterListiner.onSuccsess();
							}
							return;
						}
						List<Program> programs = HttpTools
								.requestPrograms(MAHAdsController.urlRootOnServer
										+ "program_list.php");

						Log.i("Test",
								"Programs count out side= " + programs.size());
						if (programs.size() > 0) {
							SqlMethods.deleteAllPrograms(act);
							for (int i = 0; i < programs.size(); ++i) {
								SqlMethods.insertProgram(act, programs.get(i));

							}

							MAHAdsController.getSharedPref().edit().putInt(Constants.MAH_ADS_VERSION, currVersion).apply();

						}
						if (updaterListiner != null) {
							updaterListiner.onSuccsess();
						}
						loading = false;
					} catch (IOException e) {
						Log.i("Test", "Accept_6");

						Log.i("Test", " " + e.getMessage());
						
						resultError.append(act.getResources().getString(
								R.string.mah_ads_internet_update_error));
						if (updaterListiner != null) {
							updaterListiner.onError(resultError.toString());
						}
						loading = false;
					}
				}
			}
		}).start();
	}

}
