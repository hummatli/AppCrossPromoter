package com.mobapphome.mahandroidupdater.commons

import android.graphics.Typeface
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by settar on 6/23/17.
 */


fun TextView.decorateAsLink() {
    this.movementMethod = LinkMovementMethod.getInstance()
}


fun TextView.setFontTextView(fontName: String?) {
    if (fontName == null) {
        return
    }
    try {
        val font = Typeface.createFromAsset(context.assets, fontName)
        typeface = font
    } catch (r: RuntimeException) {
        Log.e("test", "Error " + r.message)
    }

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
