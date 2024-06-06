package ru.apsenty.models

data class GetByIdResponse(
    val id: Int? = null,
    val amount: Double? = null,
    val type: String? = null,
    val comment: String? = null,
)
