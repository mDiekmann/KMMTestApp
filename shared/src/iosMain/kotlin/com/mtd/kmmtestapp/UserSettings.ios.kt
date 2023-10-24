package com.mtd.kmmtestapp

import platform.Foundation.NSUserDefaults

public class iOSUserSettings public constructor(
    private val delegate: NSUserDefaults
) : UserSettings {

    public class Factory : UserSettings.Factory {
        public override fun create(): iOSUserSettings {
            val delegate = NSUserDefaults.standardUserDefaults
            return iOSUserSettings(delegate)
        }
    }
    public override fun getRoomSlug(): String? = NSUserDefaults.standardUserDefaults.stringForKey(roomSlugKey)
    public override fun setRoomSlug(roomSlug: String?) = NSUserDefaults.standardUserDefaults.setObject(roomSlug, roomSlugKey)
}