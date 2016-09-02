package com.mobapphome.mahads.sample;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.mobapphome.mahads.tools.MAHAdsController;
import com.mobapphome.mahads.tools.MAHAdsExitListener;

public class MainActivity extends AppCompatActivity implements MAHAdsExitListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.mahBtnProgramsDlgTest).setOnClickListener(this);
        findViewById(R.id.mahBtnExitDlgTest).setOnClickListener(this);

        Spinner langSpinner = (Spinner) findViewById(R.id.langSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.langs_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langSpinner.setAdapter(adapter);


        LocaleHelper.onCreate(this, "en");
        String currentLang = LocaleHelper.getLanguage(this);
        if (currentLang.equals("en")) {
            currentLang = "english";
        } else if (currentLang.equals("az")) {
            currentLang = "azerbaijan";
        } else if (currentLang.equals("ru")) {
            currentLang = "russia";
        } else if (currentLang.equals("tr")) {
            currentLang = "turkey";
        }

        String[] langsArray = getResources().getStringArray(R.array.langs_array);
        for (int i = 0; i < langsArray.length; i++) {
            if (langsArray[i].toLowerCase().startsWith(currentLang)) {
                langSpinner.setSelection(i);
            }

        }
        langSpinner.setOnItemSelectedListener(this);

        // For MAHAds init
        MAHAdsController.init(this, "http://ubuntu1mah.cloudapp.net/mah_ads_dir/");
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.mahBtnProgramsDlgTest) {
            // For MAHAds programs dialog
            MAHAdsController.callProgramsDialog(this);
            //METHOD 3
        } else if (view.getId() == R.id.mahBtnExitDlgTest) {
            // For MAHAds exit
            MAHAdsController.callExitDialog(this);
            //METHOD 2
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String item = parent.getItemAtPosition(pos).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item + " id:" + id, Toast.LENGTH_LONG).show();
        if (item.toLowerCase().startsWith("english")) {
            LocaleHelper.setLocale(this, "en");
        } else if (item.toLowerCase().startsWith("azerbaijan")) {
            LocaleHelper.setLocale(this, "az");
        } else if (item.toLowerCase().startsWith("russia")) {
            LocaleHelper.setLocale(this, "ru");
        } else if (item.toLowerCase().startsWith("turkey")) {
            LocaleHelper.setLocale(this, "tr");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void updateViews() {
        // if you want you just call activity to restart itself to redraw all the widgets with the correct locale
        // however, it will cause a bad look and feel for your users
        //
        // this.recreate();

        //or you can just update the visible text on your current layout
        Resources resources = getResources();

//        mTitleTextView.setText(resources.getString(R.string.title_text));
//        mDescTextView.setText(resources.getString(R.string.desc_text));
//        mAboutTextView.setText(resources.getString(R.string.about_text));
    }
}
