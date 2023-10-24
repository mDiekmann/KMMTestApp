package com.mtd.kmmtestapp

val roomSlugKey = "RoomSlug"
public interface UserSettings {
    public companion object;

    public interface Factory {
        public fun create(): UserSettings
    }

    public fun getRoomSlug(): String?
    public fun setRoomSlug(roomSlug: String?)
}