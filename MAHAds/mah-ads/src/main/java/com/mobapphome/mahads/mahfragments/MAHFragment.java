package com.mobapphome.mahads.mahfragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;


public class MAHFragment extends Fragment {


    public FragmentActivity getActivityMAH() throws MAHFragmentExeption {
        if (getActivity() != null) {
            return getActivity();
        } else {
            throw new MAHFragmentExeption("MAHFragment getActivity() method returns null");
        }
    }

}
