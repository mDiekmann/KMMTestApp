package com.mtd.kmmtestapp.android


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
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
fun SearchScreen() {
    // Your Search screen content here
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
            SearchScreen()
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
                        modifier = Modifier.fillMaxSize(0.5F)
                    )
                },
                label = { label },
                selected = navItem.route == backStackEntry.value?.destination?.route,
                onClick = { navController.navigate(navItem.route) },
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