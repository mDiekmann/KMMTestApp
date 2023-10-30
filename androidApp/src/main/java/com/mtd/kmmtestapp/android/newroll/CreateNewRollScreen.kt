package com.mtd.kmmtestapp.android.newroll

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.ListItemPicker
import com.mtd.kmmtestapp.viewModels.NewRollViewModel
import kotlinx.coroutines.launch

@Composable
fun CreateNewRollScreen(viewModel: NewRollViewModel) {
    val diceCountInput by viewModel.diceCountInput.collectAsState()
    val diceSidesInput by viewModel.diceSidesInput.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ListItemPicker(
            value = diceCountInput,
            list = viewModel.possibleDiceCounts,
            onValueChange = { value ->
                viewModel.updateDiceCount(value)
            }
        )
        ListItemPicker(
            value = diceSidesInput,
            list = viewModel.possibleDiceSides,
            onValueChange = { value ->
                viewModel.updateDiceSides(value)
            }
        )
    }
    Button(
        onClick = {
            coroutineScope.launch {
                viewModel.rollDice()
            }
        },
        shape = RoundedCornerShape(size = 20.dp)
    ) {
        Text(text = "Roll Dice")
    }
}