package ru.belenkov.headsandhandstest.model.repository

import ru.belenkov.headsandhandstest.model.dto.Weather
import ru.belenkov.headsandhandstest.model.api.RestApi
import ru.belenkov.headsandhandstest.model.api.RestClient

object RestRepository {
    private val restClient: RestApi
        get() = RestClient.client.create(RestApi::class.java)

    suspend fun getWeather(): Weather? {
        val weatherRequest = restClient.getWeather()
        return weatherRequest.await().body()
    }

}