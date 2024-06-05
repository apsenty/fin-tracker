package ru.apsenty.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.apsenty.dto.SpendingDto
import ru.apsenty.service.FinTrackerService

@RestController
@RequestMapping("/fintracker")
class FinTrackerController(private val finTrackerService: FinTrackerService) {
    /**
     * Запрос всех записей расходов/доходов
     */
    @GetMapping
    fun getAll(): List<SpendingDto> {
        return finTrackerService.getAll()
    }

    /**
     * Запрос записи расхода/дохода по её id
     */
    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: Int): SpendingDto {
        return finTrackerService.getById(id)
    }

    /**
     * Создание записи расхода/дохода
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody dto: SpendingDto): String {
        return finTrackerService.create(dto)
    }


}