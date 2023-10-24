package com.mtd.kmmtestapp.viewModels

import co.touchlab.kermit.Logger
import com.mtd.kmmtestapp.UserSettings
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class UserSettingsViewModel() : KMMViewModel(), KoinComponent {
    private val userSettings : UserSettings by inject()
    private val logger = Logger.withTag("UserSettingsViewModel")

    private val _roomSlug = MutableStateFlow<String?>(viewModelScope, userSettings.getRoomSlug())

    @NativeCoroutinesState
    val roomSlug = _roomSlug.asStateFlow()

    fun setRoomSlug(roomSlug: String?) {
        logger.d("Setting room slug to $roomSlug")
        _roomSlug.value = roomSlug
        userSettings.setRoomSlug(roomSlug)
    }
}