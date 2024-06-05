package ru.apsenty.service

import ru.apsenty.dto.SpendingDto

interface FinTrackerService {

    fun getAll(): List<SpendingDto>
}