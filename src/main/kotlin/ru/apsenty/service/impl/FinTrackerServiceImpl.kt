package ru.apsenty.service.impl

import org.springframework.stereotype.Service
import ru.apsenty.dto.SpendingDto
import ru.apsenty.entity.SpendingEntity
import ru.apsenty.repository.FinTrackerRepository
import ru.apsenty.service.FinTrackerService

@Service
class FinTrackerServiceImpl(
    private val finTrackerRepository: FinTrackerRepository
) : FinTrackerService {
    override fun getAll(): List<SpendingDto> {
        return finTrackerRepository.findAll().map { it.toDto() }
    }

    private fun SpendingEntity.toDto(): SpendingDto {
        return SpendingDto(
            id = this.id,
            amount = this.amount,
            type = this.type,
            comment = this.comment
        )
    }
}