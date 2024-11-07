package com.example.galileo.widget

import android.content.Context
import android.util.ArraySet
import android.util.Log

const val PREFERENCE_NAME = "preference_galileo_01"
const val PREFERENCE_WIDGET_ID = "widgetId_galileo_01"

class MyPreference(context: Context){

    val preference = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)


    fun updateWidgetIds(ids : MutableSet<String>){

        val editor = preference.edit()
        val existingSet = getWidgetIds() ?: mutableSetOf()
        existingSet.addAll(ids)
        editor.putStringSet(PREFERENCE_WIDGET_ID, existingSet)
        editor.apply()
    }

    fun getWidgetIds() : MutableSet<String>? {
        return preference.getStringSet(PREFERENCE_WIDGET_ID, hashSetOf())
    }



}