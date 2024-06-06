package ru.apsenty.responses

abstract class BaseResponse(
    val code: String,
    val id: Int?,
    val message: String
)