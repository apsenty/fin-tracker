package ru.apsenty.apiEngine

import io.restassured.response.Response

class RestResponseImpl<T> : IRestResponse<T> {
    private var data: T? = null

    constructor()
    constructor(t: Class<T>, response: Response?) {
        this.response = response
        try {
            this.data = t.getDeclaredConstructor().newInstance()
        } catch (e: Exception) {
            println(e.message)
            throw RuntimeException("There should be a default constructor in the Response POJO")
        }
    }

    override val body: T
        get() {
            try {
                data = response!!.body.`as`(data!!.javaClass) as T
            } catch (e: Exception) {
                println(e.message)
                this.exception = e
            }
            return data!!
        }

    override val content: String?
        get() = response!!.body.asString()

    override val contentPretty: String?
        get() = response!!.body.prettyPrint()

    override val statusCode: Int
        get() = response!!.statusCode

    override val statusDescription: String?
        get() = response!!.statusLine

    override var response: Response? = null
        private set

    override var exception: Exception? = null
        private set
}