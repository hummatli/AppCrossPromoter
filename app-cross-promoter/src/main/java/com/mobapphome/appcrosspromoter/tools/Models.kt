package com.mobapphome.appcrosspromoter.tools

import android.content.Context
import android.util.Log
import com.mobapphome.appcrosspromoter.R
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by settar on 6/25/17.
 */

data class MAHRequestResult(
        val programsTotal: List<Program>,
        val resultState: ResultState,
        var programsFiltered: List<Program>? = null,
        var programsSelected: List<Program>? = null,
        var isReadFromWeb:Boolean = false) {

    enum class ResultState {
        SUCCESS, ERR_JSON_IS_NULL_OR_EMPTY, ERR_JSON_HAS_TOTAL_ERROR, ERR_SOME_ITEMS_HAS_JSON_SYNTAX_ERROR
    }
}


data class Urls(var urlForProgramVersion: String?, var urlForProgramList: String?, var urlRootOnServer: String?)


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

    enum class Freshnest {
        UPDATED, NEW, OLD
    }

    val freshnest: Freshnest
        get() = when {
            checkForFreshnest(release_date) -> Freshnest.NEW
            checkForFreshnest(update_date) -> Freshnest.UPDATED
            else -> Freshnest.OLD
        }


    fun getFreshnestStr(context: Context): String? = when (freshnest) {
        Freshnest.NEW -> context.getString(R.string.adjective_acp_new_text)
        Freshnest.UPDATED -> context.getString(R.string.acp_updated_text)
        Freshnest.OLD -> null
    }


    fun getFreshnestStrTextSizeInSP(context: Context): Int = when (freshnest) {
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



