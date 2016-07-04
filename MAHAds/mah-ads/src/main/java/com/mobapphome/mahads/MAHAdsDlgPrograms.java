package com.mobapphome.mahads;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.mobapphome.mahads.tools.Constants;
import com.mobapphome.mahads.tools.DBRequester;
import com.mobapphome.mahads.tools.DBRequesterListener;
import com.mobapphome.mahads.tools.MAHAdsController;
import com.mobapphome.mahads.tools.SqlMethods;
import com.mobapphome.mahads.tools.Updater;
import com.mobapphome.mahads.tools.UpdaterListener;
import com.mobapphome.mahads.tools.Utils;
import com.mobapphome.mahads.types.Program;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

class AlertDialogClassPrograms extends Dialog implements
		android.view.View.OnClickListener {

	public Activity parent;
	public Dialog d;
	LinearLayout lytLoadingF1;
	LinearLayout lytErrorF1;
	TextView tvErrorResultF1;
	ListView lstProgram;
	Updater updater;
	List<Object> items;

	public AlertDialogClassPrograms(Activity a) {
		super(a);
		// TODO Auto-generated constructor stub
		this.parent = a;
	}

	public void loadSpinnerData(final boolean readFromWebSuccess) {

		new DBRequester(new DBRequesterListener() {

			@Override
			public void onReadPrograms(final List<Program> programs) {
				items = new LinkedList<>();
				for (Program c : programs) {
					items.add(c);
				}
				final ProgramItmAdptPrograms adapter = new ProgramItmAdptPrograms(getContext(), items);
				try{
					Thread.sleep(100);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				parent.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
						lstProgram.setAdapter(adapter);
						if(readFromWebSuccess){
							lytLoadingF1.setVisibility(View.GONE);
							lytErrorF1.setVisibility(View.GONE);
							lstProgram.setVisibility(View.VISIBLE);						
						}else{
							if(programs.size() > 0){
								lytLoadingF1.setVisibility(View.GONE);
								lytErrorF1.setVisibility(View.GONE);
								lstProgram.setVisibility(View.VISIBLE);	
							}else{
								lytLoadingF1.setVisibility(View.GONE);
								lytErrorF1.setVisibility(View.VISIBLE);
								lstProgram.setVisibility(View.GONE);	
							}
						}
					}
				});
			}
		}).readPrograms(getContext());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("Test", "Created ");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mah_ads_dialog_programs);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		lytLoadingF1 = (LinearLayout) findViewById(R.id.lytLoadingMahAds);
//		lytLoadingF1.setVisibility(View.GONE);
		lytErrorF1 = (LinearLayout) findViewById(R.id.lytErrorMAHAds);
//		lytErrorF1.setVisibility(View.GONE);
		tvErrorResultF1 = (TextView) findViewById(R.id.tvErrorResultMAHAds);
		((ImageButton) findViewById(R.id.btnCancelMahAds))
				.setOnClickListener(this);
		lstProgram = (ListView) findViewById(R.id.lstMahAds);
		((TextView) findViewById(R.id.btnErrorRefreshMAHAds)).setOnClickListener(this);
//		lstProgram.setVisibility(View.GONE);

		updater = new Updater();
		updater.setUpdaterListiner(new UpdaterListener() {

			@Override
			public void onSuccsess() {
				parent.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Log.i("Test", "------Success");
						loadSpinnerData(true);
					}
				});
			}

			@Override
			public void onError(final String errorStr) {
				parent.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Log.i("Test", "--------onError");
						tvErrorResultF1.setText(errorStr);
						loadSpinnerData(false);
					}
				});
			}
		});
		lytLoadingF1.setVisibility(View.VISIBLE);
		lytErrorF1.setVisibility(View.GONE);
		lstProgram.setVisibility(View.GONE);
		updater.updateProgramList(parent);
		
		Animation animation = new RotateAnimation(0.0f, 360.0f,
	            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
	            0.5f);

		animation.setDuration(350);
		animation.setInterpolator(new LinearInterpolator());
	    animation.setRepeatCount(Animation.INFINITE);
	    ImageView iv = (ImageView) findViewById(R.id.ivLoadingMahAds);
	    if(MAHAdsController.isLightTheme()){
		    iv.setImageResource(R.drawable.ic_loading_mah);	    	
	    }else{
		    iv.setImageResource(R.drawable.ic_loading_mah_white);	    		    	
	    }
	    iv.startAnimation(animation);

	    MAHAdsController.setFontTextView((TextView)findViewById(R.id.tvTitle));
	    MAHAdsController.setFontTextView(tvErrorResultF1);
	    MAHAdsController.setFontTextView((TextView)findViewById(R.id.btnErrorRefreshMAHAds));
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnCancelMahAds) {
			dismiss();
		} else if (v.getId() == R.id.btnErrorRefreshMAHAds) {
			if (updater != null) {
				lytLoadingF1.setVisibility(View.VISIBLE);
				lytErrorF1.setVisibility(View.GONE);
				lstProgram.setVisibility(View.GONE);
				updater.updateProgramList(parent);
			}
		}
	}
}

public class MAHAdsDlgPrograms extends DialogFragment {

	public static String TAG = "DateDialogFragment";
	static Context mContext;

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setCanceledOnTouchOutside(false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public static MAHAdsDlgPrograms newInstance(Context context) {
		MAHAdsDlgPrograms dialog = new MAHAdsDlgPrograms();
		mContext = context;

		/* I dont really see the purpose of the below */
		// Bundle args = new Bundle();
		// args.putString("titleStr", titleStr);
		// args.putString("questStr", questStr);
		// dialog.setArguments(args);
		// setArguments can only be called before
		// fragment is attached to an activity, so
		// right after the fragment is created

		return dialog;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Bundle args = getArguments();
		AlertDialogClassPrograms dlg = new AlertDialogClassPrograms(
				getActivity());
		return dlg;
	}

}