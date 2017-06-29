package com.mobapphome.mahads.mahfragments

import android.graphics.Typeface
import android.util.Log
import android.widget.TextView

/**
 * Created by settar on 2/18/17.
 */

object TextViewFontSetter {

    fun setFontTextView(tv: TextView, fontName: String?) {
        if (fontName == null) {
            return
        }
        try {
            val font = Typeface.createFromAsset(tv.context.assets, fontName)
            tv.typeface = font
        } catch (r: RuntimeException) {
            Log.e("test", "Error " + r.message)
        }

    }

}
