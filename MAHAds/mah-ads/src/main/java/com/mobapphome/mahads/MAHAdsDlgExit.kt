package com.mobapphome.mahads

/**
 * Created by settar on 7/12/16.
 */

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.PopupMenu
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import android.widget.*
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mobapphome.mahads.mahfragments.MAHDialogFragment
import com.mobapphome.mahads.mahfragments.MAHFragmentExeption
import com.mobapphome.mahads.mahfragments.TextViewFontSetter
import com.mobapphome.mahads.tools.*


class MAHAdsDlgExit : MAHDialogFragment(), View.OnClickListener {
    var prog1: Program? = null
    var prog2: Program? = null
    var exitCallback: MAHAdsDlgExitListener? = null
    var lytProgsPanel: ViewGroup? = null
    var lytProg1MAHAdsExtDlg: ViewGroup? = null
    var lytProg2MAHAdsExtDlg: ViewGroup? = null
    var tvAsBtnMore: TextView? = null

    var tvFresnestProg1: TextView? = null
    var tvFresnestProg2: TextView? = null

    var urls: Urls? = null
    var fontName: String? = null
    var btnInfoVisibility: Boolean = false
    var btnInfoWithMenu: Boolean = false
    var btnInfoMenuItemTitle: String? = null
    var btnInfoActionURL: String? = null
    var mahRequestResult: MAHRequestResult? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MAHAdsDlgExit)
        Log.i(Constants.LOG_TAG_MAH_ADS, "Exit dialog greated")
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        try {
            Log.i(Constants.LOG_TAG_MAH_ADS, "MAH Ads Dld exit Created ")

            val args = arguments
            val gson = Gson()
            mahRequestResult = gson.fromJson(args.getString("mahRequestResult"), MAHRequestResult::class.java)
            urls = gson.fromJson(args.getString("urls"), Urls::class.java)
            fontName = args.getString("fontName")
            btnInfoVisibility = args.getBoolean("btnInfoVisibility")
            btnInfoWithMenu = args.getBoolean("btnInfoWithMenu")
            btnInfoMenuItemTitle = args.getString("btnInfoMenuItemTitle")
            btnInfoActionURL = args.getString("btnInfoActionURL")

            Log.i(Constants.LOG_TAG_MAH_ADS, "With popInfoMenu" + btnInfoWithMenu)
            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                exitCallback = activityMAH as MAHAdsDlgExitListener
            } catch (e: ClassCastException) {
                throw ClassCastException(activityMAH.toString() + " must implement MAHAdsDlgExitListener")
            }

            val view = inflater!!.inflate(R.layout.mah_ads_dialog_exit, container)

            dialog.window!!.attributes.windowAnimations = R.style.MAHAdsDialogAnimation
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false)
            dialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {

                    onNo()
                    return@OnKeyListener true
                }
                false
            })

            tvAsBtnMore = view!!.findViewById(R.id.mah_ads_dlg_exit_tv_btn_other) as TextView
            lytProgsPanel = view!!.findViewById(R.id.lytProgsPanel) as ViewGroup
            lytProg1MAHAdsExtDlg = view!!.findViewById(R.id.lytProg1MAHAdsExtDlg) as ViewGroup
            lytProg2MAHAdsExtDlg = view!!.findViewById(R.id.lytProg2MAHAdsExtDlg) as ViewGroup


            val btnYes = view!!.findViewById(R.id.mah_ads_dlg_exit_btn_yes) as Button
            val btnNo = view!!.findViewById(R.id.mah_ads_dlg_exit_btn_no) as Button
            val ivBtnCancel = view!!.findViewById(R.id.mah_ads_dlg_exit_btnCancel) as ImageView
            val ivBtnInfo = view!!.findViewById(R.id.mah_ads_dlg_exit_btnInfo) as ImageView
            btnYes.setOnClickListener(this)
            btnNo.setOnClickListener(this)
            ivBtnCancel.setOnClickListener(this)
            ivBtnInfo.setOnClickListener(this)
            view!!.findViewById(R.id.mah_ads_dlg_exit_lyt_btn_other).setOnClickListener(this)

            if (btnInfoVisibility) {
                ivBtnInfo.visibility = View.VISIBLE
            } else {
                ivBtnInfo.visibility = View.INVISIBLE
            }


            tvFresnestProg1 = view!!.findViewById(R.id.tvProg1NewText) as TextView
            tvFresnestProg2 = view!!.findViewById(R.id.tvProg2NewText) as TextView
            tvFresnestProg1!!.visibility = View.GONE
            tvFresnestProg2!!.visibility = View.GONE

            (view!!.findViewById(R.id.mah_ads_dlg_exit_iv_play_store_btn_other) as ImageView).setColorFilter(ContextCompat.getColor(context, R.color.mah_ads_all_and_btn_text_color))
            ivBtnCancel.setColorFilter(ContextCompat.getColor(context, R.color.mah_ads_title_bar_text_color))
            ivBtnInfo.setColorFilter(ContextCompat.getColor(context, R.color.mah_ads_title_bar_text_color))

            val scw = view!!.findViewById(R.id.mah_ads_dlg_scroll) as ScrollView
            scw.post { scw.fullScroll(ScrollView.FOCUS_DOWN) }

            setUi(mahRequestResult)

            if (savedInstanceState == null) {
                Updater.updateProgramList(activityMAH, urls!!)
            }

            TextViewFontSetter.setFontTextView(view!!.findViewById(R.id.tvTitle) as TextView, fontName)
            TextViewFontSetter.setFontTextView(view!!.findViewById(R.id.tvProg1NewText) as TextView, fontName)
            TextViewFontSetter.setFontTextView(view!!.findViewById(R.id.tvProg2NewText) as TextView, fontName)
            TextViewFontSetter.setFontTextView(view!!.findViewById(R.id.tvProg1NameMAHAdsExtDlg) as TextView, fontName)
            TextViewFontSetter.setFontTextView(view!!.findViewById(R.id.tvProg2NameMAHAdsExtDlg) as TextView, fontName)
            TextViewFontSetter.setFontTextView(tvAsBtnMore!!, fontName)
            TextViewFontSetter.setFontTextView(view!!.findViewById(R.id.tvQuestionTxt) as TextView, fontName)
            TextViewFontSetter.setFontTextView(btnYes, fontName)
            TextViewFontSetter.setFontTextView(btnNo, fontName)


            //Minimize the lines of question textview in  languages where question str is longer
            val tvQuestionTxt = view!!.findViewById(R.id.tvQuestionTxt) as TextView
            val strQuest = getString(R.string.mah_ads_dlg_exit_question)
            if (strQuest.length > 20) {
                tvQuestionTxt.minLines = 2
            }
            //            else {
            //                tvQuestionTxt.setMinLines(1);
            //            }

            return view
        } catch (e: MAHFragmentExeption) {
            Log.d(Constants.LOG_TAG_MAH_ADS, e.message, e)
            return null
        }

    }

    fun setUi(mahRequestResult: MAHRequestResult?) {
        val imgNotFoundDrawable = ContextCompat.getDrawable(context, R.drawable.img_not_found)
        imgNotFoundDrawable.setColorFilter(ContextCompat.getColor(context, R.color.mah_ads_no_image_color), PorterDuff.Mode.SRC_IN)

        if (mahRequestResult == null || mahRequestResult.programsSelected == null || mahRequestResult.programsSelected!!.size <= 0) {

            if (mahRequestResult != null && mahRequestResult.programsSelected == null) {
                exitCallback!!.onEventHappened("MAHAdsController programSelected is null")
            }

            lytProgsPanel!!.visibility = View.GONE
            tvAsBtnMore!!.text = view!!.resources.getString(R.string.mah_ads_dlg_exit_btn_more_txt_1)
        } else if (mahRequestResult.programsSelected!!.size == 1) {
            lytProgsPanel!!.visibility = View.VISIBLE
            lytProg2MAHAdsExtDlg!!.visibility = View.GONE
            prog1 = mahRequestResult.programsSelected!![0]
            (view!!.findViewById(R.id.tvProg1NameMAHAdsExtDlg) as TextView).text = prog1!!.name


            Glide.with(context)
                    .load(getUrlOfImage(urls!!.urlRootOnServer!!, prog1!!.img))
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.img_place_holder_normal)
                    .crossFade()
                    .error(imgNotFoundDrawable)
                    .into(view!!.findViewById(R.id.ivProg1ImgMAHAds) as ImageView)
            val freshnestStr = prog1!!.getFreshnestStr(context)
            if (freshnestStr != null) {
                tvFresnestProg1!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, prog1!!.getFreshnestStrTextSizeInSP(context).toFloat())
                tvFresnestProg1!!.text = freshnestStr
                val animRotate = AnimationUtils.loadAnimation(context, R.anim.tv_rotate) as RotateAnimation
                animRotate.fillAfter = true //For the textview to remain at the same place after the rotation
                tvFresnestProg1!!.animation = animRotate
                tvFresnestProg1!!.visibility = View.VISIBLE
            } else {
                tvFresnestProg1!!.visibility = View.GONE
            }
            lytProg1MAHAdsExtDlg!!.setOnClickListener(this@MAHAdsDlgExit)
            tvAsBtnMore!!.text = view!!.resources.getString(R.string.mah_ads_dlg_exit_btn_more_txt_2)
        } else {
            lytProgsPanel!!.visibility = View.VISIBLE
            lytProg2MAHAdsExtDlg!!.visibility = View.VISIBLE

            prog1 = mahRequestResult.programsSelected!![0]
            (view!!.findViewById(R.id.tvProg1NameMAHAdsExtDlg) as TextView).text = prog1!!.name
            Glide.with(context)
                    .load(getUrlOfImage(urls!!.urlRootOnServer!!, prog1!!.img))
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.img_place_holder_normal)
                    .crossFade()
                    .error(imgNotFoundDrawable)
                    .into(view!!.findViewById(R.id.ivProg1ImgMAHAds) as ImageView)


            val freshnestStr = prog1!!.getFreshnestStr(context)
            if (freshnestStr != null) {
                tvFresnestProg1!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, prog1!!.getFreshnestStrTextSizeInSP(context).toFloat())
                tvFresnestProg1!!.text = freshnestStr
                val animRotate = AnimationUtils.loadAnimation(context, R.anim.tv_rotate) as RotateAnimation
                animRotate.fillAfter = true //For the textview to remain at the same place after the rotation
                tvFresnestProg1!!.animation = animRotate
                tvFresnestProg1!!.visibility = View.VISIBLE
            } else {
                tvFresnestProg1!!.clearAnimation()
                tvFresnestProg1!!.visibility = View.GONE
            }

            prog2 = mahRequestResult.programsSelected!![1]
            (view!!.findViewById(R.id.tvProg2NameMAHAdsExtDlg) as TextView).text = prog2!!.name

            Glide.with(context)
                    .load(getUrlOfImage(urls!!.urlRootOnServer!!, prog2!!.img))
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.img_place_holder_normal)
                    .crossFade()
                    .error(imgNotFoundDrawable)
                    .into(view!!.findViewById(R.id.ivProg2ImgMAHAds) as ImageView)

            val freshnestStr2 = prog2!!.getFreshnestStr(context)
            if (freshnestStr2 != null) {
                tvFresnestProg2!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, prog2!!.getFreshnestStrTextSizeInSP(context).toFloat())
                tvFresnestProg2!!.text = freshnestStr2
                val animRotate = AnimationUtils.loadAnimation(context, R.anim.tv_rotate) as RotateAnimation
                animRotate.fillAfter = true //For the textview to remain at the same place after the rotation
                tvFresnestProg2!!.animation = animRotate
                tvFresnestProg2!!.visibility = View.VISIBLE
            } else {
                tvFresnestProg2!!.clearAnimation()
                tvFresnestProg2!!.visibility = View.GONE
            }

            lytProg1MAHAdsExtDlg!!.setOnClickListener(this@MAHAdsDlgExit)
            lytProg2MAHAdsExtDlg!!.setOnClickListener(this@MAHAdsDlgExit)
            tvAsBtnMore!!.text = view!!.resources.getString(R.string.mah_ads_dlg_exit_btn_more_txt_2)
            //Log.i(Constants.LOG_TAG_MAH_ADS, "freshnestStr1 = " + freshnestStr + " freshnestStr2 = " + freshnestStr2);
        }
    }

    fun onYes() {
        try {
            dismissAllowingStateLoss()
            exitCallback!!.onYes()
            activityMAH.finish()
            //The problem when appears on application close is for the transition animation time difference.
            //Time for home screen animation and other animation is differenet
            //Some times it shows reappearing dialog on application close
            //There for i call dismiss() and later call for finish()
        } catch (e: MAHFragmentExeption) {
            Log.d(Constants.LOG_TAG_MAH_ADS, e.message, e)
            return
        }

    }


    fun onNo() {
        exitCallback!!.onNo()
        dismissAllowingStateLoss()
    }

    private fun showMAHlib() {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(btnInfoActionURL))
            context.startActivity(browserIntent)
        } catch (nfe: ActivityNotFoundException) {
            val str = "You haven't set correct url to btnInfoActionURL, your url = " + btnInfoActionURL
            Toast.makeText(context, str, Toast.LENGTH_LONG).show()
            Log.d(Constants.LOG_TAG_MAH_ADS, str, nfe)
        }

    }

    fun openAppOrMarketAcitivity(pckgName: String) {
        try {
            if (checkPackageIfExists(activityMAH, pckgName)) {
                val pack = activityMAH.packageManager
                val app = pack.getLaunchIntentForPackage(pckgName)
                app.putExtra(Constants.MAH_ADS_INTERNAL_CALLED, true)
                context.startActivity(app)
            } else {
                if (!pckgName.isEmpty()) {
                    showMarket(context, pckgName)
                }
            }
        } catch (e: MAHFragmentExeption) {
            Log.d(Constants.LOG_TAG_MAH_ADS, e.message, e)
            return
        }

    }

    override fun onClick(v: View) {
        try {
            if (v.id == R.id.mah_ads_dlg_exit_btnCancel) {
                dismissAllowingStateLoss()
            } else if (v.id == R.id.mah_ads_dlg_exit_btnInfo) {

                if (btnInfoWithMenu) {
                    val itemIdForInfo = 1
                    val popup = PopupMenu(context, v)
                    popup.menu.add(Menu.NONE, itemIdForInfo, 1, btnInfoMenuItemTitle)

                    // registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener { item ->
                        if (item.itemId == itemIdForInfo) {
                            showMAHlib()
                        }
                        true
                    }

                    popup.show()// showing popup menu
                } else {
                    showMAHlib()
                }
            } else if (v.id == R.id.mah_ads_dlg_exit_btn_yes) {
                onYes()
            } else if (v.id == R.id.mah_ads_dlg_exit_btn_no) {
                onNo()
            } else if (v.id == R.id.mah_ads_dlg_exit_lyt_btn_other) {
                MAHAdsController.showDlg(activityMAH,
                        MAHAdsDlgPrograms.newInstance(mahRequestResult!!, urls!!, fontName!!, btnInfoVisibility, btnInfoWithMenu, btnInfoMenuItemTitle!!, btnInfoActionURL!!),
                        Constants.TAG_MAH_ADS_DLG_PROGRAMS)
            } else if (v.id == R.id.lytProg1MAHAdsExtDlg && prog1 != null) {
                openAppOrMarketAcitivity(prog1!!.uri.trim { it <= ' ' })
            } else if (v.id == R.id.lytProg2MAHAdsExtDlg && prog2 != null) {
                openAppOrMarketAcitivity(prog2!!.uri.trim { it <= ' ' })
            }
        } catch (e: MAHFragmentExeption) {
            Log.d(Constants.LOG_TAG_MAH_ADS, e.message, e)
            return
        }

    }

    val isProgramsPanelVisible: Boolean
        get() {
            if (lytProgsPanel!!.visibility == View.VISIBLE) {
                return true
            } else {
                return false
            }
        }

    companion object {

        fun newInstance(mahRequestResult: MAHRequestResult,
                        urls: Urls,
                        fontName: String,
                        btnInfoVisibility: Boolean,
                        btnInfoWithMenu: Boolean,
                        btnInfoMenuItemTitle: String,
                        btnInfoActionURL: String): MAHAdsDlgExit {
            val dialog = MAHAdsDlgExit()
            val args = Bundle()
            val gson = Gson()
            args.putString("mahRequestResult", gson.toJson(mahRequestResult))
            args.putString("urls", gson.toJson(urls))
            args.putString("fontName", fontName)
            args.putBoolean("btnInfoVisibility", btnInfoVisibility)
            args.putBoolean("btnInfoWithMenu", btnInfoWithMenu)
            args.putString("btnInfoMenuItemTitle", btnInfoMenuItemTitle)
            args.putString("btnInfoActionURL", btnInfoActionURL)
            dialog.arguments = args
            return dialog
        }
    }
}// Empty constructor required for DialogFragment