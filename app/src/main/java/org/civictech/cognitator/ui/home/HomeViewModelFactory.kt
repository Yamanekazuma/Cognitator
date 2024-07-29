package org.civictech.cognitator.ui.home

import androidx.compose.material3.DrawerState
import dagger.assisted.AssistedFactory

@AssistedFactory
interface HomeViewModelFactory {
    fun create(drawerState: DrawerState): HomeViewModel
}
