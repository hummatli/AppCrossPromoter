package com.mobapphome.mahads.tools;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.mobapphome.mahads.ProgramItmAdptPrograms;
import com.mobapphome.mahads.types.Program;

public class MAHAdsController {
	public static final String MAH_ADS_INTERNAL_CALLED = "internal_called";
	public static String urlRootOnServer;
	private static boolean internalCalled = false;


	private static List<Program> programsSelected = new LinkedList<>();


	public static void init(final Activity act, String urlRootOnServer) throws NullPointerException{
		MAHAdsController.urlRootOnServer = urlRootOnServer;
		if(urlRootOnServer == null){
			throw new NullPointerException("urlRootOnServer not set call init(final Activity act, String urlRootOnServer) constructor");
		}
		
		Updater updater = new Updater();
		updater.setUpdaterListiner(new UpdaterListener() {

			@Override
			public void onSuccsess() {
				new DBRequester(new DBRequesterListener() {

					@Override
					public void onReadPrograms(final List<Program> programs) {
						//Do nothing
					}
				}).readPrograms(act);
			}

			@Override
			public void onError(final String errorStr) {
				new DBRequester(new DBRequesterListener() {

					@Override
					public void onReadPrograms(final List<Program> programs) {
						//Do nothing
					}
				}).readPrograms(act);
			}
		});
		updater.updateProgramList(act);
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

	public static boolean isInternalCalled() {
		return internalCalled;
	}

	public static void setInternalCalled(boolean internalCalled) {
		MAHAdsController.internalCalled = internalCalled;
	}
	
	
}
