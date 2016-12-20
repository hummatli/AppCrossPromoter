package com.mobapphome.mahads;

/**
 * Created by settar on 7/12/16.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobapphome.mahads.mahfragments.MAHDialogFragment;
import com.mobapphome.mahads.mahfragments.MAHFragmentExeption;
import com.mobapphome.mahads.tools.Constants;
import com.mobapphome.mahads.tools.MAHAdsController;
import com.mobapphome.mahads.tools.Utils;
import com.mobapphome.mahads.types.Program;

import java.util.List;


public class MAHAdsDlgExit extends MAHDialogFragment implements
        View.OnClickListener {
    Program prog1, prog2;
    MAHAdsDlgExitListener exitCallback;
    boolean withPopupInfoMenu = true;
    View view = null;
    ViewGroup lytProgsPanel = null;
    ViewGroup lytProg1MAHAdsExtDlg = null;
    ViewGroup lytProg2MAHAdsExtDlg = null;
    TextView tvAsBtnMore = null;

    TextView tvFresnestProg1 = null;
    TextView tvFresnestProg2 = null;

    public interface MAHAdsDlgExitListener {
        public void onYes();

        public void onNo();

        public void onExitWithoutExitDlg();

        public void onEventHappened(String eventStr);
    }

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
        Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "Exit dialog greated");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "MAH Ads Dld exit Created ");

            Bundle args = getArguments();
            withPopupInfoMenu = args.getBoolean("withPopupInfoMenu", true);

            Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "With popInfoMenu" + withPopupInfoMenu);
            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                exitCallback = (MAHAdsDlgExitListener) getActivityMAH();
            } catch (ClassCastException e) {
                throw new ClassCastException(getActivityMAH().toString()
                        + " must implement MAHAdsDlgExitListener");
            }

            view = inflater.inflate(R.layout.mah_ads_dialog_exit, container);

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

            tvAsBtnMore = (TextView) view.findViewById(R.id.mah_ads_dlg_exit_tv_btn_other);
            lytProgsPanel = ((ViewGroup) view.findViewById(R.id.lytProgsPanel));
            lytProg1MAHAdsExtDlg = ((ViewGroup) view.findViewById(R.id.lytProg1MAHAdsExtDlg));
            lytProg2MAHAdsExtDlg = ((ViewGroup) view.findViewById(R.id.lytProg2MAHAdsExtDlg));


            Button btnYes = ((Button) view.findViewById(R.id.mah_ads_dlg_exit_btn_yes));
            Button btnNo = (Button) view.findViewById(R.id.mah_ads_dlg_exit_btn_no);
            ImageView ivBtnCancel = ((ImageView) view.findViewById(R.id.mah_ads_dlg_exit_btnCancel));
            ImageView ivBtnInfo = ((ImageView) view.findViewById(R.id.mah_ads_dlg_exit_btnInfo));
            btnYes.setOnClickListener(this);
            btnNo.setOnClickListener(this);
            ivBtnCancel.setOnClickListener(this);
            ivBtnInfo.setOnClickListener(this);
            view.findViewById(R.id.mah_ads_dlg_exit_lyt_btn_other).setOnClickListener(this);


            tvFresnestProg1 = ((TextView) view.findViewById(R.id.tvProg1NewText));
            tvFresnestProg2 = ((TextView) view.findViewById(R.id.tvProg2NewText));
            tvFresnestProg1.setVisibility(View.GONE);
            tvFresnestProg2.setVisibility(View.GONE);

            ((ImageView) view.findViewById(R.id.mah_ads_dlg_exit_iv_play_store_btn_other)).setColorFilter(ContextCompat.getColor(getContext(), R.color.mah_ads_all_and_btn_text_color));
            ivBtnCancel.setColorFilter(ContextCompat.getColor(getContext(), R.color.mah_ads_title_bar_text_color));
            ivBtnInfo.setColorFilter(ContextCompat.getColor(getContext(), R.color.mah_ads_title_bar_text_color));

            final ScrollView scw = ((ScrollView) view.findViewById(R.id.mah_ads_dlg_scroll));
            scw.post(new Runnable() {

                @Override
                public void run() {
                    scw.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });

            setUi(MAHAdsController.getSelectedPrograms());

            MAHAdsController.getUpdater().updateProgramList(getActivityMAH());

            MAHAdsController.setFontTextView((TextView) view.findViewById(R.id.tvTitle));
            MAHAdsController.setFontTextView((TextView) view.findViewById(R.id.tvProg1NewText));
            MAHAdsController.setFontTextView((TextView) view.findViewById(R.id.tvProg2NewText));
            MAHAdsController.setFontTextView((TextView) view.findViewById(R.id.tvProg1NameMAHAdsExtDlg));
            MAHAdsController.setFontTextView((TextView) view.findViewById(R.id.tvProg2NameMAHAdsExtDlg));
            MAHAdsController.setFontTextView(tvAsBtnMore);
            MAHAdsController.setFontTextView((TextView) view.findViewById(R.id.tvQuestionTxt));
            MAHAdsController.setFontTextView(btnYes);
            MAHAdsController.setFontTextView(btnNo);

            return view;
        } catch (MAHFragmentExeption e) {
            Log.d(MAHAdsController.LOG_TAG_MAH_ADS, e.getMessage(), e);
            return null;
        }
    }

    public ViewGroup getLytProgsPanel() {
        return lytProgsPanel;
    }

    public void setUi(List<Program> programsSelected) {
        Drawable imgNotFoundDrawable = ContextCompat.getDrawable(getContext(), R.drawable.img_not_found);
        imgNotFoundDrawable.setColorFilter(ContextCompat.getColor(getContext(), R.color.mah_ads_all_and_btn_text_color), PorterDuff.Mode.SRC_ATOP);

        if (programsSelected == null || programsSelected.size() <= 0) {
            if (programsSelected == null) {
                exitCallback.onEventHappened("MAHAdsController programSelected is null");
            }
            lytProgsPanel.setVisibility(View.GONE);
            tvAsBtnMore.setText(view.getResources().getString(R.string.mah_ads_dlg_exit_btn_more_txt_1));
        } else if (programsSelected.size() == 1) {
            lytProgsPanel.setVisibility(View.VISIBLE);
            lytProg2MAHAdsExtDlg.setVisibility(View.GONE);
            prog1 = programsSelected.get(0);
            ((TextView) view.findViewById(R.id.tvProg1NameMAHAdsExtDlg)).setText(prog1.getName());

            Glide.with(getContext())
                    .load(Utils.getUrlOfImage(prog1.getImg()))
                    .centerCrop()
                    .placeholder(R.drawable.img_place_holder_normal)
                    .crossFade()
                    .error(imgNotFoundDrawable)
                    .into((ImageView) view.findViewById(R.id.ivProg1ImgMAHAds));
            String freshnestStr = prog1.getFreshnestStr(getContext());
            if (freshnestStr != null) {
                tvFresnestProg1.setTextSize(TypedValue.COMPLEX_UNIT_SP, prog1.getFreshnestStrTextSizeInSP(getContext()));
                tvFresnestProg1.setText(freshnestStr);
                RotateAnimation animRotate = (RotateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.tv_rotate);
                animRotate.setFillAfter(true); //For the textview to remain at the same place after the rotation
                tvFresnestProg1.setAnimation(animRotate);
                tvFresnestProg1.setVisibility(View.VISIBLE);
            } else {
                tvFresnestProg1.setVisibility(View.GONE);
            }
            lytProg1MAHAdsExtDlg.setOnClickListener(MAHAdsDlgExit.this);
            tvAsBtnMore.setText(view.getResources().getString(R.string.mah_ads_dlg_exit_btn_more_txt_2));
        } else {
            lytProgsPanel.setVisibility(View.VISIBLE);
            lytProg2MAHAdsExtDlg.setVisibility(View.VISIBLE);

            prog1 = programsSelected.get(0);
            ((TextView) view.findViewById(R.id.tvProg1NameMAHAdsExtDlg)).setText(prog1.getName());
            Glide.with(getContext())
                    .load(Utils.getUrlOfImage(prog1.getImg()))
                    .centerCrop()
                    .placeholder(R.drawable.img_place_holder_normal)
                    .crossFade()
                    .error(imgNotFoundDrawable)
                    .into((ImageView) view.findViewById(R.id.ivProg1ImgMAHAds));


            String freshnestStr = prog1.getFreshnestStr(getContext());
            if (freshnestStr != null) {
                tvFresnestProg1.setTextSize(TypedValue.COMPLEX_UNIT_SP, prog1.getFreshnestStrTextSizeInSP(getContext()));
                tvFresnestProg1.setText(freshnestStr);
                RotateAnimation animRotate = (RotateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.tv_rotate);
                animRotate.setFillAfter(true); //For the textview to remain at the same place after the rotation
                tvFresnestProg1.setAnimation(animRotate);
                tvFresnestProg1.setVisibility(View.VISIBLE);
            } else {
                tvFresnestProg1.setVisibility(View.GONE);
            }

            prog2 = programsSelected.get(1);
            ((TextView) view.findViewById(R.id.tvProg2NameMAHAdsExtDlg)).setText(prog2.getName());

//                    Picasso.with(view.getContext())
//                            .load(MAHAdsController.urlRootOnServer + prog2.getImg())
//                            .placeholder(R.drawable.img_place_holder_normal)
//                            .error(imgNotFoundDrawable)
//                            .into((ImageView) view.findViewById(R.id.ivProg2ImgMAHAds));

            Glide.with(getContext())
                    .load(Utils.getUrlOfImage(prog2.getImg()))
                    .centerCrop()
                    .placeholder(R.drawable.img_place_holder_normal)
                    .crossFade()
                    .error(imgNotFoundDrawable)
                    .into((ImageView) view.findViewById(R.id.ivProg2ImgMAHAds));

            String freshnestStr2 = prog2.getFreshnestStr(getContext());
            if (freshnestStr2 != null) {
                tvFresnestProg2.setTextSize(TypedValue.COMPLEX_UNIT_SP, prog2.getFreshnestStrTextSizeInSP(getContext()));
                tvFresnestProg2.setText(freshnestStr2);
                RotateAnimation animRotate = (RotateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.tv_rotate);
                animRotate.setFillAfter(true); //For the textview to remain at the same place after the rotation
                tvFresnestProg2.setAnimation(animRotate);
                tvFresnestProg2.setVisibility(View.VISIBLE);
            } else {
                tvFresnestProg2.setVisibility(View.GONE);
            }

            lytProg1MAHAdsExtDlg.setOnClickListener(MAHAdsDlgExit.this);
            lytProg2MAHAdsExtDlg.setOnClickListener(MAHAdsDlgExit.this);
            tvAsBtnMore.setText(view.getResources().getString(R.string.mah_ads_dlg_exit_btn_more_txt_2));
        }
    }

    public void onYes() {
        try {
            dismissAllowingStateLoss();
            exitCallback.onYes();
            getActivityMAH().finish();
            //The problem when appears on application close is for the transition animation time difference.
            //Time for home screen animation and other animation is differenet
            //Some times it shows reappearing dialog on application close
            //There for i call dismiss() and later call for finish()
        } catch (MAHFragmentExeption e) {
            Log.d(MAHAdsController.LOG_TAG_MAH_ADS, e.getMessage(), e);
            return;
        }
    }


    public void onNo() {
        exitCallback.onNo();
        dismissAllowingStateLoss();
    }

    private void showMAHlib() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MAH_ADS_GITHUB_LINK));
        getContext().startActivity(browserIntent);
    }

    public void openAppOrMarketAcitivity(String pckgName) {
        try {
            if (Utils.checkPackageIfExists(getActivityMAH(), pckgName)) {
                PackageManager pack = getActivityMAH().getPackageManager();
                Intent app = pack.getLaunchIntentForPackage(pckgName);
                app.putExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, true);
                getContext().startActivity(app);
            } else {
                if (!pckgName.isEmpty()) {
                    Utils.showMarket(getContext(), pckgName);
                }
            }
        } catch (MAHFragmentExeption e) {
            Log.d(MAHAdsController.LOG_TAG_MAH_ADS, e.getMessage(), e);
            return;
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == R.id.mah_ads_dlg_exit_btnCancel) {
                dismiss();
            } else if (v.getId() == R.id.mah_ads_dlg_exit_btnInfo) {

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
            } else if (v.getId() == R.id.mah_ads_dlg_exit_btn_yes) {
                onYes();
            } else if (v.getId() == R.id.mah_ads_dlg_exit_btn_no) {
                onNo();
            } else if (v.getId() == R.id.mah_ads_dlg_exit_lyt_btn_other) {
                MAHAdsController.callProgramsDialog(getActivityMAH(), withPopupInfoMenu);
            } else if (v.getId() == R.id.lytProg1MAHAdsExtDlg && prog1 != null) {
                openAppOrMarketAcitivity(prog1.getUri().trim());
            } else if (v.getId() == R.id.lytProg2MAHAdsExtDlg && prog2 != null) {
                openAppOrMarketAcitivity(prog2.getUri().trim());
            }
        } catch (MAHFragmentExeption e) {
            Log.d(MAHAdsController.LOG_TAG_MAH_ADS, e.getMessage(), e);
            return;
        }
    }
}