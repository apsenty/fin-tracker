package ru.apsenty.exception

data class ApiError(
    val errorCode: String,
    val description: String,
)