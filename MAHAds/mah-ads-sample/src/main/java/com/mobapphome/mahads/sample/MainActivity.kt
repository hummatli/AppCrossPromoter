package com.mobapphome.mahads.sample

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

import com.mobapphome.mahads.MAHAdsDlgExit
import com.mobapphome.mahads.tools.Constants
import com.mobapphome.mahads.MAHAdsController
import com.mobapphome.mahads.tools.MAHAdsDlgExitListener

import java.util.ArrayList
import java.util.Arrays


class MainActivity : AppCompatActivity(), MAHAdsDlgExitListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    var mahAdsController: MAHAdsController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val imageView = findViewById(R.id.ivMAHForkMeOnGithub) as ImageView
        val forkMeImg = resources.getDrawable(R.drawable.forkme_green)
        // setting the opacity (alpha)
        forkMeImg.alpha = 180
        // setting the images on the ImageViews
        imageView.setImageDrawable(forkMeImg)

        imageView.setOnClickListener(this)
        findViewById(R.id.mahBtnProgramsDlgTest).setOnClickListener(this)
        findViewById(R.id.mahBtnExitDlgTest).setOnClickListener(this)

        (findViewById(R.id.tvMAHAdsLibGithubUrl) as TextView).movementMethod = LinkMovementMethod.getInstance()
        (findViewById(R.id.tvMAHAdsLibJCenterURL) as TextView).movementMethod = LinkMovementMethod.getInstance()
        (findViewById(R.id.tvMAHAdsLibContrubute) as TextView).movementMethod = LinkMovementMethod.getInstance()

        val langsArray = arrayOf("Azerbaijani", "English", "French", "German", "Hindi", "Italian", "Portuguese", "Russian", "Spanish", "Turkish")

        val langSpinner = findViewById(R.id.langSpinner) as Spinner
        val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                ArrayList<CharSequence>(Arrays.asList(*langsArray)))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        langSpinner.adapter = adapter

        //Setting local.
        LocaleHelper.onCreate(this, "en")
        var currentLang = LocaleHelper.getLanguage(this)
        if (currentLang == "az") {
            currentLang = "azerbaijani"
        } else if (currentLang == "en") {
            currentLang = "english"
        } else if (currentLang == "fr") {
            currentLang = "french"
        } else if (currentLang == "de") {
            currentLang = "german"
        } else if (currentLang == "hi") {
            currentLang = "hindi"
        } else if (currentLang == "it") {
            currentLang = "italian"
        } else if (currentLang == "pt") {
            currentLang = "portuguese"
        } else if (currentLang == "ru") {
            currentLang = "russian"
        } else if (currentLang == "es") {
            currentLang = "spanish"
        } else if (currentLang == "tr") {
            currentLang = "turkey"
        }


        //Setting spinner to right language
        for (i in langsArray.indices) {
            if (langsArray[i].toLowerCase().startsWith(currentLang)) {
                langSpinner.setSelection(i)
            }

        }
        langSpinner.onItemSelectedListener = this

        // For MAHAds init
        // METHOD 1
        mahAdsController = MAHAdsController.instance!!
        mahAdsController!!.init(this, savedInstanceState!!, "https://project-943403214286171762.firebaseapp.com/mah_ads_dir/",
                "github_apps_prg_version.json", "github_apps_prg_list.json")
        // METHOD 1
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mahAdsController!!.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_settings) {
            return true
        } else if (id == R.id.action_mahads) {
            // For MAHAds programs dialog
            //METHOD 3
            mahAdsController!!.callProgramsDialog(this)
            //METHOD 3
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    //Click method for buttons
    override fun onClick(view: View) {
        if (view.id == R.id.mahBtnProgramsDlgTest) {
            // For MAHAds programs dialog
            //METHOD 3
            mahAdsController!!.callProgramsDialog(this)
            //METHOD 3
        } else if (view.id == R.id.mahBtnExitDlgTest) {
            // For MAHAds exit
            //METHOD 2
            mahAdsController!!.callExitDialog(this)
            //METHOD 2
        } else if (view.id == R.id.ivMAHForkMeOnGithub) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MAH_ADS_GITHUB_LINK))
            startActivity(browserIntent)
        }
    }


    //Yes call back from Exit dialog for yes button
    override fun onYes() {

    }

    //Yes call back from Exit dialog for no button
    override fun onNo() {

    }

    //Yes call back from Exit dialog for onExitWithoutExitDlg
    override fun onExitWithoutExitDlg() {
        //Don't call here onBackPresses(). It will go to loop
        finish()
    }

    override fun onEventHappened(eventStr: String) {
        Log.i(LOG_TAG_MAH_ADS_SAMPLE, eventStr)
    }

    //Selection event for language spinner
    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        val item = parent.getItemAtPosition(pos).toString()

        // Showing selected spinner item
        Toast.makeText(parent.context, "Selected: $item id:$id", Toast.LENGTH_LONG).show()
        if (item.toLowerCase().startsWith("azerbaijani")) {
            LocaleHelper.setLocale(this, "az")
        } else if (item.toLowerCase().startsWith("english")) {
            LocaleHelper.setLocale(this, "en")
        } else if (item.toLowerCase().startsWith("french")) {
            LocaleHelper.setLocale(this, "fr")
        } else if (item.toLowerCase().startsWith("german")) {
            LocaleHelper.setLocale(this, "de")
        } else if (item.toLowerCase().startsWith("hindi")) {
            LocaleHelper.setLocale(this, "hi")
        } else if (item.toLowerCase().startsWith("italian")) {
            LocaleHelper.setLocale(this, "it")
        } else if (item.toLowerCase().startsWith("portuguese")) {
            LocaleHelper.setLocale(this, "pt")
        } else if (item.toLowerCase().startsWith("russian")) {
            LocaleHelper.setLocale(this, "ru")
        } else if (item.toLowerCase().startsWith("spanish")) {
            LocaleHelper.setLocale(this, "es")
        } else if (item.toLowerCase().startsWith("turkish")) {
            LocaleHelper.setLocale(this, "tr")
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    override fun onBackPressed() {
        // For MAHAds exit
        //METHOD 2
        mahAdsController!!.callExitDialog(this)
        //METHOD 2
    }

    companion object {

        val LOG_TAG_MAH_ADS_SAMPLE = "mah_ads_sample_log"
    }
}
