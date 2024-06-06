package ru.apsenty

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.boot.test.context.SpringBootTest
import ru.apsenty.models.ErrorResponse
import ru.apsenty.models.RequestBody
import ru.apsenty.services.FinTrackerCoreService
import kotlin.test.assertEquals

@SpringBootTest
class FinTrackerApplicationTests {
	companion object {
		private val objectMapper = jacksonObjectMapper()
	}

	@Test
	@DisplayName("Запрос списка записей")
	fun getAll() {
		val response = FinTrackerCoreService.getAll()

		assertAll(
			{ assertEquals(200, response.statusCode) },
			{ assertThat(response.body).isNotNull },
			{ assertThat(response.body).isNotEmpty }
		)

	}

	@Test
	@DisplayName("Запрос записи по её id")
	fun getById() {
		val response = FinTrackerCoreService.getRecordById(1)

		assertAll(
			{ assertEquals(200, response.statusCode) },
			{ assertEquals(1, response.body.id) },
			{ assertEquals(100.0, response.body.amount) },
			{ assertEquals("Расход", response.body.type) },
			{ assertEquals("Семь чебуреков на вокзале", response.body.comment) }
		)
	}

	@Test
	@DisplayName("Запрос записи по её id. Ошибка при несуществующем id")
	fun getByIdErrorNotExistingId() {
		val id = 0
		val response = FinTrackerCoreService.getRecordById(id)
		val errorResponse: ErrorResponse = objectMapper.readValue(response.content!!)

		assertAll(
			{ assertEquals(404, response.statusCode) },
			{ assertEquals("spendingNotFound", errorResponse.errorCode) },
			{ assertEquals("Запись с id: $id не найдена.", errorResponse.description) }
		)
	}

	@Test
	@DisplayName("Создание записи")
	fun addRecord() {
		val requestBody = RequestBody(
			amount = 80.0,
			type = "Расход",
			comment = "Пирожок"
		)

		val response = FinTrackerCoreService.createRecord(requestBody)

		assertAll(
			{ assertEquals(201, response.statusCode) },
			{ assertEquals("success", response.body.code) },
			{ assertEquals("Запись успешно создана.", response.body.message) },
			{ assertThat(response.body.id).isNotNull() }
		)

		FinTrackerCoreService.deleteRecord(response.body.id!!)
	}

	@Test
	@DisplayName("Обновление записи")
	fun updateRecord() {
		val createRequestBody = RequestBody(
			amount = 80.0,
			type = "Расход",
			comment = "Пирожок"
		)
		val id = FinTrackerCoreService.createRecord(createRequestBody).body.id
		val updateRequestBody = RequestBody(
			comment = "Или не пирожок"
		)

		val response = FinTrackerCoreService.updateRecord(updateRequestBody, id!!)

		assertAll(
			{ assertEquals(200, response.statusCode) },
			{ assertEquals("success", response.body.code) },
			{ assertEquals("Запись успешно обновлена.", response.body.message) },
			{ assertEquals(id, response.body.id) }
		)

		FinTrackerCoreService.deleteRecord(response.body.id!!)
	}

	@Test
	@DisplayName("Обновление записи. Ошибка при несуществующем id")
	fun updateRecordErrorNoyExistingId() {
		val id = 0
		val updateRequestBody = RequestBody(
			comment = "Или не пирожок"
		)
		val response = FinTrackerCoreService.updateRecord(updateRequestBody, id)
		val errorResponse: ErrorResponse = objectMapper.readValue(response.content!!)

		assertAll(
			{ assertEquals(404, response.statusCode) },
			{ assertEquals("spendingNotFound", errorResponse.errorCode) },
			{ assertEquals("Запись с id: $id не найдена.", errorResponse.description) }
		)
	}

	@Test
	@DisplayName("Удаление записи")
	fun deleteRecord() {
		val requestBody = RequestBody(
			amount = 85.0,
			type = "Расход",
			comment = "Булочка"
		)
		val id = FinTrackerCoreService.createRecord(requestBody).body.id
		val response = FinTrackerCoreService.deleteRecord(id!!)

		assertAll(
			{ assertEquals(200, response.statusCode) },
			{ assertEquals("success", response.body.code) },
			{ assertEquals("Запись успешно удалена.", response.body.message) },
			{ assertEquals(id, response.body.id) }
		)
	}

	@Test
	@DisplayName("Удаление записи. Ошибка при несуществующем id")
	fun deleteRecordErrorWithExistingId() {
		val id = 0
		val response = FinTrackerCoreService.deleteRecord(id)
		val errorResponse: ErrorResponse = objectMapper.readValue(response.content!!)

		assertAll(
			{ assertEquals(404, response.statusCode) },
			{ assertEquals("spendingNotFound", errorResponse.errorCode) },
			{ assertEquals("Запись с id: $id не найдена.", errorResponse.description) }
		)
	}

}
