package com.mobapphome.mahads.tools

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.preference.PreferenceManager
import android.util.Log

import java.util.Locale

object LocaleUpdater {
    @JvmStatic
    fun updateLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources

        val configuration = resources.configuration
        configuration.locale = locale

        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}