package com.mobapphome.appcrosspromoter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mobapphome.appcrosspromoter.commons.*
import com.mobapphome.appcrosspromoter.tools.Constants
import com.mobapphome.appcrosspromoter.tools.Program
import com.mobapphome.appcrosspromoter.tools.checkPackageIfExists
import com.mobapphome.appcrosspromoter.tools.getUrlOfImage
import kotlinx.android.synthetic.main.program_item_programs.view.*

/**
 * Created by settar on 6/30/17.
 */

class ProgramItmAdpt(val items: List<Any>,
                     val urlRootOnServer: String?,
                     val fontName: String?,
                     val listenerOnClick: (Any) -> Unit,
                     val listenerOnMoreClick: (Any, View) -> Unit)
    : RecyclerView.Adapter<ProgramItmAdpt.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.inflate(R.layout.program_item_programs)!!)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listenerOnClick, listenerOnMoreClick)!!

    override fun getItemCount(): Int = items.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Any,
                 listenerOnClick: (Any) -> Unit,
                 listenerOnMoreClick: (Any, View) -> Unit) = with(itemView) {
            if (item is Program) {
                val pckgName = item.uri.trim { it <= ' ' }

                tvProgramNewText.makeGone()
                val freshnestStr = item.getFreshnestStr(context)

                if (freshnestStr != null) {
                    tvProgramNewText.setTextSize(TypedValue.COMPLEX_UNIT_SP, item.getFreshnestStrTextSizeInSP(context).toFloat())
                    tvProgramNewText.text = freshnestStr

                    tvProgramNewText.startAnimationFillAfter(R.anim.tv_rotate, true)
                    tvProgramNewText.makeVisible()
                } else {
                    tvProgramNewText.makeGone()
                }

                if (checkPackageIfExists(context, pckgName)) {
                    tvOpenInstall.text = context.resources.getString(R.string.cmnd_verb_acp_open_program)
                } else {
                    tvOpenInstall.text = context.resources.getString(R.string.cmnd_verb_acp_install_program)
                }

                tvProgramName.text = item.name
                tvProgramDesc.text = item.desc

                Log.i(Constants.LOG_TAG_MAH_ADS, getUrlOfImage(urlRootOnServer!!, item.img))

                Glide.with(context)
                        .load(getUrlOfImage(urlRootOnServer, item.img))
                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .placeholder(R.drawable.img_place_holder_normal)
                        .crossFade()
                        .error(context.getDrawableWithColorFilter(R.drawable.img_not_found, R.color.mah_ads_no_image_color))
                        .into(ivProgramImg)

                imgBtnMore.setColorFilterCompat(R.color.mah_ads_all_and_btn_text_color)
                imgBtnMore.setImageResource(R.drawable.ic_more_vert_grey600_24dp)

                tvProgramNewText.setFontTextView(fontName)
                tvProgramName.setFontTextView(fontName)
                tvProgramDesc.setFontTextView(fontName)
                tvOpenInstall.setFontTextView(fontName)

                setOnClickListener { listenerOnClick(item) }
                imgBtnMore.setOnClickListener { v -> listenerOnMoreClick(item, v) }

            }
        }
    }
}