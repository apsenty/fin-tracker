package ru.apsenty.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.apsenty.dto.SpendingDto
import ru.apsenty.service.FinTrackerService

@RestController
@RequestMapping("/fintracker")
class FinTrackerController(
    private val finTrackerService: FinTrackerService
) {

    @GetMapping
    fun getAll(): List<SpendingDto> {
        return finTrackerService.getAll()
    }
}