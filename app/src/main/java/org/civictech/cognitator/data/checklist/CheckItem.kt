package org.civictech.cognitator.data.checklist

enum class CheckItemStatus {
    None,
    Safe,
    Dangerous
}

data class CheckItem(
    val id: Int,
    val categoryId: Int,
    val risk: Int,
    val label: String,
    val description: String,
    var isEnable: Boolean,
    var status: CheckItemStatus
)
