package com.mtd.kmmtestapp.android

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.ListItemPicker
import com.chargemap.compose.numberpicker.NumberPicker
import com.mtd.kmmtestapp.res.SharedRes
import com.mtd.kmmtestapp.viewModels.NewRollViewModel

@Composable
fun NewDiceRollScreen() {
    val viewModel = remember {
        NewRollViewModel()
    }

    val viewState = viewModel.viewState.collectAsState()

    val minDiceCount = viewModel.minDiceCount
    val maxDiceCount = viewModel.maxDiceCount
    val possibleDiceSides = viewModel.possibleDiceSides

    var diceCountInput by remember { mutableStateOf(minDiceCount) }
    var diceSidesInput by remember { mutableStateOf(possibleDiceSides[0]) }

    val contextForToast = LocalContext.current.applicationContext

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = SharedRes.strings.newRollTitle.resourceId)) })
        },
        content = { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    NumberPicker(
                        value = diceCountInput,
                        range = minDiceCount..maxDiceCount,
                        onValueChange = { value ->
                            diceCountInput = value
                        }
                    )
                    Text(
                        modifier = Modifier.size(24.dp),
                        textAlign = TextAlign.Center,
                        text = "d"
                    )
                    ListItemPicker(
                        value = diceSidesInput,
                        list = possibleDiceSides,
                        onValueChange = { value ->
                            diceSidesInput = value
                        }
                    )
                }
                Button(
                    onClick = {
                        viewModel.rollDice(diceCountInput, diceSidesInput)
                    },
                    shape = RoundedCornerShape(size = 20.dp)
                ) {
                    Text(text = "Roll Dice")
                }
            }
        }
    )
}

@Composable
@Preview
fun PreviewNewDiceRollScreen() {
    NewDiceRollScreen()
}