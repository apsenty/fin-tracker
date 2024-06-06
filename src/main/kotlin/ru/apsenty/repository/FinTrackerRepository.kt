package ru.apsenty.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.apsenty.entity.SpendingEntity

interface FinTrackerRepository: JpaRepository<SpendingEntity, Int> {
}