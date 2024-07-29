package org.civictech.cognitator.ui.home.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.civictech.cognitator.R
import org.civictech.cognitator.ui.home.HomeScreen
import org.civictech.cognitator.ui.listings.ListingsScreen
import org.civictech.cognitator.ui.settings.SettingsScreen
import org.civictech.cognitator.ui.theme.CognitatorTheme

private enum class MainRoute {
    Home,
    Listings,
    Statistics,
    Settings
}

private data class DrawerMenu(val icon: ImageVector, val title: String, val route: String)

@Composable
private fun DrawerContentDivider(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        modifier = modifier.padding(10.dp, 0.dp)
    )
}

@Composable
private fun DrawerContent(
    menus: Array<DrawerMenu>,
    bottomMenus: Array<DrawerMenu>,
    onMenuClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.primary)
                .padding(0.dp, 0.dp, 0.dp, 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(100.dp, 20.dp),
                imageVector = Icons.Filled.Lock,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
            )
        }

        HorizontalDivider()

        menus.forEach {
            NavigationDrawerItem(
                label = { Text(text = it.title) },
                icon = { Icon(imageVector = it.icon, contentDescription = null) },
                selected = false,
                onClick = {
                    onMenuClick(it.route)
                }
            )

            DrawerContentDivider()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        DrawerContentDivider()

        bottomMenus.forEach {
            NavigationDrawerItem(
                label = { Text(text = it.title) },
                icon = { Icon(imageVector = it.icon, contentDescription = null) },
                selected = false,
                onClick = {
                    onMenuClick(it.route)
                }
            )
        }

        HorizontalDivider()

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {
    val menus = arrayOf(
        DrawerMenu(
            Icons.Filled.Home,
            stringResource(R.string.screen_home),
            MainRoute.Home.name
        ),
        DrawerMenu(
            Icons.AutoMirrored.Filled.List,
            stringResource(R.string.screen_listings),
            MainRoute.Listings.name
        ),
        DrawerMenu(
            Icons.Filled.Info,
            stringResource(R.string.screen_statistics),
            MainRoute.Statistics.name
        )
    )

    val bottomMenus = arrayOf(
        DrawerMenu(
            Icons.Filled.Settings,
            stringResource(R.string.screen_settings),
            MainRoute.Settings.name
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(menus, bottomMenus) { route ->
                    coroutineScope.launch {
                        drawerState.close()
                    }

                    navController.navigate(route)
                }
            }
        }) {

        if (LocalInspectionMode.current) {
            Text(text = "Preview")
        } else {
            NavHost(navController = navController, startDestination = MainRoute.Home.name) {
                composable(MainRoute.Home.name) {
                    HomeScreen(drawerState)
                }
                composable(MainRoute.Listings.name) {
                    ListingsScreen(drawerState)
                }
                composable(MainRoute.Statistics.name) {
                    Text(text = "Statistics")
                }
                composable(MainRoute.Settings.name) {
                    SettingsScreen(drawerState)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewLight() {
    CognitatorTheme {
        MainNavigation(drawerState = rememberDrawerState(initialValue = DrawerValue.Open))
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewNight() {
    PreviewLight()
}