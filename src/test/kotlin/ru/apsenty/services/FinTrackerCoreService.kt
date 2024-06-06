package ru.apsenty.services

import io.restassured.RestAssured
import io.restassured.http.ContentType
import ru.apsenty.apiEngine.IRestResponse
import ru.apsenty.apiEngine.RestResponseImpl
import ru.apsenty.models.BaseResponse
import ru.apsenty.models.GetAllResponse
import ru.apsenty.models.GetByIdResponse
import ru.apsenty.models.RequestBody

object FinTrackerCoreService {
    private const val BASE_URI = "http://127.0.0.1:8080/fintracker"

    fun getAll(): IRestResponse<GetAllResponse> {
        val response = RestAssured.given().log().all()
            .get(BASE_URI)

        return RestResponseImpl(GetAllResponse::class.java, response)
    }

    fun getRecordById(id: Int?): IRestResponse<GetByIdResponse> {
        val response = RestAssured.given().log().all()
            .get("$BASE_URI/$id")

        return RestResponseImpl(GetByIdResponse::class.java, response)
    }

    fun createRecord(requestBody: RequestBody): IRestResponse<BaseResponse> {
        val response = RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post(BASE_URI)

        return RestResponseImpl(BaseResponse::class.java, response)
    }

    fun updateRecord(requestBody: RequestBody, spendingId: Int): IRestResponse<BaseResponse> {
        val response = RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .patch("$BASE_URI/$spendingId")

        return RestResponseImpl(BaseResponse::class.java, response)
    }

    fun deleteRecord(spendingId: Int): IRestResponse<BaseResponse> {
        val response = RestAssured.given().log().all()
            .delete("$BASE_URI/$spendingId")

        return RestResponseImpl(BaseResponse::class.java, response)
    }
}