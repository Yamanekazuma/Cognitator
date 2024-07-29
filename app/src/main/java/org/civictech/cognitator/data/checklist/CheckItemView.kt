package org.civictech.cognitator.data.checklist

data class CheckItemView(
    val id: Int,
    val category: String,
    val risk: Int,
    val label: String
)
