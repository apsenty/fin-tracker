package ru.apsenty.exception

import org.springframework.http.HttpStatus

class SpendNotFoundException(spendingId: Int): BaseException(
    HttpStatus.NOT_FOUND,
    ApiError(
        errorCode = "spendingNotFound",
        description = "Запись с id:$spendingId не найдена."
    )
)