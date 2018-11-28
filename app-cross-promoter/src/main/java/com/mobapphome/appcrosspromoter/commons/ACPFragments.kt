package com.mobapphome.appcrosspromoter.commons

import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

/**
 * Created by settar on 12/20/16.
 */


open class MAHDialogFragment : DialogFragment() {

    val activityMAH: FragmentActivity
        @Throws(MAHFragmentExeption::class)
        get() = if (activity != null) {
            activity!!
        } else {
            throw MAHFragmentExeption("MAHFragment getActivity() method returns null")
        }
}

open class MAHFragment : Fragment() {

    val activityMAH: FragmentActivity
        @Throws(MAHFragmentExeption::class)
        get() = if (activity != null) {
            activity!!
        } else {
            throw MAHFragmentExeption("MAHFragment getActivity() method returns null")
        }
}


class MAHFragmentExeption : Exception {
    constructor() : super() {}

    constructor(message: String) : super(message) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}

    constructor(cause: Throwable) : super(cause) {}
}
