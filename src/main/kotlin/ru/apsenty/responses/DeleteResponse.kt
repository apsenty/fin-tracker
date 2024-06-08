package ru.apsenty.responses

class DeleteResponse(spendingId: Int): BaseResponse(
    code = "success",
    id = spendingId,
    message = "Запись успешно удалена."
)