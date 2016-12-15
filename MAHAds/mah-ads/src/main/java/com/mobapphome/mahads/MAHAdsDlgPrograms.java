package com.mobapphome.mahads;

/**
 * Created by settar on 7/12/16.
 */


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobapphome.mahads.tools.Constants;
import com.mobapphome.mahads.tools.MAHAdsController;
import com.mobapphome.mahads.tools.Updater;
import com.mobapphome.mahads.types.Program;

import java.util.LinkedList;
import java.util.List;

public class MAHAdsDlgPrograms extends DialogFragment implements
        View.OnClickListener {

    LinearLayout lytLoadingF1;
    LinearLayout lytErrorF1;
    TextView tvErrorResultF1;
    ListView lstProgram;
    List<Object> items;
    Updater updater;
    boolean withPopupInfoMenu = true;

    public MAHAdsDlgPrograms() {
        // Empty constructor required for DialogFragment
    }

    public static MAHAdsDlgPrograms newInstance(boolean withPopupInfoMenu) {
        MAHAdsDlgPrograms dialog = new MAHAdsDlgPrograms();
        Bundle args = new Bundle();
        args.putBoolean("withPopupInfoMenu", withPopupInfoMenu);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MAHAdsDlgPrograms);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Test", "MAH Ads Programs Dlg Created ");

        Bundle args = getArguments();
        withPopupInfoMenu = args.getBoolean("withPopupInfoMenu", true);

        View view = inflater.inflate(R.layout.mah_ads_dialog_programs, container);

        getDialog().getWindow().getAttributes().windowAnimations = R.style.MAHAdsDialogAnimation;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_BACK) {

                    onClose();
                    return true;
                }
                return false;
            }
        });


        lytLoadingF1 = (LinearLayout) view.findViewById(R.id.lytLoadingMahAds);
        lytErrorF1 = (LinearLayout) view.findViewById(R.id.lytErrorMAHAds);
        tvErrorResultF1 = (TextView) view.findViewById(R.id.tvErrorResultMAHAds);
        view.findViewById(R.id.mah_ads_dlg_programs_btnCancel).setOnClickListener(this);
        view.findViewById(R.id.mah_ads_dlg_programs_btn_close).setOnClickListener(this);
        view.findViewById(R.id.mah_ads_dlg_programs_btnInfo).setOnClickListener(this);
        lstProgram = (ListView) view.findViewById(R.id.lstMahAds);
        ((TextView) view.findViewById(R.id.btnErrorRefreshMAHAds)).setOnClickListener(this);

        updater = new Updater();
        lytLoadingF1.setVisibility(View.VISIBLE);
        lytErrorF1.setVisibility(View.GONE);
        lstProgram.setVisibility(View.GONE);
        updater.updateProgramList(getActivity());

        Animation animation = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);

        animation.setDuration(350);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        ImageView iv = (ImageView) view.findViewById(R.id.ivLoadingMahAds);
        if (MAHAdsController.isLightTheme()) {
            iv.setImageResource(R.drawable.ic_loading_mah);
        } else {
            iv.setImageResource(R.drawable.ic_loading_mah_white);
        }
        iv.startAnimation(animation);

        MAHAdsController.setFontTextView((TextView) view.findViewById(R.id.tvTitle));
        MAHAdsController.setFontTextView(tvErrorResultF1);
        MAHAdsController.setFontTextView((TextView) view.findViewById(R.id.btnErrorRefreshMAHAds));
        return view;
    }

    public void setViewAfterLoad(List<Program> programs, boolean success) {
        if (success) {
            Log.i("Test", "------Success");
        } else {
            Log.i("Test", "--------onError");
            tvErrorResultF1.setText(getResources().getString(
                    R.string.mah_ads_internet_update_error));
        }
        items = new LinkedList<>();
        for (Program c : programs) {
            items.add(c);
        }
        ProgramItmAdptPrograms adapterInit = new ProgramItmAdptPrograms(getContext(), items);

        lstProgram.setAdapter(adapterInit);
        if (success) {
            lytLoadingF1.setVisibility(View.GONE);
            lytErrorF1.setVisibility(View.GONE);
            lstProgram.setVisibility(View.VISIBLE);
        } else {
            if (programs.size() > 0) {
                lytLoadingF1.setVisibility(View.GONE);
                lytErrorF1.setVisibility(View.GONE);
                lstProgram.setVisibility(View.VISIBLE);
            } else {
                lytLoadingF1.setVisibility(View.GONE);
                lytErrorF1.setVisibility(View.VISIBLE);
                lstProgram.setVisibility(View.GONE);
            }
        }
    }


    public void onClose() {
        dismiss();
    }

    private void showMAHlib() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MAH_ADS_GITHUB_LINK));
        getContext().startActivity(browserIntent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mah_ads_dlg_programs_btnCancel
                || v.getId() == R.id.mah_ads_dlg_programs_btn_close) {
            onClose();
        } else if (v.getId() == R.id.mah_ads_dlg_programs_btnInfo) {

            if (withPopupInfoMenu) {
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
            } else {
                showMAHlib();
            }


        } else if (v.getId() == R.id.btnErrorRefreshMAHAds) {
            if (updater != null) {
                lytLoadingF1.setVisibility(View.VISIBLE);
                lytErrorF1.setVisibility(View.GONE);
                lstProgram.setVisibility(View.GONE);
                updater.updateProgramList(getActivity());
            }
        }
    }
}