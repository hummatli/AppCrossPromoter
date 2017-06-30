package com.mobapphome.mahads

import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import com.bumptech.glide.Glide
import com.mobapphome.mahads.tools.*
import com.mobapphome.mahandroidupdater.commons.inflate
import com.mobapphome.mahandroidupdater.commons.setFontTextView
import kotlinx.android.synthetic.main.program_item_programs.view.*

/**
 * Created by settar on 6/30/17.
 */

class ProgramItmAdptNew(val items: List<Any>,
                        val urlRootOnServer: String?,
                        val fontName: String?,
                        val listenerOnClick: (Any) -> Unit,
                        val listenerOnMoreClick: (Any, View) -> Unit)
    : RecyclerView.Adapter<ProgramItmAdptNew.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder = ViewHolder(parent?.inflate(R.layout.program_item_programs)!!)

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) = holder?.bind(items[position], listenerOnClick, listenerOnMoreClick)!!

    override fun getItemCount(): Int = items.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Any, listenerOnClick: (Any) -> Unit, listenerOnMoreClick: (Any, View) -> Unit) = with(itemView) {
            if (item is Program) {
                val pckgName = item.uri.trim { it <= ' ' }

                // For hole view for click----------------------------------

//                vi.setOnClickListener {
//                    if (checkPackageIfExists(vi.context, pckgName)) {
//                        val pack = vi.context.packageManager
//                        val app = pack.getLaunchIntentForPackage(pckgName)
//                        app.putExtra(Constants.MAH_ADS_INTERNAL_CALLED, true)
//                        vi.context.startActivity(app)
//                    } else {
//                        if (!pckgName.isEmpty()) {
//                            showMarket(ProgramItmAdptPrograms.inflater!!.context, pckgName)
//                        }
//                    }
//                }


                tvProgramNewText.visibility = View.GONE
                val freshnestStr = item.getFreshnestStr(context)
                if (freshnestStr != null) {
                    tvProgramNewText.setTextSize(TypedValue.COMPLEX_UNIT_SP, item.getFreshnestStrTextSizeInSP(context).toFloat())
                    tvProgramNewText.text = freshnestStr
                    val animRotate = AnimationUtils.loadAnimation(context, R.anim.tv_rotate) as RotateAnimation
                    animRotate.fillAfter = true //For the textview to remain at the same place after the rotation
                    tvProgramNewText.startAnimation(animRotate)
                    tvProgramNewText.visibility = View.VISIBLE
                } else {
                    tvProgramNewText.visibility = View.GONE
                }



                if (checkPackageIfExists(context, pckgName)) {
                    tvOpenInstall.text = context.resources.getString(R.string.cmnd_verb_mah_ads_open_program)
                } else {
                    tvOpenInstall.text = context.resources.getString(R.string.cmnd_verb_mah_ads_install_program)
                }

                // Setting all values in listview
                tvProgramName.text = item.name
                tvProgramDesc.text = item.desc

                Log.i(Constants.LOG_TAG_MAH_ADS, getUrlOfImage(urlRootOnServer!!, item.img))

                val imgNotFoundDrawable = ContextCompat.getDrawable(context, R.drawable.img_not_found)
                imgNotFoundDrawable.setColorFilter(ContextCompat.getColor(context, R.color.mah_ads_no_image_color), PorterDuff.Mode.SRC_IN)

                Glide.with(context)
                        .load(getUrlOfImage(urlRootOnServer, item.img))
                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .placeholder(R.drawable.img_place_holder_normal)
                        .crossFade()
                        .error(imgNotFoundDrawable)
                        .into(ivProgramImg)


                imgBtnMore.setColorFilter(ContextCompat.getColor(context, R.color.mah_ads_all_and_btn_text_color))

                imgBtnMore.setImageResource(R.drawable.ic_more_vert_grey600_24dp)

                imgBtnMore.setOnClickListener { v ->
                    val popup = PopupMenu(context, v)
                    // Inflating the Popup using xml file
                    popup.menuInflater.inflate(R.menu.program_popup_menu, popup.menu)
                    // registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener { item ->
                        if (item.itemId == R.id.popupMenuOpenOnGoogleP) {
                            if (!pckgName.isEmpty()) {
                                showMarket(context, pckgName)
                            }
                        }
                        true
                    }

                    popup.show()// showing popup menu
                }

                tvProgramNewText.setFontTextView(fontName)
                tvProgramName.setFontTextView(fontName)
                tvProgramDesc.setFontTextView(fontName)
                tvOpenInstall.setFontTextView(fontName)
            }

            setOnClickListener { listenerOnClick(item) }
            imgBtnMore.setOnClickListener { v -> listenerOnMoreClick(item, v) }
        }
    }
}