package ru.apsenty.models

import com.fasterxml.jackson.annotation.JsonInclude

data class RequestBody(
    @get:JsonInclude(JsonInclude.Include.NON_NULL)
    var amount: Double? = null,
    @get:JsonInclude(JsonInclude.Include.NON_NULL)
    var type: String? = null,
    @get:JsonInclude(JsonInclude.Include.NON_NULL)
    var comment: String? = null,
)
