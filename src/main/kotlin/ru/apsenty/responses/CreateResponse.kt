package ru.apsenty.responses

class CreateResponse(spendingId: Int): BaseResponse(
    code = "success",
    id = spendingId,
    message = "Запись успешно создана."
)