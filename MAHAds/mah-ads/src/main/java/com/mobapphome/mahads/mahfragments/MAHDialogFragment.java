package com.mobapphome.mahads.mahfragments;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import com.mobapphome.mahads.mahfragments.MAHFragmentExeption;

/**
 * Created by settar on 12/20/16.
 */


public class MAHDialogFragment extends DialogFragment {

    public FragmentActivity getActivityMAH() throws MAHFragmentExeption {
        if (getActivity() != null) {
            return getActivity();
        } else {
            throw new MAHFragmentExeption("MAHFragment getActivity() method returns null");
        }
    }
}