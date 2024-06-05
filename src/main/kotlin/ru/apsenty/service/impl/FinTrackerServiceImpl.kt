package ru.apsenty.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.apsenty.dto.SpendingDto
import ru.apsenty.entity.SpendingEntity
import ru.apsenty.exception.SpendNotFoundException
import ru.apsenty.repository.FinTrackerRepository
import ru.apsenty.service.FinTrackerService

@Service
class FinTrackerServiceImpl(
    private val finTrackerRepository: FinTrackerRepository
) : FinTrackerService {
    override fun getAll(): List<SpendingDto> {
        return finTrackerRepository.findAll().map { it.toDto() }
    }

    override fun getById(id: Int): SpendingDto {
        return finTrackerRepository.findByIdOrNull(id)
            ?.toDto()
            ?: throw SpendNotFoundException(id)
    }

    override fun create(dto: SpendingDto): String {
        val id = finTrackerRepository.save(dto.toEntity()).id
        return "Запись успешно создана с id: $id"
    }

    private fun SpendingEntity.toDto(): SpendingDto {
        return SpendingDto(
            id = this.id,
            amount = this.amount,
            type = this.type,
            comment = this.comment
        )
    }

    private fun SpendingDto.toEntity(): SpendingEntity {
        return SpendingEntity(
            id = 0,
            amount = this.amount,
            type = this.type,
            comment = this.comment,
        )
    }
}