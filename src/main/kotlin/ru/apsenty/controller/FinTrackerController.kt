package ru.apsenty.controller

import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.apsenty.dto.SpendingDto
import ru.apsenty.responses.CreateResponse
import ru.apsenty.responses.DeleteResponse
import ru.apsenty.responses.UpdateResponse
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
    fun create(@RequestBody dto: SpendingDto): CreateResponse {
        return finTrackerService.create(dto)
    }

    /**
     * Обновление записи расхода/дохода
     */
    @PatchMapping("/{id}")
    @Transactional
    fun update(@PathVariable id: Int, @RequestBody spendingUpdates: Map<String, Any>): UpdateResponse {
        return finTrackerService.update(id, spendingUpdates)

    }

    /**
     * Удаление записи расхода/дохода
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int): DeleteResponse {
        return finTrackerService.delete(id)
    }


}