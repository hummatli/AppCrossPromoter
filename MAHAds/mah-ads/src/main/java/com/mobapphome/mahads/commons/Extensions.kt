package com.mobapphome.mahads.commons

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.mobapphome.mahads.R

/**
 * Created by settar on 6/23/17.
 */


fun TextView.decorateAsLink() {
    this.movementMethod = LinkMovementMethod.getInstance()
}


fun TextView.setFontTextView(fontName: String?) =
        if (fontName != null) {
            try {
                val font = Typeface.createFromAsset(context.assets, fontName)
                typeface = font
            } catch (r: RuntimeException) {
                Log.e("test", "Error " + r.message)
            }
        } else {
        }


fun ViewGroup.inflate(layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.isVisible(): Boolean = visibility == View.VISIBLE
fun View.isGone(): Boolean = visibility == View.GONE
fun View.isInVisible(): Boolean = visibility == View.INVISIBLE


fun ImageView.setColorFilterCompat(color: Int, mode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN) {
    setColorFilter(ContextCompat.getColor(context, color), mode)
}

fun Context.getDrawableWithColorFilter(id: Int, color: Int, mode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN): Drawable {
    val drawable = ContextCompat.getDrawable(this, id)
    drawable.setColorFilter(ContextCompat.getColor(this, color), mode)
    return drawable
}

fun View.startAnimationFillAfter(id: Int, fillAfter: Boolean = true){
    val animRotate = AnimationUtils.loadAnimation(context, id)
    animRotate.fillAfter = fillAfter //For the textview to remain at the same place after the rotation
    animation = animRotate
    startAnimation(animRotate)
}