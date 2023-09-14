package com.mtd.kmmtestapp.android.newroll

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mtd.kmmtestapp.android.common.LoadingDialog
import com.mtd.kmmtestapp.res.SharedRes
import com.mtd.kmmtestapp.viewModels.NewRollViewModel

@Composable
fun NewDiceRollScreen( viewModel: NewRollViewModel = viewModel() ) {
    val viewState by viewModel.viewState.collectAsState()
    val scaffoldState = rememberScaffoldState()

    val contextForToast = LocalContext.current.applicationContext

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = SharedRes.strings.newRollTitle.resourceId)) })
        },
        content = { padding ->
            LoadingDialog(isLoading= viewState.isLoading)
            
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CreateNewRollScreen(viewModel = viewModel)
                RollResultsScreen(viewState.latestRollViewState)
            }
        }
    )

    // How do I set this up to use the viewState error message?
    if ( viewState.error != null ) {
        LaunchedEffect(key1 = viewState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = "Error creating new roll",
                actionLabel = "Dismiss"
            )
        }
    }
}

@Composable
@Preview
fun PreviewNewDiceRollScreen() {
    NewDiceRollScreen()
}