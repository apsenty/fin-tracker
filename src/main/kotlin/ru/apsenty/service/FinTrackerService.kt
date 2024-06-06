package ru.apsenty.service

import ru.apsenty.dto.SpendingDto
import ru.apsenty.responses.CreateResponse
import ru.apsenty.responses.DeleteResponse
import ru.apsenty.responses.UpdateResponse

interface FinTrackerService {

    fun getAll(): List<SpendingDto>

    fun getById(id: Int): SpendingDto

    fun create(dto: SpendingDto): CreateResponse

    fun update(id: Int, dto: SpendingDto): UpdateResponse

    fun delete(id: Int): DeleteResponse
}