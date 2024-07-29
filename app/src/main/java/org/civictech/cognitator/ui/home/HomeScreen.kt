package org.civictech.cognitator.ui.home

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(drawerState: DrawerState) {
    val vm = hiltViewModel(
        creationCallback = { factory: HomeViewModelFactory ->
            factory.create(drawerState)
        }
    )

    HomeView(vm)
}
