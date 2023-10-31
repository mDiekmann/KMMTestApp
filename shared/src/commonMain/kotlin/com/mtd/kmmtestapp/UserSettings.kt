package com.mtd.kmmtestapp

public interface UserSettings {
    public companion object;

    val roomSlugKey: String
        get() = "RoomSlug"

    public fun getRoomSlug(): String?
    public fun setRoomSlug(roomSlug: String?)
}