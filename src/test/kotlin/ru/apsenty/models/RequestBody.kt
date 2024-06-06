package ru.apsenty.models

data class RequestBody(
    var amount: Double? = null,
    var type: String? = null,
    var comment: String? = null,
)
