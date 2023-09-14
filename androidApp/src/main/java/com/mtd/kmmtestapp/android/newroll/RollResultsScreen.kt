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
import com.mtd.kmmtestapp.viewModels.LatestRollState

@Composable
fun RollResultsScreen(viewState: LatestRollState) {
    Column(
        Modifier
            .fillMaxWidth()
            .absolutePadding(10.dp, 20.dp, 10.dp, 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewState is LatestRollState.Initial) {
            Text(text = "Press above to roll")
        } else if (viewState is LatestRollState.LastSuccessfulRoll){
            Text(text = viewState.rollValue)
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = viewState.rollDetails)
        }
    }
}

@Composable
@Preview
fun PreviewRollResultsScreen() {
    RollResultsScreen(LatestRollState.Initial)
}