package ru.belenkov.headsandhandstest.model.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Weather(
    val main: MainData? = null,
    val name: String? = "Yekaterinburg"
) : Serializable

data class MainData(
    val temp: Double = 0.0,
    val pressure: Int = 0,
    val humidity: Int = 0,
    @SerializedName("temp_min")
    val tempMin: Double = 0.0,
    @SerializedName("temp_max")
    val tempMax: Double = 0.0
) : Serializable
