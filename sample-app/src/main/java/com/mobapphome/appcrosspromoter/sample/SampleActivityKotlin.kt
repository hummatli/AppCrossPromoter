package com.mobapphome.appcrosspromoter.sample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.mobapphome.appcrosspromoter.ACPController
import com.mobapphome.appcrosspromoter.ACPDlgExit
import com.mobapphome.appcrosspromoter.commons.decorateAsLink
import com.mobapphome.appcrosspromoter.tools.Constants
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class SampleActivityKotlin : AppCompatActivity(), ACPDlgExit.MAHAdsDlgExitListener {
    var ACPController: ACPController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //This block is only in Kotlin sample
        run {
            title = getString(R.string.title_kotlin_sample)
            mahBtnOpenJavaSample.setOnClickListener {
                val intent = Intent(this, SampleActivityJava::class.java)
                startActivity(intent)
            }
        }

        val forkMeImg = ContextCompat.getDrawable(this, R.drawable.forkme_green)
        // setting the opacity (alpha)
        forkMeImg!!.alpha = 180
        ivForkMeOnGithub.setImageDrawable(forkMeImg)

        ivForkMeOnGithub.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MAH_ADS_GITHUB_LINK))
            startActivity(browserIntent)
        }
        btnProgramsDlgTest.setOnClickListener {
            // For MAHAds programs dialog
            //METHOD 3
            ACPController!!.callProgramsDialog(this)
            //METHOD 3
        }
        btnExitDlgTest.setOnClickListener {
            // For MAHAds exit
            //METHOD 2
            ACPController!!.callExitDialog(this)
            //METHOD 2
        }

        tvMAHAdsLibGithubUrl.decorateAsLink()
        tvMAHAdsLibJCenterURL.decorateAsLink()
        tvMAHAdsLibContrubute.decorateAsLink()

        val langsList = listOf("Azerbaijani", "English", "French", "German", "Hindi", "Italian", "Portuguese", "Russian", "Spanish", "Turkish")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ArrayList<CharSequence>(langsList))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        langSpinner.adapter = adapter

        //Setting local.
        LocaleHelper.onCreate(this, "en")
        var currentLang = when (LocaleHelper.getLanguage(this)) {
            "az" -> "azerbaijani"
            "en" -> "english"
            "fr" -> "french"
            "de" -> "german"
            "hi" -> "hindi"
            "it" -> "italian"
            "pt" -> "portuguese"
            "ru" -> "russian"
            "es" -> "spanish"
            "tr" -> "turkey"
            else -> "unknown"
        }

        //Setting spinner to right language
        for (i in langsList.indices) {
            if (langsList[i].toLowerCase().startsWith(currentLang)) {
                langSpinner.setSelection(i)
            }

        }
        langSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val item = parent.getItemAtPosition(pos).toString()

                // Showing selected spinner item
                Toast.makeText(parent.context, "Selected: $item id:$id", Toast.LENGTH_LONG).show()

                val itemLowerCase = item.toLowerCase()

                LocaleHelper.setLocale(baseContext, when {
                    itemLowerCase.startsWith("azerbaijani") -> "az"
                    itemLowerCase.startsWith("english") -> "en"
                    itemLowerCase.startsWith("french") -> "fr"
                    itemLowerCase.startsWith("german") -> "de"
                    itemLowerCase.startsWith("hindi") -> "hi"
                    itemLowerCase.startsWith("italian") -> "it"
                    itemLowerCase.startsWith("portuguese") -> "pt"
                    itemLowerCase.startsWith("russian") -> "ru"
                    itemLowerCase.startsWith("spanish") -> "es"
                    itemLowerCase.startsWith("turkish") -> "tr"
                    else -> "does not specfied"
                })
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        // For MAHAds init
        // METHOD 1
        ACPController = com.mobapphome.appcrosspromoter.ACPController.instance
        ACPController!!.init(this, savedInstanceState, "https://project-943403214286171762.firebaseapp.com/mah_ads_dir/",
                "github_apps_prg_version.json", "github_apps_prg_list.json")
        // METHOD 1
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        ACPController!!.onSaveInstanceState(outState)
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
            ACPController!!.callProgramsDialog(this)
            //METHOD 3
            return true
        }

        return super.onOptionsItemSelected(item)
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

    override fun onBackPressed() {
        // For MAHAds exit
        //METHOD 2
        ACPController!!.callExitDialog(this)
        //METHOD 2
    }

    companion object {
        val LOG_TAG_MAH_ADS_SAMPLE = "mah_ads_sample_log"
    }
}
