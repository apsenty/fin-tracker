package ru.apsenty.service

import ru.apsenty.dto.SpendingDto
import ru.apsenty.responses.CreateResponse
import ru.apsenty.responses.DeleteResponse
import ru.apsenty.responses.UpdateResponse

interface FinTrackerService {

    fun getAll(): List<SpendingDto>

    fun getById(id: Int): SpendingDto

    fun create(dto: SpendingDto): CreateResponse

    fun delete(id: Int): DeleteResponse

    fun update(id: Int, spendingUpdates: Map<String, Any>): UpdateResponse
}