package org.civictech.cognitator.ui.home

import androidx.compose.material3.DrawerState
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import org.civictech.cognitator.data.check.CheckService
import org.civictech.cognitator.data.checklist.CheckItemView
import javax.inject.Inject

@HiltViewModel(assistedFactory = HomeViewModelFactory::class)
class HomeViewModel @AssistedInject constructor(
    @Assisted private val drawerState: DrawerState
) : ViewModel() {

    @Inject
    lateinit var checkService: CheckService

    fun getDrawerState(): DrawerState = drawerState

    fun check(): Boolean = checkService.check()

    fun getNotifiableItems(): List<CheckItemView> = checkService.getList().map {
        CheckItemView(it.id, checkService.getCategory(it.categoryId), it.risk, it.label)
    }.sortedByDescending { it.risk }
}
