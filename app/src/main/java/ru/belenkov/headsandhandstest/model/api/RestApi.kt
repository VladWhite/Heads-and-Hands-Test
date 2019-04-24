package ru.belenkov.headsandhandstest.model.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.belenkov.headsandhandstest.model.dto.Weather
import ru.belenkov.headsandhandstest.util.DEFAULT_CITY
import ru.belenkov.headsandhandstest.util.WEATHER_KEY

interface RestApi {

    @GET("/data/2.5/weather")
    fun getWeather(
        @Query("q") cityName: String = DEFAULT_CITY,
        @Query("appid") key: String = WEATHER_KEY
    ): Deferred<Response<Weather>>
}