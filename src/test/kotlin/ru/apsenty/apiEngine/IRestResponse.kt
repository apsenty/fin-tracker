package ru.apsenty.apiEngine

import io.restassured.response.Response

interface IRestResponse<T> {
    val body: T
    val content: String?
    val contentPretty: String?
    val statusCode: Int
    val statusDescription: String?
    val response: Response?
    val exception: Exception?
}