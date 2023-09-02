package com.mtd.kmmtestapp.android.common


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mtd.kmmtestapp.android.newroll.NewDiceRollScreen
import com.mtd.kmmtestapp.android.rollhistory.RollHistoryScreen
import com.mtd.kmmtestapp.res.SharedRes

sealed class BottomNavigationScreens(val route: String, @StringRes val stringRes: Int, val imageRes: Int) {
    object NewRoll : BottomNavigationScreens(
        "NewRoll",
        SharedRes.strings.newRollLabel.resourceId,
        SharedRes.images.dice_icon.drawableResId
    )
    object RollHistory : BottomNavigationScreens(
        "RollHistory",
        SharedRes.strings.rollHistoryLabel.resourceId,
        SharedRes.images.list_icon.drawableResId
    )
}

@Composable
fun AppNavigation(navController: NavHostController,
                  paddingModifier: Modifier) {
    NavHost(navController,
        startDestination = BottomNavigationScreens.NewRoll.route
    ) {
        composable(route = BottomNavigationScreens.NewRoll.route)
        {
            NewDiceRollScreen()
        }
        composable(route = BottomNavigationScreens.RollHistory.route)
        {
            RollHistoryScreen()
        }
    }
}

@Composable
private fun BottomBarNavigation(
    navController: NavController,
    bottomNavigationItems: List<BottomNavigationScreens>
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation {
        bottomNavigationItems.forEach { navItem ->
            val label = stringResource(id = navItem.stringRes)
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = navItem.imageRes),
                        contentDescription = label,
                    )
                },
                label = { Text(text = label) },
                selected = navItem.route == backStackEntry.value?.destination?.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                          },
            )
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.NewRoll,
        BottomNavigationScreens.RollHistory,
    )

    Scaffold(
        bottomBar = {
            BottomBarNavigation(navController, bottomNavigationItems)
        }
    ) { paddingValues ->
        AppNavigation(
            navController = navController,
            paddingModifier = Modifier.padding(paddingValues)
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}