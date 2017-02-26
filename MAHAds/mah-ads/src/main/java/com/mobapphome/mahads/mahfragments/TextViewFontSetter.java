package com.mobapphome.mahads.mahfragments;

import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by settar on 2/18/17.
 */

public class TextViewFontSetter {

    public static void setFontTextView(TextView tv, String fontName) {
        if(fontName == null){
            return;
        }
        try{
            Typeface font = Typeface.createFromAsset(tv.getContext().getAssets(),fontName);
            tv.setTypeface(font);
        }catch(RuntimeException r){
            Log.e("test", "Error " + r.getMessage());
        }
    }

}
