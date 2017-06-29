package com.mobapphome.mahads.tools

import android.content.Context
import android.util.Log

import com.mobapphome.mahads.R

import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

enum class Freshnest {
    UPDATED, NEW, OLD
}

data class Program(
        var id: Int = 0,
        var name: String? = null,
        var desc: String,
        var uri: String,
        var img: String,
        var release_date: String,
        var update_date: String
) : Serializable {

    var ONE_MONTTH_MILLI_SEC = 1000L * 60 * 60 * 24 * 30



    val freshnest: Freshnest
        get() {
            if (checkForFreshnest(release_date)) {
                return Freshnest.NEW
            } else if (checkForFreshnest(update_date)) {
                return Freshnest.UPDATED
            } else {
                return Freshnest.OLD
            }
        }

    fun getFreshnestStr(context: Context): String? =
            when (freshnest) {
                Freshnest.NEW -> context.getString(R.string.adjective_mah_ads_new_text)
                Freshnest.UPDATED -> context.getString(R.string.mah_ads_updated_text)
                Freshnest.OLD -> null
            }


    fun getFreshnestStrTextSizeInSP(context: Context): Int =
            when (freshnest) {
                Freshnest.NEW -> 12
                Freshnest.UPDATED -> 10
                Freshnest.OLD -> 10
            }

    private fun checkForFreshnest(dateStr: String): Boolean { //Not later than 1 month
        var isFresh = false

        try {
            val dateAsDate = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(dateStr)
            if (dateAsDate != null) {
                val dateToday = Date().time
                val dateChecked = dateAsDate.time
                val diff = dateToday - dateChecked
                if (diff <= ONE_MONTTH_MILLI_SEC) {
                    isFresh = true
                    Log.i("Test", "Program new = $name date = $dateAsDate")
                }
            }
        } catch (e: java.text.ParseException) {
            Log.i("Test", "Paresing program date Exception: " + e.message)
        }

        return isFresh
    }
}
