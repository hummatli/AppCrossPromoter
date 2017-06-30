package com.mobapphome.mahads

import android.content.Context
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v7.widget.PopupMenu
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mobapphome.mahads.tools.*
import com.mobapphome.mahandroidupdater.commons.setFontTextView

internal class ProgramItmAdptPrograms(context: Context, private val items: List<Any>, var urlRootOnServer: String?,
                                      var fontName: String?) : BaseAdapter() {

    private val TAG = ProgramItmAdptPrograms::class.java.name


    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val obj = items[position]
        if (obj is Program) {
            val currProgram = obj
            val pckgName = currProgram.uri.trim { it <= ' ' }

            // For hole view for click----------------------------------
            val vi = inflater!!.inflate(R.layout.program_item_programs, null)
            vi.setOnClickListener {
                if (checkPackageIfExists(vi.context, pckgName)) {
                    val pack = vi.context.packageManager
                    val app = pack.getLaunchIntentForPackage(pckgName)
                    app.putExtra(Constants.MAH_ADS_INTERNAL_CALLED, true)
                    vi.context.startActivity(app)
                } else {
                    if (!pckgName.isEmpty()) {
                        showMarket(inflater!!.context, pckgName)
                    }
                }
            }



            val tvProgramNewText = vi.findViewById(R.id.tvProgramNewText) as TextView
            tvProgramNewText.visibility = View.GONE
            val freshnestStr = currProgram.getFreshnestStr(inflater!!.context)
            if (freshnestStr != null) {
                tvProgramNewText.setTextSize(TypedValue.COMPLEX_UNIT_SP, currProgram.getFreshnestStrTextSizeInSP(inflater!!.context).toFloat())
                tvProgramNewText.text = freshnestStr
                val animRotate = AnimationUtils.loadAnimation(inflater!!.context, R.anim.tv_rotate) as RotateAnimation
                animRotate.fillAfter = true //For the textview to remain at the same place after the rotation
                tvProgramNewText.startAnimation(animRotate)
                tvProgramNewText.visibility = View.VISIBLE
            } else {
                tvProgramNewText.visibility = View.GONE
            }


            val nameTV = vi.findViewById(R.id.tvProgramName) as TextView
            val descTV = vi.findViewById(R.id.tvProgramDesc) as TextView
            val ivImg = vi.findViewById(R.id.ivProgramImg) as ImageView
            val tvOpenGooglePLay = vi.findViewById(R.id.tvOpenInstall) as TextView

            if (checkPackageIfExists(vi.context, pckgName)) {
                tvOpenGooglePLay.text = vi.context.resources.getString(R.string.cmnd_verb_mah_ads_open_program)
            } else {
                tvOpenGooglePLay.text = vi.context.resources.getString(R.string.cmnd_verb_mah_ads_install_program)
            }
            // Setting all values in listview
            nameTV.text = currProgram.name
            descTV.text = currProgram.desc

            Log.i(Constants.LOG_TAG_MAH_ADS, getUrlOfImage(urlRootOnServer!!, currProgram.img))

            val imgNotFoundDrawable = ContextCompat.getDrawable(inflater!!.context, R.drawable.img_not_found)
            imgNotFoundDrawable.setColorFilter(ContextCompat.getColor(inflater!!.context, R.color.mah_ads_no_image_color), PorterDuff.Mode.SRC_IN)

            Glide.with(vi.context)
                    .load(getUrlOfImage(urlRootOnServer!!, currProgram.img))
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.img_place_holder_normal)
                    .crossFade()
                    .error(imgNotFoundDrawable)
                    .into(ivImg)


            val ivMore = vi.findViewById(R.id.imgBtnMore) as ImageButton
            ivMore.setColorFilter(ContextCompat.getColor(inflater!!.context, R.color.mah_ads_all_and_btn_text_color))

            ivMore.setImageResource(R.drawable.ic_more_vert_grey600_24dp)

            ivMore.setOnClickListener { v ->
                val popup = PopupMenu(vi.context, v)
                // Inflating the Popup using xml file
                popup.menuInflater.inflate(R.menu.program_popup_menu, popup.menu)
                // registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener { item ->
                    if (item.itemId == R.id.popupMenuOpenOnGoogleP) {
                        if (!pckgName.isEmpty()) {
                            showMarket(inflater!!.context, pckgName)
                        }
                    }
                    true
                }

                popup.show()// showing popup menu
            }



            (vi.findViewById(R.id.tvProgramNewText) as TextView).setFontTextView(fontName)
            nameTV.setFontTextView(fontName)
            descTV.setFontTextView(fontName)
            tvOpenGooglePLay.setFontTextView(fontName)
            return vi
        } else {
            return null
        }

    }


    companion object {
        private var inflater: LayoutInflater? = null
    }
}
