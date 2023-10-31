package com.mtd.kmmtestapp

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
public class AndroidUserSettings public constructor(
    private val delegate: SharedPreferences
) : UserSettings {
    public override fun getRoomSlug(): String? = delegate.getString(roomSlugKey, null)
    public override fun setRoomSlug(roomSlug: String?) = delegate.edit().putString(roomSlugKey, roomSlug).apply()
}