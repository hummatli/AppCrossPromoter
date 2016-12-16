package com.mobapphome.mahads;

/**
 * Created by settar on 7/12/16.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mobapphome.mahads.tools.Constants;
import com.mobapphome.mahads.tools.MAHAdsController;
import com.mobapphome.mahads.tools.MAHAdsExitListener;
import com.mobapphome.mahads.tools.Updater;
import com.mobapphome.mahads.tools.Utils;
import com.mobapphome.mahads.tools.gui.AngledLinearLayout;
import com.mobapphome.mahads.types.Program;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;


public class MAHAdsDlgExit extends DialogFragment implements
        View.OnClickListener {
    Program prog1, prog2;
    MAHAdsExitListener exitCallback;
    boolean withPopupInfoMenu = true;

    public MAHAdsDlgExit() {
        // Empty constructor required for DialogFragment
    }

    public static MAHAdsDlgExit newInstance(boolean withPopupInfoMenu) {
        MAHAdsDlgExit dialog = new MAHAdsDlgExit();
        Bundle args = new Bundle();
        args.putBoolean("withPopupInfoMenu", withPopupInfoMenu);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MAHAdsDlgExit);
        Log.i("test", "Exit dialog greated");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Test", "MAH Ads Dld exit Created ");

        Bundle args = getArguments();
        withPopupInfoMenu = args.getBoolean("withPopupInfoMenu", true);

        Log.i("test", "With popInfoMenu" + withPopupInfoMenu);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            exitCallback = (MAHAdsExitListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement MAHAdsExitListener");
        }

        final View view = inflater.inflate(R.layout.mah_ads_dialog_exit, container);

        getDialog().getWindow().getAttributes().windowAnimations = R.style.MAHAdsDialogAnimation;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_BACK) {

                    onNo();
                    return true;
                }
                return false;
            }
        });

        Button btnYes = ((Button) view.findViewById(R.id.mah_ads_dlg_exit_btn_yes));
        btnYes.setOnClickListener(this);

        Button btnNo = (Button) view.findViewById(R.id.mah_ads_dlg_exit_btn_no);
        btnNo.setOnClickListener(this);

        final TextView tvAsBtnMore = (TextView) view.findViewById(R.id.mah_ads_dlg_exit_tv_as_btn_more);
        tvAsBtnMore.setOnClickListener(this);

        view.findViewById(R.id.mah_ads_dlg_exit_btnCancel).setOnClickListener(this);
        view.findViewById(R.id.mah_ads_dlg_exit_btnInfo).setOnClickListener(this);

        final ScrollView scw = ((ScrollView)view.findViewById(R.id.mah_ads_dlg_scroll));
        scw.post(new Runnable() {

            @Override
            public void run() {
                scw.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        final LinearLayout lytProgsPanel = ((LinearLayout)view.findViewById(R.id.lytProgsPanel));
        final LinearLayout lytProg1MAHAdsExtDlg = ((LinearLayout)view.findViewById(R.id.lytProg1MAHAdsExtDlg));
        final LinearLayout lytProg2MAHAdsExtDlg = ((LinearLayout)view.findViewById(R.id.lytProg2MAHAdsExtDlg));


        new AsyncTask<Void, Void, List<Program>>(){

            @Override
            protected List<Program> doInBackground(Void... voids) {
                List<Program> programsAll =  Updater.jsonToProgramList(Utils.readStringFromCache(getActivity()));

                //Create method for selected programs
                List<Program> programsSelected = new LinkedList<>();
                for(int i = 0 ; i < 2; i++ ){
                    programsSelected.add(programsAll.get(i));
                }
                return programsSelected;
            }

            @Override
            protected void onPostExecute(List<Program> programsSelected) {
                super.onPostExecute(programsSelected);
                if(programsSelected.size() <= 0){
                    lytProgsPanel.setVisibility(View.GONE);
                    tvAsBtnMore.setText(view.getResources().getString(R.string.mah_ads_dlg_exit_btn_more_txt_1));
                }else if(programsSelected.size() == 1){
                    lytProgsPanel.setVisibility(View.VISIBLE);
                    lytProg2MAHAdsExtDlg.setVisibility(View.GONE);
                    prog1 = programsSelected.get(0);
                    ((TextView)view.findViewById(R.id.tvProg1NameMAHAdsExtDlg)).setText(prog1.getName());
                    Picasso.with(view.getContext())
                            .load(MAHAdsController.urlRootOnServer + prog1.getImg())
                            .placeholder(R.drawable.img_place_holder_normal)
                            .error(R.drawable.img_not_found)
                            .into((ImageView) view.findViewById(R.id.ivProg1ImgMAHAds));
                    AngledLinearLayout prog1LytNewText = (AngledLinearLayout)view.findViewById(R.id.lytProg1NewText);
                    if(prog1.isNewPrgram()){
                        prog1LytNewText.setVisibility(View.VISIBLE);
                    }else{
                        prog1LytNewText.setVisibility(View.GONE);
                    }
                    lytProg1MAHAdsExtDlg.setOnClickListener(MAHAdsDlgExit.this);
                    tvAsBtnMore.setText(view.getResources().getString(R.string.mah_ads_dlg_exit_btn_more_txt_2));
                }else{
                    lytProgsPanel.setVisibility(View.VISIBLE);
                    lytProg2MAHAdsExtDlg.setVisibility(View.VISIBLE);

                    prog1 = programsSelected.get(0);
                    ((TextView)view.findViewById(R.id.tvProg1NameMAHAdsExtDlg)).setText(prog1.getName());
                    Picasso.with(view.getContext())
                            .load(MAHAdsController.urlRootOnServer + prog1.getImg())
                            .placeholder(R.drawable.img_place_holder_normal)
                            .error(R.drawable.img_not_found)
                            .into((ImageView) view.findViewById(R.id.ivProg1ImgMAHAds));

                    AngledLinearLayout prog1LytNewText = (AngledLinearLayout)view.findViewById(R.id.lytProg1NewText);
                    if(prog1.isNewPrgram()){
                        prog1LytNewText.setVisibility(View.VISIBLE);
                    }else{
                        prog1LytNewText.setVisibility(View.GONE);
                    }

                    prog2 = programsSelected.get(1);
                    ((TextView)view.findViewById(R.id.tvProg2NameMAHAdsExtDlg)).setText(prog2.getName());
                    Picasso.with(view.getContext())
                            .load(MAHAdsController.urlRootOnServer + prog2.getImg())
                            .placeholder(R.drawable.img_place_holder_normal)
                            .error(R.drawable.img_not_found)
                            .into((ImageView) view.findViewById(R.id.ivProg2ImgMAHAds));

                    AngledLinearLayout prog2LytNewText = (AngledLinearLayout)view.findViewById(R.id.lytProg2NewText);
                    if(prog2.isNewPrgram()){
                        prog2LytNewText.setVisibility(View.VISIBLE);
                    }else{
                        prog2LytNewText.setVisibility(View.GONE);
                    }

                    lytProg1MAHAdsExtDlg.setOnClickListener(MAHAdsDlgExit.this);
                    lytProg2MAHAdsExtDlg.setOnClickListener(MAHAdsDlgExit.this);
                    tvAsBtnMore.setText(view.getResources().getString(R.string.mah_ads_dlg_exit_btn_more_txt_2));
                }
            }
        }.execute();

        MAHAdsController.setFontTextView((TextView) view.findViewById(R.id.tvTitle));
        MAHAdsController.setFontTextView((TextView) view.findViewById(R.id.tvProg1NewText));
        MAHAdsController.setFontTextView((TextView) view.findViewById(R.id.tvProg2NewText));
        MAHAdsController.setFontTextView((TextView) view.findViewById(R.id.tvProg1NameMAHAdsExtDlg));
        MAHAdsController.setFontTextView((TextView) view.findViewById(R.id.tvProg2NameMAHAdsExtDlg));
        MAHAdsController.setFontTextView(tvAsBtnMore);
        MAHAdsController.setFontTextView((TextView) view.findViewById(R.id.tvQuestionTxt));
        MAHAdsController.setFontTextView(btnYes);
        MAHAdsController.setFontTextView(btnNo);


        MAHAdsController.init(getActivity(), MAHAdsController.urlRootOnServer);

        return view;
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Log.i("test", "Exit dialog onDetach");
//        //Last called function on dismiss.
//         //We can call        getActivity().finish(); here if needed
//    }


    public void onYes(){
        /*#330 */ dismiss();
        exitCallback.onYes();
        getActivity().finish();
        //The problem when appears on application close is for the transition animation time difference.
        //Time for home screen animation and other animation is differenet
        //Some times it shows reappearing dialog on application close
        //There for i call dismiss() and later call for finish()
    }


    public void onNo(){
        exitCallback.onNo();
       /*#305*/ dismiss();
    }

    private void showMAHlib(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MAH_ADS_GITHUB_LINK));
        getContext().startActivity(browserIntent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.mah_ads_dlg_exit_btnCancel){
            dismiss();
        } else if (v.getId() == R.id.mah_ads_dlg_exit_btnInfo) {

            if(withPopupInfoMenu){
                PopupMenu popup = new PopupMenu(getContext(), v);
                // Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.mah_ads_info_popup_menu, popup.getMenu());
                // registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.mah_ads_info_popup_item) {
                            showMAHlib();
                        }
                        return true;
                    }
                });

                popup.show();// showing popup menu
            }else{
                showMAHlib();
            }
        }else if(v.getId() == R.id.mah_ads_dlg_exit_btn_yes){
            onYes();
        }else if(v.getId() == R.id.mah_ads_dlg_exit_btn_no){
            onNo();
        }else if(v.getId() == R.id.mah_ads_dlg_exit_tv_as_btn_more){
            MAHAdsController.callProgramsDialog(getActivity(), withPopupInfoMenu);
        }else if(v.getId() == R.id.lytProg1MAHAdsExtDlg && prog1 != null){
            final String pckgName = prog1.getUri().trim();

            if(Utils.checkPackageIfExists(getActivity(), pckgName)){
                PackageManager pack = getActivity().getPackageManager();
                Intent app = pack.getLaunchIntentForPackage(pckgName);
                app.putExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, true);
                getActivity().startActivity(app);
            }else {
                if(!pckgName.isEmpty()){
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                    marketIntent.setData(Uri.parse("market://details?id="+pckgName));
                    getActivity().startActivity(marketIntent);
                }
            }
        }else if(v.getId() == R.id.lytProg2MAHAdsExtDlg && prog2 != null){
            final String pckgName = prog2.getUri().trim();

            if(Utils.checkPackageIfExists(getActivity(), pckgName)){
                PackageManager pack = getActivity().getPackageManager();
                Intent app = pack.getLaunchIntentForPackage(pckgName);
                app.putExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, true);
                getActivity().startActivity(app);
            }else {
                if(!pckgName.isEmpty()){
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                    marketIntent.setData(Uri.parse("market://details?id="+pckgName));
                   /*#364*/ getActivity().startActivity(marketIntent);
                }
            }
        }
    }
}