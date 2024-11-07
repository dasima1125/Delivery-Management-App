package com.example.galileo.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
import com.example.galileo.R
import com.example.galileo.db.DBHandler
import com.example.galileo.updater.updatecontrol
import java.util.Calendar
import java.util.Random

/**
 * Implementation of App Widget functionality.
 */

const val WIDGET_SYNC_POINT = "GALILEO_SYNC"

lateinit var preference_galileo: MyPreference

class widget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        if (!::preference_galileo.isInitialized)
            preference_galileo = MyPreference(context)

        val ids = preference_galileo.getWidgetIds()

        for (appWidgetId in appWidgetIds) {
            if (ids != null) {
                ids.add(appWidgetId.toString())
            }
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
        ids?.let { preference_galileo.updateWidgetIds(it) }
    }

    override fun onEnabled(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, widget::class.java)
        intent.action = WIDGET_SYNC_POINT
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 60000, pendingIntent)
    }


    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled  -1861554638
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        val idsToRemove = appWidgetIds.map { it.toString() }
        Log.d("galileo_test", "Deleted complete IDs: $idsToRemove")

        val preference2 = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)
        val editor = preference2.edit()

        editor.remove(PREFERENCE_WIDGET_ID) // warring flush system Handle with caution
        editor.apply()

    }
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("galileo_test", "onReceive:reporting")

        if (WIDGET_SYNC_POINT == intent?.action) {
            if (!::preference_galileo.isInitialized) {
                preference_galileo = MyPreference(context)
            }

            val ids = preference_galileo.getWidgetIds()
            if (ids != null) {
                Log.d("galileo_test", "onReceive: $ids")
                for (id in ids){
                    updateAppWidget(context, AppWidgetManager.getInstance(context), id.toInt())
                }
            }
        }
        super.onReceive(context, intent)
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {


    val intent = Intent(context, widget::class.java)
    intent.action = WIDGET_SYNC_POINT

    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    val views = RemoteViews(context.packageName, R.layout.widget)

    var db = DBHandler(context)
    db.autoUpdater()
    val log = db.widgetBroadcast()//배달완료된거만 올림 나중에 논리구성 수정요함
    //Log.d("oiia11", "1st : ${log[0]}" )
    //Log.d("oiia11", "2nd : ${log[1]}" )
    //Log.d("oiia11", "3rd : ${log[2]}" )




    //코드가 더럽다 더러워,,,
    if (log[0]!="null"){
        views.setTextColor(R.id.list_item_1_1st,          ContextCompat.getColor(context, R.color.white))
        views.setTextColor(R.id.list_item_1_2nd,          ContextCompat.getColor(context, R.color. gray))
        views.setTextColor(R.id.list_item_1_3rd_coreInfo, ContextCompat.getColor(context, R.color.white))

        views.setTextViewText(R.id.list_item_1_1st, log[0].split("^")[0].toString())
        views.setTextViewText(R.id.list_item_1_2nd, log[0].split("^")[1].split(",")[2])
        views.setOnClickPendingIntent(R.id.list_item_1_3rd_coreInfo,pendingIntent)
    }
    else if (log[0] == "null"){
        views.setTextViewText(R.id.list_item_1_1st, "배달온게없네요...")
        views.setTextViewText(R.id.list_item_1_2nd, "서운하네 ..")
    }

    if (log[1]!="null") {
        views.setInt(R.id.firstLine, "setBackgroundColor", ContextCompat.getColor(context, R.color.gray))

        views.setTextColor(R.id.list_item_2_1st,          ContextCompat.getColor(context, R.color.white))
        views.setTextColor(R.id.list_item_2_2nd,          ContextCompat.getColor(context, R.color. gray))
        views.setTextColor(R.id.list_item_2_3rd_coreInfo, ContextCompat.getColor(context, R.color.white))

        views.setTextViewText(R.id.list_item_2_1st, log[1].split("^")[0].toString())
        views.setTextViewText(R.id.list_item_2_2nd, log[1].split("^")[1].split(",")[2])
        views.setTextViewText(R.id.list_item_2_3rd_coreInfo, log[1].split("^")[2].split("|")[0])
    }
    else if (log[1] == "null"){
        views.setInt(R.id.firstLine, "setBackgroundColor", ContextCompat.getColor(context, R.color.dark_gray))

        views.setTextColor(R.id.list_item_2_1st,          ContextCompat.getColor(context, R.color.dark_gray))
        views.setTextColor(R.id.list_item_2_2nd,          ContextCompat.getColor(context, R.color.dark_gray))
        views.setTextColor(R.id.list_item_2_3rd_coreInfo, ContextCompat.getColor(context, R.color.dark_gray))
    }

    if (log[2]!="null") {
        views.setInt(R.id.secondLine, "setBackgroundColor", ContextCompat.getColor(context, R.color.gray))

        views.setTextColor(R.id.list_item_3_1st,          ContextCompat.getColor(context, R.color.white))
        views.setTextColor(R.id.list_item_3_2nd,          ContextCompat.getColor(context, R.color. gray))
        views.setTextColor(R.id.list_item_3_3rd_coreInfo, ContextCompat.getColor(context, R.color.white))

        views.setTextViewText(R.id.list_item_3_1st, log[2].split("^")[0].toString())
        views.setTextViewText(R.id.list_item_3_2nd, log[2].split("^")[1].split(",")[2])
        views.setTextViewText(R.id.list_item_3_3rd_coreInfo, log[2].split("^")[2].split("|")[0])
    }
    else if (log[2] == "null"){
        views.setInt(R.id.secondLine, "setBackgroundColor", ContextCompat.getColor(context, R.color.dark_gray))

        views.setTextColor(R.id.list_item_3_1st,          ContextCompat.getColor(context, R.color.dark_gray))
        views.setTextColor(R.id.list_item_3_2nd,          ContextCompat.getColor(context, R.color.dark_gray))
        views.setTextColor(R.id.list_item_3_3rd_coreInfo, ContextCompat.getColor(context, R.color.dark_gray))

    }

    appWidgetManager.updateAppWidget(appWidgetId, views)

    db.close()
    Log.d("galileo_test", "updateAppWidget:  complete")
}
