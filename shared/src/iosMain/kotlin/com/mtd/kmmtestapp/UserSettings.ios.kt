package com.mtd.kmmtestapp

import platform.Foundation.NSUserDefaults

public class iOSUserSettings public constructor(
    private val delegate: NSUserDefaults
) : UserSettings {
    public override fun getRoomSlug(): String? = delegate.stringForKey(roomSlugKey)
    public override fun setRoomSlug(roomSlug: String?) = delegate.setObject(roomSlug, roomSlugKey)
}