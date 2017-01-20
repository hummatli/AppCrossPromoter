package com.mobapphome.mahads;

/**
 * Created by settar on 7/12/16.
 */


import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
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
import android.widget.Toast;

import com.mobapphome.mahads.mahfragments.MAHDialogFragment;
import com.mobapphome.mahads.mahfragments.MAHFragmentExeption;
import com.mobapphome.mahads.tools.MAHAdsController;
import com.mobapphome.mahads.types.MAHRequestResult;
import com.mobapphome.mahads.types.Program;

import java.util.LinkedList;
import java.util.List;

public class MAHAdsDlgPrograms extends MAHDialogFragment implements
        View.OnClickListener {

    View view;

    TextView tvErrorResultF1;
    ImageView ivLoading;
    ListView lstProgram;
    LinearLayout lytErrorF1;


    List<Object> items;

    boolean btnInfoVisibility;
    boolean btnInfoWithMenu;
    String btnInfoMenuItemTitle;
    String btnInfoActionURL;
    boolean dataHasAlreadySet = false;

    public MAHAdsDlgPrograms() {
        // Empty constructor required for DialogFragment
    }

    public static MAHAdsDlgPrograms newInstance(boolean btnInfoVisibility,
                                                boolean btnInfoWithMenu,
                                                String btnInfoMenuItemTitle,
                                                String btnInfoActionURL) {
        MAHAdsDlgPrograms dialog = new MAHAdsDlgPrograms();
        Bundle args = new Bundle();
        args.putBoolean("btnInfoVisibility", btnInfoVisibility);
        args.putBoolean("btnInfoWithMenu", btnInfoWithMenu);
        args.putString("btnInfoMenuItemTitle", btnInfoMenuItemTitle);
        args.putString("btnInfoActionURL", btnInfoActionURL);
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
        try {
            Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "MAH Ads Programs Dlg Created ");

            Bundle args = getArguments();
            btnInfoVisibility = args.getBoolean("btnInfoVisibility");
            btnInfoWithMenu = args.getBoolean("btnInfoWithMenu");
            btnInfoMenuItemTitle = args.getString("btnInfoMenuItemTitle");
            btnInfoActionURL = args.getString("btnInfoActionURL");

            view = inflater.inflate(R.layout.mah_ads_dialog_programs, container);

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


            lstProgram = (ListView) view.findViewById(R.id.lstMahAds);
            lytErrorF1 = (LinearLayout) view.findViewById(R.id.lytErrorMAHAds);
            tvErrorResultF1 = (TextView) view.findViewById(R.id.tvErrorResultMAHAds);



            view.findViewById(R.id.mah_ads_dlg_programs_btn_close).setOnClickListener(this);
            view.findViewById(R.id.btnErrorRefreshMAHAds).setOnClickListener(this);

            ImageView ivBtnCancel = ((ImageView) view.findViewById(R.id.mah_ads_dlg_programs_btnCancel));
            ImageView ivBtnInfo = ((ImageView) view.findViewById(R.id.mah_ads_dlg_programs_btnInfo));

            ivBtnCancel.setOnClickListener(this);
            ivBtnInfo.setOnClickListener(this);
            ivBtnCancel.setColorFilter(ContextCompat.getColor(getContext(), R.color.mah_ads_title_bar_text_color));
            ivBtnInfo.setColorFilter(ContextCompat.getColor(getContext(), R.color.mah_ads_title_bar_text_color));

            if (btnInfoVisibility) {
                ivBtnInfo.setVisibility(View.VISIBLE);
            } else {
                ivBtnInfo.setVisibility(View.INVISIBLE);
            }

            ivLoading = (ImageView) view.findViewById(R.id.ivLoadingMahAds);
            ivLoading.setColorFilter(ContextCompat.getColor(getContext(), R.color.mah_ads_all_and_btn_text_color));
            ivLoading.setImageResource(R.drawable.ic_loading_mah);

            lstProgram.setVisibility(View.GONE);
            lytErrorF1.setVisibility(View.GONE);
            ivLoading.setVisibility(View.GONE);


            startLoading();
            setUI(MAHAdsController.getMahRequestResult());

            //Call to update data from service or local
            MAHAdsController.getUpdater().updateProgramList(getActivityMAH());

            MAHAdsController.setFontTextView((TextView) view.findViewById(R.id.tvTitle));
            MAHAdsController.setFontTextView(tvErrorResultF1);
            MAHAdsController.setFontTextView((TextView) view.findViewById(R.id.btnErrorRefreshMAHAds));
            return view;
        } catch (MAHFragmentExeption e) {
            Log.d(MAHAdsController.LOG_TAG_MAH_ADS, e.getMessage(), e);
            return null;
        }
    }

    public void setUI(final MAHRequestResult result) {
        Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "------Result State is " + result.getResultState());


        if (result.getResultState() == MAHRequestResult.ResultState.SUCCESS
                || result.getResultState() == MAHRequestResult.ResultState.ERR_SOME_ITEMS_HAS_JSON_SYNTAX_ERROR) {
            dataHasAlreadySet = true;
            final List<Program> programsExceptMyself = result.getProgramsFiltered();
            items = new LinkedList<>();
            for (Program c : programsExceptMyself) {
                items.add(c);
            }
            final ProgramItmAdptPrograms adapterInit = new ProgramItmAdptPrograms(getContext(), items);

            lstProgram.post(new Runnable() {
                @Override
                public void run() {
                    Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "lstProgram post called");
                    lstProgram.setAdapter(adapterInit);
                    lytErrorF1.setVisibility(View.GONE);
                    lstProgram.setVisibility(View.VISIBLE);
                }
            });
        } else {
            if (result.isReadFromWeb()) {
                lstProgram.post(new Runnable() {
                    @Override
                    public void run() {
                        lytErrorF1.setVisibility(View.VISIBLE);
                        lstProgram.setVisibility(View.GONE);
                        tvErrorResultF1.setText(getResources().getString(
                                R.string.mah_ads_internet_update_error));
                    }
                });
            } else {

                lstProgram.post(new Runnable() {
                    @Override
                    public void run() {
                        lytErrorF1.setVisibility(View.VISIBLE);
                        lstProgram.setVisibility(View.GONE);
                        tvErrorResultF1.setText(getResources().getString(
                                R.string.mah_ads_internet_update_error));
                    }
                });

            }
        }

        stopLoading();
    }

    public void startLoading() {
        if (dataHasAlreadySet) {
            return;
        }
        Animation animationLoading = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);

        animationLoading.setDuration(350);
        animationLoading.setInterpolator(new LinearInterpolator());
        animationLoading.setRepeatCount(Animation.INFINITE);

        ivLoading.startAnimation(animationLoading);
        ivLoading.setVisibility(View.VISIBLE);
        lstProgram.setVisibility(View.GONE);
        lytErrorF1.setVisibility(View.GONE);

        Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Animation started");
    }

    public void stopLoading() {
        ivLoading.setVisibility(View.GONE);
        lstProgram.setVisibility(View.GONE);
        lytErrorF1.setVisibility(View.GONE);

        ivLoading.clearAnimation();
        Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Animation stopped");
    }

    public void onClose() {
        dismissAllowingStateLoss();
    }

    private void showMAHlib() {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(btnInfoActionURL));
            getContext().startActivity(browserIntent);
        } catch (ActivityNotFoundException nfe) {
            String str = "You haven't set correct url to btnInfoActionURL, your url = " + btnInfoActionURL;
            Toast.makeText(getContext(), str, Toast.LENGTH_LONG).show();
            Log.d(MAHAdsController.LOG_TAG_MAH_ADS, str, nfe);
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == R.id.mah_ads_dlg_programs_btnCancel
                    || v.getId() == R.id.mah_ads_dlg_programs_btn_close) {
                onClose();
            } else if (v.getId() == R.id.mah_ads_dlg_programs_btnInfo) {

                if (btnInfoWithMenu) {
                    final int itemIdForInfo = 1;
                    PopupMenu popup = new PopupMenu(getContext(), v);
                    popup.getMenu().add(Menu.NONE, itemIdForInfo, 1, btnInfoMenuItemTitle);

                    // registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == itemIdForInfo) {
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
                MAHAdsController.getUpdater().updateProgramList(getActivityMAH());
            }
        } catch (MAHFragmentExeption e) {
            Log.d(MAHAdsController.LOG_TAG_MAH_ADS, e.getMessage(), e);
            return;
        }
    }
}