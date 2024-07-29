package org.civictech.cognitator.data.check

import org.civictech.cognitator.data.checklist.CheckItem
import javax.inject.Inject

class CheckService @Inject constructor() {
    fun check(): Boolean {
        return true
    }

    fun getList(): List<CheckItem> {
        return emptyList()
    }

    fun getCategory(id: Int): String {
        return "Category"
    }
}
