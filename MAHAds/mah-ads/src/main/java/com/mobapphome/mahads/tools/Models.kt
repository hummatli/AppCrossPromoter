package com.mobapphome.mahads.tools

import com.google.gson.FieldAttributes
import com.google.gson.ExclusionStrategy
import java.text.SimpleDateFormat


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


interface MAHAdsDlgExitListener {
    fun onYes()

    fun onNo()

    fun onExitWithoutExitDlg()

    fun onEventHappened(eventStr: String)
}

class GsonDeserializeExclusion : ExclusionStrategy {

    override fun shouldSkipField(f: FieldAttributes): Boolean {
        return f.declaredClass == SimpleDateFormat::class.java
    }

    override fun shouldSkipClass(clazz: Class<*>): Boolean {
        return false
    }

}