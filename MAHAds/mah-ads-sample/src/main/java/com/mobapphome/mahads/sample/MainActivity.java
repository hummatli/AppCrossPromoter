package com.mobapphome.mahads.sample;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mobapphome.mahads.MAHAdsDlgExit;
import com.mobapphome.mahads.MAHAdsDlgPrograms;
import com.mobapphome.mahads.tools.ExitListiner;
import com.mobapphome.mahads.tools.MAHAdsController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // For MAHAds init
        MAHAdsController.init(this,"http://ubuntu1mah.cloudapp.net/mah_ads_dir/");
        MAHAdsController.setInternalCalled(getIntent().getBooleanExtra(MAHAdsController.MAH_ADS_INTERNAL_CALLED, false));
        // METHOD 1
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_mahads){
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction(); //get the fragment
            final MAHAdsDlgPrograms frag = MAHAdsDlgPrograms.newInstance(this);
            frag.show(ft, "AdsDialogFragment");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        if(MAHAdsController.isInternalCalled()){
            super.onBackPressed();
        }else{
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction(); //get the fragment
            final MAHAdsDlgExit frag = MAHAdsDlgExit.newInstance(this, new ExitListiner() {

                @Override
                public void onYes() {
                    finish();
                }

                @Override
                public void onNo() {
                    // TODO Auto-generated method stub
                }
            });
            frag.show(ft, "AdsDialogExit");
        }
        //super.onBackPressed();
    }
}
