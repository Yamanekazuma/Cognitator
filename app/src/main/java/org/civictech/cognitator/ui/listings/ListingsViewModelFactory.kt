package org.civictech.cognitator.ui.listings

import androidx.compose.material3.DrawerState
import dagger.assisted.AssistedFactory

@AssistedFactory
interface ListingsViewModelFactory {
    fun create(drawerState: DrawerState): ListingsViewModel
}
