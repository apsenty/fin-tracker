package ru.apsenty.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.apsenty.dto.SpendingDto
import ru.apsenty.entity.SpendingEntity
import ru.apsenty.exception.SpendNotFoundException
import ru.apsenty.repository.FinTrackerRepository
import ru.apsenty.responses.CreateResponse
import ru.apsenty.responses.DeleteResponse
import ru.apsenty.responses.UpdateResponse
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

    override fun create(dto: SpendingDto): CreateResponse {
        val id = finTrackerRepository.save(dto.toEntity()).id
        return CreateResponse(id)
    }

    override fun update(id: Int, dto: SpendingDto): UpdateResponse {
        val existingSpending = finTrackerRepository.findByIdOrNull(id)
            ?: throw SpendNotFoundException(id)

        existingSpending.amount = dto.amount
        existingSpending.type = dto.type
        existingSpending.comment = dto.comment

        finTrackerRepository.save(existingSpending)
        return UpdateResponse(id)
    }

    override fun delete(id: Int): DeleteResponse {
        val existingSpending = finTrackerRepository.findByIdOrNull(id)
            ?: throw SpendNotFoundException(id)

        finTrackerRepository.deleteById(existingSpending.id)
        return DeleteResponse(id)
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