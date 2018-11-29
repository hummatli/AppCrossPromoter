package com.mobapphome.appcrosspromoter.sample;

/**
 * Created by settar on 6/30/17.
 */


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobapphome.appcrosspromoter.ACPController;
import com.mobapphome.appcrosspromoter.ACPDlgExit;
import com.mobapphome.appcrosspromoter.tools.Constants;

import java.util.ArrayList;
import java.util.Arrays;


public class SampleActivityJava extends AppCompatActivity implements ACPDlgExit.ACPDlgExitListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    final public static String LOG_TAG_MAH_ADS_SAMPLE = "mah_ads_sample_log";
    ACPController ACPController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This block is only in Java sample---------------
        {
            findViewById(R.id.mahBtnOpenJavaSample).setVisibility(View.GONE);
            setTitle(getString(R.string.title_java_sample));
        }

        ImageView imageView = (ImageView) findViewById(R.id.ivForkMeOnGithub);
        Drawable forkMeImg = getResources().getDrawable(R.drawable.forkme_green);
        // setting the opacity (alpha)
        forkMeImg.setAlpha(180);
        // setting the images on the ImageViews
        imageView.setImageDrawable(forkMeImg);

        imageView.setOnClickListener(this);
        findViewById(R.id.btnProgramsDlgTest).setOnClickListener(this);
        findViewById(R.id.btnExitDlgTest).setOnClickListener(this);

        ((TextView) findViewById(R.id.tvMAHAdsLibGithubUrl)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) findViewById(R.id.tvMAHAdsLibJCenterURL)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) findViewById(R.id.tvMAHAdsLibContrubute)).setMovementMethod(LinkMovementMethod.getInstance());

        String[] langsArray = new String[]{
                "Azerbaijani",
                "English",
                "French",
                "German",
                "Hindi",
                "Italian",
                "Portuguese",
                "Russian",
                "Spanish",
                "Turkish"};

        Spinner langSpinner = (Spinner) findViewById(R.id.langSpinner);
        ArrayAdapter adapter = new ArrayAdapter<CharSequence>(
                this,
                android.R.layout.simple_spinner_item,
                new ArrayList<CharSequence>(Arrays.asList(langsArray)));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langSpinner.setAdapter(adapter);

        //Setting local.
        LocaleHelper.onCreate(this, "en");
        String currentLang = LocaleHelper.getLanguage(this);
        if (currentLang.equals("az")) {
            currentLang = "azerbaijani";
        } else if (currentLang.equals("en")) {
            currentLang = "english";
        } else if (currentLang.equals("fr")) {
            currentLang = "french";
        } else if (currentLang.equals("de")) {
            currentLang = "german";
        } else if (currentLang.equals("hi")) {
            currentLang = "hindi";
        } else if (currentLang.equals("it")) {
            currentLang = "italian";
        } else if (currentLang.equals("pt")) {
            currentLang = "portuguese";
        } else if (currentLang.equals("ru")) {
            currentLang = "russian";
        } else if (currentLang.equals("es")) {
            currentLang = "spanish";
        } else if (currentLang.equals("tr")) {
            currentLang = "turkey";
        }


        //Setting spinner to right language
        for (int i = 0; i < langsArray.length; i++) {
            if (langsArray[i].toLowerCase().startsWith(currentLang)) {
                langSpinner.setSelection(i);
            }

        }
        langSpinner.setOnItemSelectedListener(this);

        // For MAHAds init
        // METHOD 1
        ACPController = ACPController.getInstance();
        ACPController.init(this, savedInstanceState, "https://project-943403214286171762.firebaseapp.com/mah_ads_dir/",
                "github_apps_prg_version.json", "github_apps_prg_list.json");
        // METHOD 1
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ACPController.onSaveInstanceState(outState);
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
            ACPController.callProgramsDialog(this);
            //METHOD 3
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Click method for buttons
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnProgramsDlgTest) {
            // For MAHAds programs dialog
            //METHOD 3
            ACPController.callProgramsDialog(this);
            //METHOD 3
        } else if (view.getId() == R.id.btnExitDlgTest) {
            // For MAHAds exit
            //METHOD 2
            ACPController.callExitDialog(this);
            //METHOD 2
        } else if (view.getId() == R.id.ivForkMeOnGithub) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MAH_ADS_GITHUB_LINK));
            startActivity(browserIntent);
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
        if (item.toLowerCase().startsWith("azerbaijani")) {
            LocaleHelper.setLocale(this, "az");
        } else if (item.toLowerCase().startsWith("english")) {
            LocaleHelper.setLocale(this, "en");
        } else if (item.toLowerCase().startsWith("french")) {
            LocaleHelper.setLocale(this, "fr");
        } else if (item.toLowerCase().startsWith("german")) {
            LocaleHelper.setLocale(this, "de");
        } else if (item.toLowerCase().startsWith("hindi")) {
            LocaleHelper.setLocale(this, "hi");
        } else if (item.toLowerCase().startsWith("italian")) {
            LocaleHelper.setLocale(this, "it");
        } else if (item.toLowerCase().startsWith("portuguese")) {
            LocaleHelper.setLocale(this, "pt");
        } else if (item.toLowerCase().startsWith("russian")) {
            LocaleHelper.setLocale(this, "ru");
        } else if (item.toLowerCase().startsWith("spanish")) {
            LocaleHelper.setLocale(this, "es");
        } else if (item.toLowerCase().startsWith("turkish")) {
            LocaleHelper.setLocale(this, "tr");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onBackPressed() {
        // For MAHAds exit
        //METHOD 2
        ACPController.callExitDialog(this);
        //METHOD 2
    }
}