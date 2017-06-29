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
import android.view.*
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.*
import com.google.gson.Gson
import com.mobapphome.mahads.mahfragments.MAHDialogFragment
import com.mobapphome.mahads.mahfragments.MAHFragmentExeption
import com.mobapphome.mahads.mahfragments.TextViewFontSetter
import com.mobapphome.mahads.tools.Constants
import com.mobapphome.mahads.tools.MAHRequestResult
import com.mobapphome.mahads.tools.Updater
import com.mobapphome.mahads.tools.Urls
import java.util.*

class MAHAdsDlgPrograms : MAHDialogFragment(), View.OnClickListener {
    var tvErrorResultF1: TextView? = null
    var ivLoading: ImageView? = null
    var lstProgram: ListView? = null
    var lytErrorF1: LinearLayout? = null
    var items: MutableList<Any>? = null
    var mahRequestResult: MAHRequestResult? = null
    var urls: Urls? = null
    var fontName: String? = null
    var btnInfoVisibility: Boolean = false
    var btnInfoWithMenu: Boolean = false
    var btnInfoMenuItemTitle: String? = null
    var btnInfoActionURL: String? = null
    var dataHasAlreadySet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MAHAdsDlgPrograms)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        try {
            Log.i(Constants.LOG_TAG_MAH_ADS, "MAH Ads Programs Dlg Created ")

            val args = arguments
            val gson = Gson()
            mahRequestResult = gson.fromJson(args.getString("mahRequestResult"), MAHRequestResult::class.java)
            urls = gson.fromJson(args.getString("urls"), Urls::class.java)
            fontName = args.getString("fontName")
            btnInfoVisibility = args.getBoolean("btnInfoVisibility")
            btnInfoWithMenu = args.getBoolean("btnInfoWithMenu")
            btnInfoMenuItemTitle = args.getString("btnInfoMenuItemTitle")
            btnInfoActionURL = args.getString("btnInfoActionURL")

            val view = inflater!!.inflate(R.layout.mah_ads_dialog_programs, container)

            dialog.window!!.attributes.windowAnimations = R.style.MAHAdsDialogAnimation
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false)
            dialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {

                    onClose()
                    return@OnKeyListener true
                }
                false
            })


            lstProgram = view.findViewById(R.id.lstMahAds) as ListView
            lytErrorF1 = view.findViewById(R.id.lytErrorMAHAds) as LinearLayout
            tvErrorResultF1 = view.findViewById(R.id.tvErrorResultMAHAds) as TextView


            view.findViewById(R.id.mah_ads_dlg_programs_btn_close).setOnClickListener(this)
            view.findViewById(R.id.btnErrorRefreshMAHAds).setOnClickListener(this)

            val ivBtnCancel = view.findViewById(R.id.mah_ads_dlg_programs_btnCancel) as ImageView
            val ivBtnInfo = view.findViewById(R.id.mah_ads_dlg_programs_btnInfo) as ImageView

            ivBtnCancel.setOnClickListener(this)
            ivBtnInfo.setOnClickListener(this)
            ivBtnCancel.setColorFilter(ContextCompat.getColor(context, R.color.mah_ads_title_bar_text_color))
            ivBtnInfo.setColorFilter(ContextCompat.getColor(context, R.color.mah_ads_title_bar_text_color))

            if (btnInfoVisibility) {
                ivBtnInfo.visibility = View.VISIBLE
            } else {
                ivBtnInfo.visibility = View.INVISIBLE
            }

            ivLoading = view.findViewById(R.id.ivLoadingMahAds) as ImageView
            //ivLoading.setColorFilter(ContextCompat.getColor(context, R.color.mah_ads_all_and_btn_text_color))
            ivLoading?.setImageResource(R.drawable.ic_loading_mah)
            ivLoading?.getDrawable()?.setColorFilter(ContextCompat.getColor(context, R.color.mah_ads_all_and_btn_text_color), PorterDuff.Mode.MULTIPLY );

            lstProgram?.visibility = View.GONE
            lytErrorF1?.visibility = View.GONE
            ivLoading?.visibility = View.GONE


            startLoading()
            setUI(mahRequestResult, true)

            if (savedInstanceState == null) {
                //Call to update data from service or local
                Updater.updateProgramList(activityMAH, urls!!)
            }

            TextViewFontSetter.setFontTextView(view.findViewById(R.id.tvTitle) as TextView, fontName)
            TextViewFontSetter.setFontTextView(tvErrorResultF1!!, fontName)
            TextViewFontSetter.setFontTextView(view.findViewById(R.id.btnErrorRefreshMAHAds) as TextView, fontName)
            return view
        } catch (e: MAHFragmentExeption) {
            Log.d(Constants.LOG_TAG_MAH_ADS, e.message, e)
            return null
        }

    }

    fun setUI(result: MAHRequestResult?, firstTime: Boolean) {
        Log.i(Constants.LOG_TAG_MAH_ADS, "------Result State is " + result?.resultState)


        if (result != null && (result.resultState === MAHRequestResult.ResultState.SUCCESS || result.resultState === MAHRequestResult.ResultState.ERR_SOME_ITEMS_HAS_JSON_SYNTAX_ERROR)) {
            dataHasAlreadySet = true
            val programsExceptMyself = result.programsFiltered
            items = LinkedList<Any>()
            for (c in programsExceptMyself!!) {
                items?.add(c)
            }
            val adapterInit = ProgramItmAdptPrograms(context, items!!, urls!!.urlRootOnServer!!, fontName!!)

            lstProgram!!.post {
                Log.i(Constants.LOG_TAG_MAH_ADS, "lstProgram post called")
                lstProgram!!.adapter = adapterInit
                lytErrorF1!!.visibility = View.GONE
                lstProgram!!.visibility = View.VISIBLE
            }
        } else {
            if (result == null || result.isReadFromWeb) {
                lstProgram!!.post {
                    lytErrorF1!!.visibility = View.VISIBLE
                    lstProgram!!.visibility = View.GONE
                    tvErrorResultF1!!.text = resources.getString(
                            R.string.mah_ads_internet_update_error)
                }
            } else {
                if (!firstTime) {
                    lstProgram!!.post {
                        lytErrorF1!!.visibility = View.VISIBLE
                        lstProgram!!.visibility = View.GONE
                        tvErrorResultF1!!.text = resources.getString(
                                R.string.mah_ads_internet_update_error)
                    }
                }
            }
        }

        stopLoading()
    }

    fun startLoading() {
        if (dataHasAlreadySet) {
            return
        }
        val animationLoading = RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f)

        animationLoading.duration = 350
        animationLoading.interpolator = LinearInterpolator()
        animationLoading.repeatCount = Animation.INFINITE

        ivLoading!!.startAnimation(animationLoading)
        ivLoading!!.visibility = View.VISIBLE
        lstProgram!!.visibility = View.GONE
        lytErrorF1!!.visibility = View.GONE

        Log.i(Constants.LOG_TAG_MAH_ADS, "Animation started")
    }

    fun stopLoading() {
        ivLoading!!.visibility = View.GONE
        lstProgram!!.visibility = View.GONE
        lytErrorF1!!.visibility = View.GONE

        ivLoading!!.clearAnimation()
        Log.i(Constants.LOG_TAG_MAH_ADS, "Animation stopped")
    }

    fun onClose() {
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

    override fun onClick(v: View) {
        try {
            if (v.id == R.id.mah_ads_dlg_programs_btnCancel || v.id == R.id.mah_ads_dlg_programs_btn_close) {
                onClose()
            } else if (v.id == R.id.mah_ads_dlg_programs_btnInfo) {

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
            } else if (v.id == R.id.btnErrorRefreshMAHAds) {
                Updater.updateProgramList(activityMAH, urls!!)
            }
        } catch (e: MAHFragmentExeption) {
            Log.d(Constants.LOG_TAG_MAH_ADS, e.message, e)
            return
        }

    }

    companion object {

        fun newInstance(
                mahRequestResult: MAHRequestResult,
                urls: Urls,
                fontName: String, btnInfoVisibility: Boolean,
                btnInfoWithMenu: Boolean,
                btnInfoMenuItemTitle: String,
                btnInfoActionURL: String): MAHAdsDlgPrograms {
            val dialog = MAHAdsDlgPrograms()
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