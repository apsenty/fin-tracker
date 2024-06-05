package ru.apsenty.repository

import org.springframework.data.repository.CrudRepository
import ru.apsenty.entity.SpendingEntity

interface FinTrackerRepository: CrudRepository<SpendingEntity, Int> {
}