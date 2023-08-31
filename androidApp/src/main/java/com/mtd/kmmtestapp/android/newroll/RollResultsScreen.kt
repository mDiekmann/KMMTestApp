package com.mtd.kmmtestapp.android.newroll

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtd.kmmtestapp.models.DiceRoll

@Composable
fun RollResultsScreen(diceRoll: DiceRoll?) {
    Column(
        Modifier
            .fillMaxWidth()
            .absolutePadding(10.dp, 20.dp, 10.dp, 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (diceRoll == null) {
            Text(text = "Press above to roll")
        } else {
            Text(text = "Last Roll (${diceRoll.input}): ${diceRoll.result}")
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "${diceRoll.detailsAsIntArray}")
        }
    }
}

@Composable
@Preview
fun PreviewRollResultsScreen() {
    RollResultsScreen(diceRoll = null)
}