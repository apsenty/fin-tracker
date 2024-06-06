package ru.apsenty.models

data class BaseResponse(
    val code: String? = null,
    val id: Int? = null,
    val message: String? = null,
)