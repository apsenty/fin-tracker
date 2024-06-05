package ru.apsenty.service

import ru.apsenty.dto.SpendingDto

interface FinTrackerService {

    fun getAll(): List<SpendingDto>

    fun getById(id: Int): SpendingDto

    fun create(dto: SpendingDto): String

    fun update(id: Int, dto: SpendingDto): String
}