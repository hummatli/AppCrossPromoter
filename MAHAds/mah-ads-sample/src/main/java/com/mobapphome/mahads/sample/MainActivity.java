package com.mobapphome.mahads.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobapphome.mahads.MAHAdsDlgExit;
import com.mobapphome.mahads.tools.MAHAdsController;


public class MainActivity extends AppCompatActivity implements MAHAdsDlgExit.MAHAdsDlgExitListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    final public static String LOG_TAG_MAH_ADS_SAMPLE = "mah_ads_sample_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.mahBtnProgramsDlgTest).setOnClickListener(this);
        findViewById(R.id.mahBtnExitDlgTest).setOnClickListener(this);

        ((TextView)findViewById(R.id.tvMAHAdsLibGithubUrl)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)findViewById(R.id.tvMAHAdsLibJCenterURL)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)findViewById(R.id.tvMAHAdsLibContrubute)).setMovementMethod(LinkMovementMethod.getInstance());

        Spinner langSpinner = (Spinner) findViewById(R.id.langSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.langs_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langSpinner.setAdapter(adapter);

        //Setting local.
        LocaleHelper.onCreate(this, "en");
        String currentLang = LocaleHelper.getLanguage(this);
        if (currentLang.equals("en")) {
            currentLang = "english";
        } else if (currentLang.equals("az")) {
            currentLang = "azerbaijan";
        } else if (currentLang.equals("fr")) {
            currentLang = "france";
        } else if (currentLang.equals("ru")) {
            currentLang = "russia";
        } else if (currentLang.equals("tr")) {
            currentLang = "turkey";
        }

        //Setting spinner to right language
        String[] langsArray = getResources().getStringArray(R.array.langs_array);
        for (int i = 0; i < langsArray.length; i++) {
            if (langsArray[i].toLowerCase().startsWith(currentLang)) {
                langSpinner.setSelection(i);
            }

        }
        langSpinner.setOnItemSelectedListener(this);

        // For MAHAds init
        // METHOD 1
        MAHAdsController.init(this,"https://project-943403214286171762.firebaseapp.com/mah_ads_dir/mah_ads_github_apps/",
                "program_version.json", "program_list.json");
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
        } else if (id == R.id.action_mahads) {
            // For MAHAds programs dialog
            //METHOD 3
            MAHAdsController.callProgramsDialog(this);
            //METHOD 3
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Click method for buttons
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.mahBtnProgramsDlgTest) {
            // For MAHAds programs dialog
            //METHOD 3
            MAHAdsController.callProgramsDialog(this);
            //METHOD 3
        } else if (view.getId() == R.id.mahBtnExitDlgTest) {
            // For MAHAds exit
            //METHOD 2
            MAHAdsController.callExitDialog(this);
            //METHOD 2
        }
    }


    //Yes call back from Exit dialog for yes button
    @Override
    public void onYes() {

    }

    //Yes call back from Exit dialog for no button
    @Override
    public void onNo() {

    }

    //Yes call back from Exit dialog for onExitWithoutExitDlg
    @Override
    public void onExitWithoutExitDlg() {
        //Don't call here onBackPresses(). It will go to loop
        finish();
    }

    @Override
    public void onEventHappened(String eventStr) {
        Log.i(LOG_TAG_MAH_ADS_SAMPLE, eventStr);
    }

    //Selection event for language spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String item = parent.getItemAtPosition(pos).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item + " id:" + id, Toast.LENGTH_LONG).show();
        if (item.toLowerCase().startsWith("english")) {
            LocaleHelper.setLocale(this, "en");
        } else if (item.toLowerCase().startsWith("azerbaijan")) {
            LocaleHelper.setLocale(this, "az");
        } else if (item.toLowerCase().startsWith("france")) {
            LocaleHelper.setLocale(this, "fr");
        } else if (item.toLowerCase().startsWith("russia")) {
            LocaleHelper.setLocale(this, "ru");
        } else if (item.toLowerCase().startsWith("turkey")) {
            LocaleHelper.setLocale(this, "tr");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onBackPressed() {
        // For MAHAds exit
        //METHOD 2
        MAHAdsController.callExitDialog(this);
        //METHOD 2
    }
}
