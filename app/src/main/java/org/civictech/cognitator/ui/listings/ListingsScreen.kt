package org.civictech.cognitator.ui.listings

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ListingsScreen(drawerState: DrawerState) {
    val vm = hiltViewModel(
        creationCallback = { factory: ListingsViewModelFactory ->
            factory.create(drawerState)
        }
    )


    ListingsView(vm)
}
