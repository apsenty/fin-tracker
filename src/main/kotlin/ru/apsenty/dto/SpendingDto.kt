package ru.apsenty.dto

data class SpendingDto(
    val id: Int? = null,
    val amount: Double,
    val type: String,
    val comment: String? = null
)
