package com.mtd.kmmtestapp.viewModels

import co.touchlab.kermit.Logger
import com.mtd.kmmtestapp.UserSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class UserSettingsViewModel() : ViewModel(), KoinComponent {
    private val userSettings : UserSettings by inject()
    private val logger = Logger.withTag("UserSettingsViewModel")

    private val _roomSlug = MutableStateFlow<String?>(userSettings.getRoomSlug())
    val roomSlug = _roomSlug.asStateFlow()

    suspend fun activate() {
    }

    override fun onCleared() {
        logger.v("Clearing UserSettingsViewModel")
    }

    fun setRoomSlug(roomSlug: String?) {
        logger.d("Setting room slug to $roomSlug")
        _roomSlug.value = roomSlug
        userSettings.setRoomSlug(roomSlug)
    }
}