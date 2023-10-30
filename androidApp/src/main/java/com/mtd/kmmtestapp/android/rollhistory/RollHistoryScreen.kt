package com.mtd.kmmtestapp.android.rollhistory

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mtd.kmmtestapp.models.RollInfoModel
import com.mtd.kmmtestapp.res.SharedRes
import com.mtd.kmmtestapp.viewModels.RollHistoryViewModel
import com.mtd.kmmtestapp.viewModels.RollHistoryViewState
import kotlinx.coroutines.launch

@Composable
fun RollHistoryScreen() {
    val viewModel = remember {
        RollHistoryViewModel()
    }
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(viewModel) {
        viewModel.activate()
    }

    val coroutineScope = rememberCoroutineScope()
    val contextForToast = LocalContext.current.applicationContext

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = SharedRes.strings.rollHistoryTitle.resourceId)) },
                actions = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            viewModel.clearDiceRolls()
                        }
                    }) {
                        Icon(Icons.Filled.Delete, null)
                    }
                }
            )
        },
        content = {padding ->
            Box(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                when(val state = viewState) {
                    is RollHistoryViewState.Empty -> Empty()
                    is RollHistoryViewState.Success -> {
                        val rolls = state.rolls
                        Success(successData = rolls)
                    }
                    is RollHistoryViewState.Error -> {
                        val error = state.error
                        DisplayError(error)
                    }

                    RollHistoryViewState.Initial -> {
                        // no-op
                    }
                }
            }
        }
    )
}

@Composable
fun Empty() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = SharedRes.strings.rollHistoryEmptyLabel.resourceId))
    }
}

@Composable
fun DisplayError(error: String) {

}

@Composable
fun Success(
    successData: List<RollInfoModel>
) {
    DiceRollList(diceRolls = successData)
}

@Composable
fun DiceRollList(
    diceRolls: List<RollInfoModel>
) {
    val listState = rememberLazyListState()

    LazyColumn (
        state = listState,
        modifier = Modifier.simpleVerticalScrollbar(state = listState)
    ){
        items(diceRolls) { item ->
            Text(text = item.equation)
        }
    }
}

@Composable
fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 8.dp
): Modifier {
    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )

    return drawWithContent {
        drawContent()

        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
        val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f

        // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
        if (needDrawScrollbar && firstVisibleElementIndex != null) {
            val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
            val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
            val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight

            drawRect(
                color = Color.Red,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = alpha
            )
        }
    }
}

@Composable
@Preview
fun PreviewRollHistoryScreen() {
    RollHistoryScreen()
}