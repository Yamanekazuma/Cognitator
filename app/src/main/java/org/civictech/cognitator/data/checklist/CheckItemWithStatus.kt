package org.civictech.cognitator.data.checklist

data class CheckItemWithStatus(
    val id: Int,
    val category: String,
    val risk: Int,
    val label: String,
    val description: String,
    val isEnable: Boolean,
    val status: CheckItemStatus
)
