package com.nepplus.colosseum.utils

import android.content.Context

class ContextUtil {

    companion object {

        private val prefName = "ColosseumPref"

        private val AUTO_LOGIN = "AUTO_LOGIN"
        private val TOKEN = "TOKEN"

        fun setAutoLogin(context: Context, isAutoLogin: Boolean) {

            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putBoolean(AUTO_LOGIN, isAutoLogin).apply()

        }

        fun getAutoLogin(context: Context): Boolean {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getBoolean(AUTO_LOGIN, false)
        }

        fun setToken(context: Context, token: String) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(TOKEN, token).apply()
        }

        fun getToken(context: Context): String {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(TOKEN, "")!!
        }

    }

}