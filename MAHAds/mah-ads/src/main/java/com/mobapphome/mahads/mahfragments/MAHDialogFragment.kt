package com.mobapphome.mahads.mahfragments

import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity

import com.mobapphome.mahads.mahfragments.MAHFragmentExeption

/**
 * Created by settar on 12/20/16.
 */


open class MAHDialogFragment : DialogFragment() {

    val activityMAH: FragmentActivity
        @Throws(MAHFragmentExeption::class)
        get() {
            if (activity != null) {
                return activity
            } else {
                throw MAHFragmentExeption("MAHFragment getActivity() method returns null")
            }
        }
}