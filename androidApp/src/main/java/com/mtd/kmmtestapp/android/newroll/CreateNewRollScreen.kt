package com.mtd.kmmtestapp.android.newroll

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.ListItemPicker
import com.chargemap.compose.numberpicker.NumberPicker
import com.mtd.kmmtestapp.viewModels.NewRollViewModel

@Composable
fun CreateNewRollScreen(viewModel: NewRollViewModel) {
    val minDiceCount = viewModel.minDiceCount
    val maxDiceCount = viewModel.maxDiceCount
    val possibleDiceSides = viewModel.possibleDiceSides

    var diceCountInput by rememberSaveable { mutableStateOf(minDiceCount) }
    var diceSidesInput by rememberSaveable { mutableStateOf(possibleDiceSides[0]) }

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