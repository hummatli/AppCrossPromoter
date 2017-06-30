package com.mobapphome.mahads.tools

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.mobapphome.mahads.R
import com.mobapphome.mahads.mahfragments.MAHFragmentExeption
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.util.*


//General --------------------------------------------------------------------
fun checkPackageIfExists(context: Context, pckgName: String): Boolean {
    try {
        val info = context.packageManager.getApplicationInfo(pckgName, 0)
        return true
    } catch (e: PackageManager.NameNotFoundException) {
        return false
    }

}

fun writeStringToCache(context: Context, stringToCache: String) {
    val outputStream: FileOutputStream

    try {
        outputStream = context.openFileOutput(Constants.PROGRAM_LIST_CACHE, Context.MODE_PRIVATE)
        outputStream.write(stringToCache.toByteArray())
        outputStream.close()
    } catch (e: Exception) {
        //e.printStackTrace();
        Log.d(Constants.LOG_TAG_MAH_ADS, "IOexception = " + e.message, e)
    }

}

fun readStringFromCache(context: Context): String? {
    val inputStream: FileInputStream

    try {
        inputStream = context.openFileInput(Constants.PROGRAM_LIST_CACHE)

        val r = BufferedReader(InputStreamReader(inputStream))
        val total = StringBuilder()
        var line: String
        r.forEachLine {
            total.append(it).append('\n')

        }
//        while ((line = r.readLine()) != null) {
//            total.append(line).append('\n')
//        }
        inputStream.close()
        return total.toString()
    } catch (e: Exception) {
        Log.d(Constants.LOG_TAG_MAH_ADS, "IOexception = " + e.message, e)
        //e.printStackTrace();
    }

    return null
}

fun getVersionFromLocal(context: Context): Int {
    return getSharedPref(context).getInt(Constants.MAH_ADS_VERSION, -1)
}


fun getUrlOfImage(urlRootOnServer: String, initialUrlForImage: String): String {
    if (initialUrlForImage.startsWith("http://") || initialUrlForImage.startsWith("https://")) {
        return initialUrlForImage
    } else {
        return urlRootOnServer + initialUrlForImage
    }
}


fun getRootFromUrl(urlStr: String): String {
    return urlStr.substring(0, urlStr.lastIndexOf('/') + 1)
}

fun showMarket(context: Context, pckgName: String) {
    val marketIntent = Intent(Intent.ACTION_VIEW)
    marketIntent.data = Uri.parse("market://details?id=" + pckgName)
    try {
        context.startActivity(marketIntent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, context.getString(R.string.mah_ads_play_service_not_found), Toast.LENGTH_LONG).show()
        Log.e(Constants.LOG_TAG_MAH_ADS, context.getString(R.string.mah_ads_play_service_not_found) + e.message)
    }

}

//Program list filtering----------------------------------------------------------------
private fun programSelect(programsSource: MutableList<Program>, programsSelectedLocal: MutableList<Program>) {
    val random = Random()
    while (programsSource.size > 0 && programsSelectedLocal.size < 2) {
        //Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "DBRequester prog filtered count  = " + programsFiltered.size());
        val randomIndex = random.nextInt(programsSource.size)
        //Log.i(MAHAdsController.LOG_TAG_MAH_ADS, "DBRequester random number = " + randomIndex);
        val progRandom = programsSource[randomIndex]
        programsSource.removeAt(randomIndex)
        if (!programsSelectedLocal.contains(progRandom)) {
            programsSelectedLocal.add(progRandom)
        }
    }
}

fun filterMAHRequestResult(context: Context, requestResult: MAHRequestResult): MAHRequestResult {

    val programsTotal = requestResult.programsTotal
    if (programsTotal != null) {
        val programsFiltered = LinkedList<Program>()
        val programsNotInstalledOld = LinkedList<Program>()
        val programsNotInstalledFresh = LinkedList<Program>()
        val programsInstalled = LinkedList<Program>()

        for (c in programsTotal) {
            if (c.uri.trim { it <= ' ' } != context.packageName.trim { it <= ' ' }) {
                programsFiltered.add(c)
                if (!checkPackageIfExists(context, c.uri.trim { it <= ' ' })) {
                    val freshnest = c.freshnest
                    if (freshnest == Freshnest.NEW || freshnest == Freshnest.UPDATED) {
                        programsNotInstalledFresh.add(c)
                    } else {
                        programsNotInstalledOld.add(c)
                    }
                } else {
                    programsInstalled.add(c)
                }
            }
        }

        //For generating selected programs start
        val programsSelectedLocal = LinkedList<Program>()
        programSelect(programsNotInstalledFresh, programsSelectedLocal)
        programSelect(programsNotInstalledOld, programsSelectedLocal)
        programSelect(programsInstalled, programsSelectedLocal)


        requestResult.programsFiltered = programsFiltered
        requestResult.programsSelected = programsSelectedLocal

        return requestResult
    } else {
        Log.i(Constants.LOG_TAG_MAH_ADS, "Programs total is null")
        return requestResult
    }
}

fun getSharedPref(context: Context): SharedPreferences {
    return context.getSharedPreferences("MAH_ADS", Context.MODE_PRIVATE)
}

fun sorrWithMAHExeption(f: () -> Unit) {
    try {
        f()
    } catch (e: MAHFragmentExeption) {
        Log.d(Constants.LOG_TAG_MAH_ADS, e.message, e)
    }
}

