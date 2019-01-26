package com.mobapphome.appcrosspromoter

/**
 * Created by settar on 7/12/16.
 */

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.PopupMenu
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.ScrollView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mobapphome.appcrosspromoter.commons.*
import com.mobapphome.appcrosspromoter.tools.*
import kotlinx.android.synthetic.main.acp_dialog_exit.*


class ACPDlgExit(
        var prog1: Program? = null,
        var prog2: Program? = null,
        var exitCallback: ACPDlgExitListener? = null,

        var urls: Urls? = null,
        var fontName: String? = null,
        var btnInfoVisibility: Boolean = false,
        var btnInfoWithMenu: Boolean = false,
        var btnInfoMenuItemTitle: String? = null,
        var btnInfoActionURL: String? = null,
        var mahRequestResult: MAHRequestResult? = null)
    : MAHDialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MAHAdsDlgExit)
        Log.i(Constants.LOG_TAG_MAH_ADS, "Exit dialog greated")
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        try {
            Log.i(Constants.LOG_TAG_MAH_ADS, "MAH Ads Dld exit Created ")

            val args = arguments
            val gson = Gson()
            mahRequestResult = gson.fromJson(args!!.getString("_mahRequestResult"), MAHRequestResult::class.java)
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
                exitCallback = activityMAH as ACPDlgExitListener
            } catch (e: ClassCastException) {
                throw ClassCastException(activityMAH.toString() + " must implement ACPDlgExitListener")
            }


            dialog.window!!.attributes.windowAnimations = R.style.MAHAdsDialogAnimation
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false)
            dialog.setOnKeyListener { dialog, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    onNo()
                }
                false
            }

            return inflater!!.inflate(R.layout.acp_dialog_exit, container)
        } catch (e: MAHFragmentExeption) {
            Log.d(Constants.LOG_TAG_MAH_ADS, e.message, e)
            return null
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnYes.setOnClickListener { sorrWithMAHExeption { onYes() } }
        btnNo.setOnClickListener { sorrWithMAHExeption { onNo() } }
        ivBtnCancel.setOnClickListener { sorrWithMAHExeption { dismissAllowingStateLoss() } }
        ivBtnInfo.setOnClickListener { v ->
            sorrWithMAHExeption {
                if (btnInfoWithMenu) {
                    val itemIdForInfo = 1
                    val popup = PopupMenu(context!!, v)
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
            }
        }
        lytBtnOther.setOnClickListener {
            sorrWithMAHExeption {
                ACPController.showDlg(activityMAH,
                        ACPDlgPrograms.newInstance(mahRequestResult, urls, fontName, btnInfoVisibility, btnInfoWithMenu, btnInfoMenuItemTitle!!, btnInfoActionURL!!),
                        Constants.TAG_MAH_ADS_DLG_PROGRAMS)
            }
        }

        if (btnInfoVisibility) ivBtnInfo.makeVisible() else ivBtnInfo.makeInvisible()


        tvFreshnestProg1.makeGone()
        tvFreshnestProg2.makeGone()

        iconBtnOther.setColorFilterCompat(R.color.acp_all_and_btn_text_color)
        ivBtnCancel.setColorFilterCompat(R.color.acp_title_bar_text_color)
        ivBtnInfo.setColorFilterCompat( R.color.acp_title_bar_text_color)

        mah_ads_dlg_scroll.post { mah_ads_dlg_scroll.fullScroll(ScrollView.FOCUS_DOWN) }

        setUi(mahRequestResult)

        if (savedInstanceState == null) Updater.updateProgramList(activityMAH, urls!!)


        tvTitle.setFontTextView(fontName)
        tvFreshnestProg1.setFontTextView(fontName)
        tvFreshnestProg2.setFontTextView(fontName)
        tvProg1NameMAHAdsExtDlg.setFontTextView(fontName)
        tvProg2NameMAHAdsExtDlg.setFontTextView(fontName)
        mah_ads_dlg_exit_tv_btn_other.setFontTextView(fontName)
        tvQuestionTxt.setFontTextView(fontName)
        btnYes.setFontTextView(fontName)
        btnNo.setFontTextView(fontName)

        //Minimize the lines of question textview in  languages where question str
        val strQuest = getString(R.string.acp_dlg_exit_question)
        if (strQuest.length > 20) {
            tvQuestionTxt.minLines = 2
        }
        //            else {
        //                tvQuestionTxt.setMinLines(1);
        //            }
    }

    fun setUi(mahRequestResult: MAHRequestResult?) {
        val imgNotFoundDrawable = context!!.getDrawableWithColorFilter( R.drawable.img_not_found, R.color.acp_no_image_color)

        //if (_mahRequestResult == null || _mahRequestResult.programsSelected == null || _mahRequestResult.programsSelected!!.isEmpty() ) {
        if (mahRequestResult?.programsSelected?.isEmpty() ?: true) {

            if (mahRequestResult != null && mahRequestResult.programsSelected == null) {
                exitCallback!!.onEventHappened("ACPController programSelected is null")
            }

            lytProgsPanel.makeGone()
            mah_ads_dlg_exit_tv_btn_other.text = view!!.resources.getString(R.string.acp_dlg_exit_btn_more_txt_1)
        } else if (mahRequestResult?.programsSelected?.size == 1) {
            lytProgsPanel.makeVisible()
            lytProg2MAHAdsExtDlg.makeGone()
            prog1 = mahRequestResult.programsSelected!![0]
            tvProg1NameMAHAdsExtDlg.text = prog1!!.name


            Glide.with(context)
                    .load(getUrlOfImage(urls!!.urlRootOnServer!!, prog1!!.img))
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.img_place_holder_normal)
                    .crossFade()
                    .error(imgNotFoundDrawable)
                    .into(ivProg1ImgMAHAds)
            val freshnestStr = prog1!!.getFreshnestStr(context!!)
            if (freshnestStr != null) {
                tvFreshnestProg1.setTextSize(TypedValue.COMPLEX_UNIT_SP, prog1!!.getFreshnestStrTextSizeInSP(context!!).toFloat())
                tvFreshnestProg1.text = freshnestStr
                tvFreshnestProg1.startAnimationFillAfter(R.anim.tv_rotate)
                tvFreshnestProg1.makeVisible()
            } else {
                tvFreshnestProg1.makeGone()
            }

            lytProg1MAHAdsExtDlg.setOnClickListener {
                sorrWithMAHExeption {
                    if (prog1 != null) openAppOrMarketAcitivity(prog1!!.uri.trim { it <= ' ' })
                }
            }
            mah_ads_dlg_exit_tv_btn_other.text = view!!.resources.getString(R.string.acp_dlg_exit_btn_more_txt_2)
        } else {
            lytProgsPanel.makeVisible()
            lytProg2MAHAdsExtDlg.makeVisible()

            prog1 = mahRequestResult!!.programsSelected!![0]
            tvProg1NameMAHAdsExtDlg.text = prog1!!.name
            Glide.with(context)
                    .load(getUrlOfImage(urls!!.urlRootOnServer!!, prog1!!.img))
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.img_place_holder_normal)
                    .crossFade()
                    .error(imgNotFoundDrawable)
                    .into(ivProg1ImgMAHAds)


            val freshnestStr = prog1!!.getFreshnestStr(context!!)
            if (freshnestStr != null) {
                tvFreshnestProg1.setTextSize(TypedValue.COMPLEX_UNIT_SP, prog1!!.getFreshnestStrTextSizeInSP(context!!).toFloat())
                tvFreshnestProg1.text = freshnestStr
                tvFreshnestProg1.startAnimationFillAfter(R.anim.tv_rotate)
                tvFreshnestProg1.makeVisible()
            } else {
                tvFreshnestProg1.clearAnimation()
                tvFreshnestProg1.makeGone()
            }

            prog2 = mahRequestResult.programsSelected!![1]
            tvProg2NameMAHAdsExtDlg.text = prog2!!.name

            Glide.with(context)
                    .load(getUrlOfImage(urls!!.urlRootOnServer!!, prog2!!.img))
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.img_place_holder_normal)
                    .crossFade()
                    .error(imgNotFoundDrawable)
                    .into(ivProg2ImgMAHAds)

            val freshnestStr2 = prog2!!.getFreshnestStr(context!!)
            if (freshnestStr2 != null) {
                tvFreshnestProg2.setTextSize(TypedValue.COMPLEX_UNIT_SP, prog2!!.getFreshnestStrTextSizeInSP(context!!).toFloat())
                tvFreshnestProg2.text = freshnestStr2
                tvFreshnestProg2.startAnimationFillAfter(R.anim.tv_rotate)
                tvFreshnestProg2.makeVisible()
            } else {
                tvFreshnestProg2.clearAnimation()
                tvFreshnestProg2.makeGone()
            }

            lytProg1MAHAdsExtDlg.setOnClickListener {
                sorrWithMAHExeption {
                    if (prog1 != null) openAppOrMarketAcitivity(prog1!!.uri.trim { it <= ' ' })

                }
            }
            lytProg2MAHAdsExtDlg.setOnClickListener {
                sorrWithMAHExeption {
                    if (prog2 != null) openAppOrMarketAcitivity(prog2!!.uri.trim { it <= ' ' })
                }
            }
            mah_ads_dlg_exit_tv_btn_other.text = view!!.resources.getString(R.string.acp_dlg_exit_btn_more_txt_2)
            //Log.i(Constants.LOG_TAG_MAH_ADS, "freshnestStr1 = " + freshnestStr + " freshnestStr2 = " + freshnestStr2);
        }
    }

    fun onYes() = sorrWithMAHExeption {
        dismissAllowingStateLoss()
        exitCallback!!.onYes()
        activityMAH.finish()
        //The problem when appears on application close is for the transition animation time difference.
        //Time for home screen animation and other animation is differenet
        //Some times it shows reappearing dialog on application close
        //There for i call dismiss() and later call for finish()
    }


    fun onNo() {
        exitCallback!!.onNo()
        dismissAllowingStateLoss()
    }

    private fun showMAHlib() =
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(btnInfoActionURL))
                context!!.startActivity(browserIntent)
            } catch (nfe: ActivityNotFoundException) {
                val str = "You haven't set correct url to btnInfoActionURL, your url = " + btnInfoActionURL
                Toast.makeText(context, str, Toast.LENGTH_LONG).show()
                Log.d(Constants.LOG_TAG_MAH_ADS, str, nfe)
            }


    fun openAppOrMarketAcitivity(pckgName: String) = sorrWithMAHExeption {
        if (checkPackageIfExists(activityMAH, pckgName)) {
            val pack = activityMAH.packageManager
            val app = pack.getLaunchIntentForPackage(pckgName)
            app.putExtra(Constants.MAH_ADS_INTERNAL_CALLED, true)
            context!!.startActivity(app)
        } else {
            if (!pckgName.isEmpty()) {
                showMarket(context!!, pckgName)
            } else {
            }
        }
    }


    val isProgramsPanelVisible: Boolean
        get() = lytProgsPanel!!.isVisible()


    companion object {

        fun newInstance(mahRequestResult: MAHRequestResult?,
                        urls: Urls?,
                        fontName: String?,
                        btnInfoVisibility: Boolean,
                        btnInfoWithMenu: Boolean,
                        btnInfoMenuItemTitle: String,
                        btnInfoActionURL: String): ACPDlgExit {
            val dialog = ACPDlgExit()
            val args = Bundle()
            val gson = Gson()
            args.putString("_mahRequestResult", gson.toJson(mahRequestResult))
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

    interface ACPDlgExitListener {
        fun onYes()

        fun onNo()

        fun onExitWithoutExitDlg()

        fun onEventHappened(eventStr: String)
    }

}// Empty constructor required for DialogFragment