package ru.apsenty.responses

class UpdateResponse(spendingId: Int): BaseResponse(
    code = "success",
    id = spendingId,
    message = "Запись успешно обновлена."
)