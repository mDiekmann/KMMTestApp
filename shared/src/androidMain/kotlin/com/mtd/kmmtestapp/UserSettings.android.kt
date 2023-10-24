package com.mtd.kmmtestapp

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
public class AndroidUserSettings public constructor(
    private val delegate: SharedPreferences
) : UserSettings {
    public class Factory(context: Context) : UserSettings.Factory {
        private val appContext = context.applicationContext

        public override fun create(): AndroidUserSettings {
            val preferencesName = "${appContext.packageName}_preferences"
            val delegate = appContext.getSharedPreferences(preferencesName, MODE_PRIVATE)
            return AndroidUserSettings(delegate)
        }
    }

    public override fun getRoomSlug(): String? = delegate.getString(roomSlugKey, null)
    public override fun setRoomSlug(roomSlug: String?) = delegate.edit().putString(roomSlugKey, roomSlug).apply()
}