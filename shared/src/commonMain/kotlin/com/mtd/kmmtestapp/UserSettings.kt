package com.mtd.kmmtestapp

public interface UserSettings {
    val roomSlugKey: String
        get() = "RoomSlug"

    public fun getRoomSlug(): String?
    public fun setRoomSlug(roomSlug: String?)
}