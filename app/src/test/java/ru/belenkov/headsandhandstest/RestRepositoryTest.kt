package ru.belenkov.headsandhandstest

import kotlinx.coroutines.runBlocking

import org.junit.Test

import ru.belenkov.headsandhandstest.model.repository.RestRepository

class RestRepositoryTest {

    @Test
    fun getWeatherTest() {
        val weather = runBlocking {
            RestRepository.getWeather()
        }!!
    }
}