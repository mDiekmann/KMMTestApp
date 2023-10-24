package com.mtd.kmmtestapp.android.userSettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.mtd.kmmtestapp.res.SharedRes
import com.mtd.kmmtestapp.viewModels.UserSettingsViewModel

@Composable
fun UserSettingsScreen() {
    val viewModel = remember {
        UserSettingsViewModel()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = SharedRes.strings.userSettingsTitle.resourceId)) }
            )
        },
        content = { padding->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var text by remember {
                    mutableStateOf(
                        TextFieldValue(
                            viewModel.roomSlug.value ?: ""
                        )
                    )
                }

                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        if (it.text.isEmpty()) {
                            viewModel.setRoomSlug(null)
                        } else {
                            viewModel.setRoomSlug(it.text)
                        }
                    },
                    label = { Text("Room Slug") }
                )
            }
        }
    )
}