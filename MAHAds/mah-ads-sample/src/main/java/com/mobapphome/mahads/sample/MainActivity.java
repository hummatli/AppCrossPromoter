package com.mobapphome.mahads.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.mobapphome.mahads.tools.LocaleUpdater;
import com.mobapphome.mahads.tools.MAHAdsController;
import com.mobapphome.mahads.tools.MAHAdsExitListener;

public class MainActivity extends AppCompatActivity implements MAHAdsExitListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleUpdater.setLocale(this,"tr");
        setContentView(R.layout.activity_main);
        // For MAHAds init
        MAHAdsController.init(this,"http://ubuntu1mah.cloudapp.net/mah_ads_dir/");
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
            // For MAHAds programs dialog
            MAHAdsController.callProgramsDialog(this);
            //METHOD 3
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        // For MAHAds exit
        MAHAdsController.callExitDialog(this);
        //METHOD 2
    }

    @Override
    public void onYes() {

    }

    @Override
    public void onNo() {

    }

    @Override
    public void onExitWithoutExitDlg() {
        //Don't call here onBackPresses(). It will go to loop
        finish();
    }
}
