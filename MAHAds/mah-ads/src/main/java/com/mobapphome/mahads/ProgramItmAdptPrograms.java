package com.mobapphome.mahads;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobapphome.mahads.tools.MAHAdsController;
import com.mobapphome.mahads.tools.Utils;
import com.mobapphome.mahads.types.Program;

import java.util.List;

public class ProgramItmAdptPrograms extends BaseAdapter implements
        View.OnClickListener {

    private final String TAG = ProgramItmAdptPrograms.class.getName();
    private List<Object> items;
    private static LayoutInflater inflater = null;


    public ProgramItmAdptPrograms(Context context, List<Object> items) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public void onClick(View arg0) {

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Object obj = items.get(position);
        if (obj instanceof Program) {
            final Program currProgram = (Program) obj;
            final String pckgName = currProgram.getUri().trim();

            // For hole view for click----------------------------------
            final View vi = inflater.inflate(R.layout.program_item_programs, null);
            vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (Utils.checkPackageIfExists(vi.getContext(), pckgName)) {
                        PackageManager pack = vi.getContext().getPackageManager();
                        Intent app = pack.getLaunchIntentForPackage(pckgName);
                        app.putExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, true);
                        vi.getContext().startActivity(app);
                    } else {
                        if (!pckgName.isEmpty()) {
                            Utils.showMarket(inflater.getContext(), pckgName);
                        }
                    }
                }
            });

            final TextView tvProgramNewText = ((TextView)vi.findViewById(R.id.tvNewText));
            tvProgramNewText.setVisibility(View.GONE);
            String freshnestStr = currProgram.getFreshnestStr(inflater.getContext());
            if (freshnestStr != null) {
                tvProgramNewText.setTextSize(TypedValue.COMPLEX_UNIT_SP, currProgram.getFreshnestStrTextSizeInSP(inflater.getContext()));
                tvProgramNewText.setText(freshnestStr);
                RotateAnimation animRotate = (RotateAnimation) AnimationUtils.loadAnimation(inflater.getContext(), R.anim.tv_rotate);
                animRotate.setFillAfter(true); //For the textview to remain at the same place after the rotation
                tvProgramNewText.startAnimation(animRotate);
                tvProgramNewText.setVisibility(View.VISIBLE);
            } else {
                tvProgramNewText.setVisibility(View.GONE);
            }

            TextView nameTV = (TextView) vi.findViewById(R.id.tvProgramNameMAHAds);
            TextView descTV = (TextView) vi.findViewById(R.id.tvProgramDescMAHAds);
            ImageView ivImg = (ImageView) vi.findViewById(R.id.ivProgramImgMAHAds);
            TextView tvOpenGooglePLay = (TextView) vi.findViewById(R.id.tvOpenInstallMAHAds);

            if (Utils.checkPackageIfExists(vi.getContext(), pckgName)) {
                tvOpenGooglePLay.setText(vi.getContext().getResources().getString(R.string.cmnd_verb_mah_ads_open_program));
            } else {
                tvOpenGooglePLay.setText(vi.getContext().getResources().getString(R.string.cmnd_verb_mah_ads_install_program));
            }
            // Setting all values in listview
            nameTV.setText(currProgram.getName());
            descTV.setText(currProgram.getDesc());

            Log.i(MAHAdsController.LOG_TAG_MAH_ADS, Utils.getUrlOfImage(currProgram.getImg()));

            Drawable imgNotFoundDrawable = ContextCompat.getDrawable(inflater.getContext(), R.drawable.img_not_found);
            imgNotFoundDrawable.setColorFilter(ContextCompat.getColor(inflater.getContext(), R.color.mah_ads_no_image_color), PorterDuff.Mode.SRC_IN);

            Glide.with(vi.getContext())
                    .load(Utils.getUrlOfImage(currProgram.getImg()))
                    .centerCrop()
                    .placeholder(R.drawable.img_place_holder_normal)
                    .crossFade()
                    .error(imgNotFoundDrawable)
                    .into(ivImg);




            ImageView ivMore = (ImageButton) vi.findViewById(R.id.btnOverflowMAHAds);
            ivMore.setColorFilter(ContextCompat.getColor(inflater.getContext(), R.color.mah_ads_all_and_btn_text_color));

            ivMore.setImageResource(R.drawable.ic_more_vert_grey600_24dp);

            ivMore.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(vi.getContext(), v);
                    // Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.program_popup_menu, popup.getMenu());
                    // registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.popupMenuOpenOnGoogleP) {
                                if (!pckgName.isEmpty()) {
                                    Utils.showMarket(inflater.getContext(), pckgName);
                                }
                            }
                            return true;
                        }
                    });

                    popup.show();// showing popup menu
                }
            });

            MAHAdsController.setFontTextView((TextView) vi.findViewById(R.id.tvNewText));
            MAHAdsController.setFontTextView(nameTV);
            MAHAdsController.setFontTextView(descTV);
            MAHAdsController.setFontTextView(tvOpenGooglePLay);
            return vi;
        } else {
            return null;
        }

    }
}
