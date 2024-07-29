package org.civictech.cognitator.ui.settings

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(drawerState: DrawerState) {
    val vm = hiltViewModel(
        creationCallback = { factory: SettingsViewModelFactory ->
            factory.create(drawerState)
        }
    )

    SettingsView(vm)
}
