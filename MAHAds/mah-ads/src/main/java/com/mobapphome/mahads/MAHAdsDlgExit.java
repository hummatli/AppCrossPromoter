package com.mobapphome.mahads;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import com.loopj.android.image.SmartImageView;
import com.mobapphome.mahads.tools.Constants;
import com.mobapphome.mahads.tools.DBRequester;
import com.mobapphome.mahads.tools.DBRequesterListener;
import com.mobapphome.mahads.tools.ExitListiner;
import com.mobapphome.mahads.tools.MAHAdsController;
import com.mobapphome.mahads.tools.SqlMethods;
import com.mobapphome.mahads.tools.Updater;
import com.mobapphome.mahads.tools.UpdaterListener;
import com.mobapphome.mahads.tools.Utils;
import com.mobapphome.mahads.tools.gui.AngledLinearLayout;
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
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.CompletionInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

class AlertDialogClassExit extends Dialog implements
		android.view.View.OnClickListener {

	public FragmentActivity parent;
	public Dialog d;
	ExitListiner exitListiner;
	LinearLayout lytProgsPanel;
	LinearLayout lytProg1MAHAdsExtDlg;
	LinearLayout lytProg2MAHAdsExtDlg;
	Program prog1, prog2;



	public AlertDialogClassExit(FragmentActivity a) {
		super(a);
		// TODO Auto-generated constructor stub
		this.parent = a;
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("Test", "MAH Dld exit Created ");
		super.onCreate(savedInstanceState);
		
		getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mah_ads_dialog_exit);
		
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));		
		
		Button btnYes = ((Button) findViewById(R.id.mah_ads_dlg_exit_btn_yes));
		btnYes.setOnClickListener(this);
		
		Button btnNo = (Button) findViewById(R.id.mah_ads_dlg_exit_btn_no);
		btnNo.setOnClickListener(this);
		
		TextView tvAsBtnMore = (TextView) findViewById(R.id.mah_ads_dlg_exit_tv_as_btn_more);
		tvAsBtnMore.setOnClickListener(this);

		((ImageButton) findViewById(R.id.mah_ads_dlg_exit_btnCancel)).setOnClickListener(this);
		lytProgsPanel = ((LinearLayout)findViewById(R.id.lytProgsPanel));
		lytProg1MAHAdsExtDlg = ((LinearLayout)findViewById(R.id.lytProg1MAHAdsExtDlg));
		lytProg2MAHAdsExtDlg = ((LinearLayout)findViewById(R.id.lytProg2MAHAdsExtDlg));
		
		
		List<Program> programsSelected = MAHAdsController.getProgramsSelected();
		if(programsSelected.size() <= 0){
			lytProgsPanel.setVisibility(View.GONE);
			tvAsBtnMore.setText(parent.getResources().getString(R.string.mah_ads_dlg_exit_btn_more_txt_1));
		}else if(programsSelected.size() == 1){
			lytProgsPanel.setVisibility(View.VISIBLE);
			lytProg2MAHAdsExtDlg.setVisibility(View.GONE);	
			prog1 = programsSelected.get(0);
			((TextView)findViewById(R.id.tvProg1NameMAHAdsExtDlg)).setText(prog1.getName());
			if (prog1.getImg() != null && !prog1.getImg().trim().isEmpty()) {
				((SmartImageView)findViewById(R.id.ivProg1ImgMAHAds)).setImageUrl(MAHAdsController.urlRootOnServer + prog1.getImg());
			}
			AngledLinearLayout prog1LytNewText = (AngledLinearLayout)findViewById(R.id.lytProg1NewText);
			if(prog1.isNewPrgram()){
				prog1LytNewText.setVisibility(View.VISIBLE);
			}else{
				prog1LytNewText.setVisibility(View.GONE);				
			}
			lytProg1MAHAdsExtDlg.setOnClickListener(this);
			tvAsBtnMore.setText(parent.getResources().getString(R.string.mah_ads_dlg_exit_btn_more_txt_2));
		}else{
			lytProgsPanel.setVisibility(View.VISIBLE);
			lytProg2MAHAdsExtDlg.setVisibility(View.VISIBLE);	
			
			prog1 = programsSelected.get(0);
			((TextView)findViewById(R.id.tvProg1NameMAHAdsExtDlg)).setText(prog1.getName());
			if (prog1.getImg() != null && !prog1.getImg().trim().isEmpty()) {
				((SmartImageView)findViewById(R.id.ivProg1ImgMAHAds)).setImageUrl(MAHAdsController.urlRootOnServer + prog1.getImg());
			}
			AngledLinearLayout prog1LytNewText = (AngledLinearLayout)findViewById(R.id.lytProg1NewText);
			if(prog1.isNewPrgram()){
				prog1LytNewText.setVisibility(View.VISIBLE);
			}else{
				prog1LytNewText.setVisibility(View.GONE);				
			}
			
			prog2 = programsSelected.get(1);
			((TextView)findViewById(R.id.tvProg2NameMAHAdsExtDlg)).setText(prog2.getName());
			if (prog2.getImg() != null && !prog2.getImg().trim().isEmpty()) {
				((SmartImageView)findViewById(R.id.ivProg2ImgMAHAds)).setImageUrl(MAHAdsController.urlRootOnServer + prog2.getImg());
			}
			AngledLinearLayout prog2LytNewText = (AngledLinearLayout)findViewById(R.id.lytProg2NewText);
			if(prog2.isNewPrgram()){
				prog2LytNewText.setVisibility(View.VISIBLE);
			}else{
				prog2LytNewText.setVisibility(View.GONE);				
			}
			
			lytProg1MAHAdsExtDlg.setOnClickListener(this);
			lytProg2MAHAdsExtDlg.setOnClickListener(this);
			tvAsBtnMore.setText(parent.getResources().getString(R.string.mah_ads_dlg_exit_btn_more_txt_2));
		}
		
		MAHAdsController.setFontTextView((TextView) findViewById(R.id.tvTitle));
		MAHAdsController.setFontTextView((TextView) findViewById(R.id.tvProg1NewText));
		MAHAdsController.setFontTextView((TextView) findViewById(R.id.tvProg2NewText));
		MAHAdsController.setFontTextView((TextView) findViewById(R.id.tvProg1NameMAHAdsExtDlg));
		MAHAdsController.setFontTextView((TextView) findViewById(R.id.tvProg2NameMAHAdsExtDlg));
		MAHAdsController.setFontTextView(tvAsBtnMore);
		MAHAdsController.setFontTextView((TextView) findViewById(R.id.tvQuestionTxt));
		MAHAdsController.setFontTextView(btnYes);
		MAHAdsController.setFontTextView(btnNo);

		
		MAHAdsController.init(parent, MAHAdsController.urlRootOnServer);
		
	}

	public void setExitListiner(ExitListiner exitListiner) {
		this.exitListiner = exitListiner;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.mah_ads_dlg_exit_btnCancel){
			dismiss();
		}else if(v.getId() == R.id.mah_ads_dlg_exit_btn_yes){
			if(exitListiner != null){ 
				exitListiner.onYes();
			}
		}else if(v.getId() == R.id.mah_ads_dlg_exit_btn_no){
			dismiss();
			if(exitListiner != null){ 
				exitListiner.onNo();
			}
		}else if(v.getId() == R.id.mah_ads_dlg_exit_tv_as_btn_more){
			final FragmentTransaction ft = parent.getSupportFragmentManager().beginTransaction(); //get the fragment
			final MAHAdsDlgPrograms frag = MAHAdsDlgPrograms.newInstance(parent);
			frag.show(ft, "AdsDialogFragment");
		}else if(v.getId() == R.id.lytProg1MAHAdsExtDlg && prog1 != null){
			final String pckgName = prog1.getUri().trim();

			if(Utils.checkPackageIfExists(parent, pckgName)){
                PackageManager pack = parent.getPackageManager();
                Intent app = pack.getLaunchIntentForPackage(pckgName);
                app.putExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, true);
                parent.startActivity(app);						
			}else {
				if(!pckgName.isEmpty()){
					Intent marketIntent = new Intent(Intent.ACTION_VIEW);
					marketIntent.setData(Uri.parse("market://details?id="+pckgName));
					parent.startActivity(marketIntent);
				}
			}
		}else if(v.getId() == R.id.lytProg2MAHAdsExtDlg && prog2 != null){
			final String pckgName = prog2.getUri().trim();

			if(Utils.checkPackageIfExists(parent, pckgName)){
                PackageManager pack = parent.getPackageManager();
                Intent app = pack.getLaunchIntentForPackage(pckgName);
                app.putExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, true);
                parent.startActivity(app);						
			}else {
				if(!pckgName.isEmpty()){
					Intent marketIntent = new Intent(Intent.ACTION_VIEW);
					marketIntent.setData(Uri.parse("market://details?id="+pckgName));
					parent.startActivity(marketIntent);
				}
			}
		}
	}
}



public class MAHAdsDlgExit extends DialogFragment {

	public static String TAG = "DateDialogFragment";
	static Context mContext; 
	ExitListiner exitListiner;

	public void setExitListiner(ExitListiner exitListiner) {
		this.exitListiner = exitListiner;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_BACK) {

                    dismiss();
                    if(exitListiner != null){
                        exitListiner.onNo();
                    }
                    return true;
                }
                return false;
            }
        });

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public static MAHAdsDlgExit newInstance(Context context, ExitListiner exitListiner) {
		MAHAdsDlgExit dialog = new MAHAdsDlgExit();
		mContext = context;
		dialog.setExitListiner(exitListiner);
		/* I dont really see the purpose of the below */
//		Bundle args = new Bundle();
//		args.putString("txt", txt);
//		args.putString("questStr", questStr);
//		dialog.setArguments(args);
		// setArguments can only be called before
									// fragment is attached to an activity, so
									// right after the fragment is created
		return dialog;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		//Bundle args = getArguments();
	    AlertDialogClassExit dlg= new AlertDialogClassExit(getActivity());
	    dlg.setExitListiner(exitListiner);
	    return dlg;
	}
	
}