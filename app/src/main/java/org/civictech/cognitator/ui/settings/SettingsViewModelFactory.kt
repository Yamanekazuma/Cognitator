package org.civictech.cognitator.ui.settings

import androidx.compose.material3.DrawerState
import dagger.assisted.AssistedFactory

@AssistedFactory
interface SettingsViewModelFactory {
    fun create(drawerState: DrawerState): SettingsViewModel
}
