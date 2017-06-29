package com.mobapphome.mahads.mahfragments

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity


class MAHFragment : Fragment() {


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
