package ru.apsenty

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.apsenty.models.BaseResponse

@SpringBootTest
@AutoConfigureMockMvc
class FinTrackerMockMethodsTests {
    companion object {
        private var objectMapper = jacksonObjectMapper()
    }

    @Test
    @DisplayName("Запрос списка записей")
    fun getAllSpending(@Autowired mvc: MockMvc) {
        mvc.perform(MockMvcRequestBuilders.get("/fintracker"))
            .andExpectAll(
                MockMvcResultMatchers.status().isOk,
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)
            )
    }


    @Test
    @DisplayName("Запрос записи по её id")
    fun getSpendingById(@Autowired mvc: MockMvc) {
        mvc.perform(MockMvcRequestBuilders.get("/fintracker/1"))
            .andExpectAll(
                MockMvcResultMatchers.status().isOk,
                MockMvcResultMatchers.content().json("""
					{
						"id":1,
						"amount":100.0,
						"type":"Расход",
						"comment":"Семь чебуреков на вокзале"
					}
				""".trimIndent())
            )
    }

    @Test
    @DisplayName("Запрос записи по её id. Ошибка при несуществующем id")
    fun getSpendingByIdErrorNotFound(@Autowired mvc: MockMvc) {
        mvc.perform(MockMvcRequestBuilders.get("/fintracker/0"))
            .andExpectAll(
                MockMvcResultMatchers.status().isNotFound,
                MockMvcResultMatchers.content().json("""
					{
						"errorCode":"spendingNotFound",
						"description":"Запись с id: 0 не найдена."
					}
				""".trimIndent())
            )
    }

    @Test
    @DisplayName("Создание записи")
    fun createSpending(@Autowired mvc: MockMvc) {
        val response = mvc.perform(
            MockMvcRequestBuilders.post("/fintracker")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
				{
					"amount":80.0,
					"type":"Расход",
					"comment":"Пирожок"
				}
			""".trimIndent())
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isCreated,
                MockMvcResultMatchers.jsonPath("code").value("success"),
                MockMvcResultMatchers.jsonPath("id").isNumber,
                MockMvcResultMatchers.jsonPath("message").value("Запись успешно создана.")
            )
            .andReturn().response.contentAsString
        val id = objectMapper.readValue<BaseResponse>(response).id

        mvc.perform(MockMvcRequestBuilders.delete("/fintracker/$id"))
    }

    @Test
    @DisplayName("Обновление записи. Обновление одного параметра")
    fun updateSpending(@Autowired mvc: MockMvc) {
        val response = mvc.perform(
            MockMvcRequestBuilders.post("/fintracker")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
				{
					"amount":80.0,
					"type":"Расход",
					"comment":"Пирожок"
				}
			""".trimIndent())
        ).andReturn().response.contentAsString
        val id = objectMapper.readValue<BaseResponse>(response).id

        mvc.perform(
            MockMvcRequestBuilders.patch("/fintracker/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
				{
					"comment":"Или не пирожок"
				}
			""".trimIndent())
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk,
                MockMvcResultMatchers.jsonPath("code").value("success"),
                MockMvcResultMatchers.jsonPath("id").value(id),
                MockMvcResultMatchers.jsonPath("message").value("Запись успешно обновлена.")
            )

        mvc.perform(MockMvcRequestBuilders.delete("/fintracker/$id"))
    }

    @Test
    @DisplayName("Обновление записи. Ошибка при несуществующем id")
    fun updateSpendingErrorNotFound(@Autowired mvc: MockMvc) {
        mvc.perform(
            MockMvcRequestBuilders.patch("/fintracker/0")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
				{
					"comment":"Или не пирожок"
				}
			""".trimIndent())
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isNotFound,
                MockMvcResultMatchers.content().json("""
					{
						"errorCode":"spendingNotFound",
						"description":"Запись с id: 0 не найдена."
					}
				""".trimIndent())
            )
    }

    @Test
    @DisplayName("Удаление записи")
    fun deleteSpending(@Autowired mvc: MockMvc) {
        val response = mvc.perform(
            MockMvcRequestBuilders.post("/fintracker")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
				{
					"amount":80.0,
					"type":"Расход",
					"comment":"Пирожок"
				}
			""".trimIndent())
        ).andReturn().response.contentAsString
        val id = objectMapper.readValue<BaseResponse>(response).id

        mvc.perform(MockMvcRequestBuilders.delete("/fintracker/$id"))
            .andExpectAll(
                MockMvcResultMatchers.status().isOk,
                MockMvcResultMatchers.jsonPath("code").value("success"),
                MockMvcResultMatchers.jsonPath("id").value(id),
                MockMvcResultMatchers.jsonPath("message").value("Запись успешно удалена.")
            )
    }

    @Test
    @DisplayName("Удаление записи. Ошибка при несуществующем id")
    fun deleteSpendingErrorNotFound(@Autowired mvc: MockMvc) {
        mvc.perform(MockMvcRequestBuilders.delete("/fintracker/0"))
            .andExpectAll(
                MockMvcResultMatchers.status().isNotFound,
                MockMvcResultMatchers.content().json("""
					{
						"errorCode":"spendingNotFound",
						"description":"Запись с id: 0 не найдена."
					}
				""".trimIndent())
            )
    }
}