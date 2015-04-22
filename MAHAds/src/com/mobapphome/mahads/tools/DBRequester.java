package com.mobapphome.mahads.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.net.ParseException;
import android.util.Log;

import com.mobapphome.mahads.types.Program;

public class DBRequester {
	
	private DBRequesterListener dbRequesterListener;

	public DBRequester(DBRequesterListener dbRequesterListener) {
		super();
		this.dbRequesterListener = dbRequesterListener;
	}
	
	public void readPrograms(final Context context){
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				List<Program> programs = SqlMethods.readAllPrograms(context);
				Log.i("Test", "Progra size from base = " + programs.size());
				List<Program> programsFiltered = new LinkedList<>();
				List<Program> programsNotInstalledOld = new LinkedList<>();
				List<Program> programsNotInstalledNew = new LinkedList<>();
				List<Program> programsInstalled = new LinkedList<>();
				
				for (Program c : programs) {
					if(!c.getUri().trim().equals(context.getPackageName().trim())){
						programsFiltered.add(c);							
						if(!Utils.checkPackageIfExists(context, c.getUri().trim())){
							if(c.isNewPrgram()){
								programsNotInstalledNew.add(c);
							}else{
								programsNotInstalledOld.add(c);								
							}
						}else{
							programsInstalled.add(c);
						}
						
					}
				}
				
				//For generating selected programs start
				List<Program> programsSelectedLocal = new LinkedList<>();
				programSelect(programsNotInstalledNew, programsSelectedLocal);
				programSelect(programsNotInstalledOld, programsSelectedLocal);
				programSelect(programsInstalled, programsSelectedLocal);

				MAHAdsController.setProgramsSelected(programsSelectedLocal);
				//For generating selected programs end
				
				if(dbRequesterListener != null){
					dbRequesterListener.onReadPrograms(programsFiltered);
				}
			}
		});
		t.setPriority(Thread.MIN_PRIORITY);
		t.start();
	}
	
	void programSelect(List<Program> programsSource, List<Program> programsSelectedLocal){
		Random random = new Random();
		while(programsSource.size() > 0 && programsSelectedLocal.size() <2){
			//Log.i("Test", "DBRequester prog filtered count  = " + programsFiltered.size());
			int randomIndex = random.nextInt(programsSource.size());
			//Log.i("Test", "DBRequester random number = " + randomIndex);
			Program progRandom = programsSource.get(randomIndex);
			programsSource.remove(randomIndex);
			if(!programsSelectedLocal.contains(progRandom)){
				programsSelectedLocal.add(progRandom);
			}
		}
	}
	
}
