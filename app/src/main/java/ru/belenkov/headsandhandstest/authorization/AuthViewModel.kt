package ru.belenkov.headsandhandstest.authorization

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.belenkov.headsandhandstest.model.dto.Weather
import ru.belenkov.headsandhandstest.model.dto.WeatherError

import ru.belenkov.headsandhandstest.model.repository.RestRepository
import ru.belenkov.headsandhandstest.util.EMAIL_REGEX
import ru.belenkov.headsandhandstest.util.PASSWORD_REGEX
import ru.belenkov.headsandhandstest.util.SingleLiveEvent
import timber.log.Timber
import java.lang.Exception

class AuthViewModel : ViewModel() {

    var emailValue: ObservableField<String> = ObservableField("")
    var passwordValue: ObservableField<String> = ObservableField("")

    var authIsValid: ObservableBoolean = ObservableBoolean(false)
    var loginButtonEnabled: ObservableBoolean = ObservableBoolean(true)

    var emailIsValid: MutableLiveData<Boolean> = MutableLiveData()
    var passwordIsValid: MutableLiveData<Boolean> = MutableLiveData()

    var weatherData: SingleLiveEvent<Weather> = SingleLiveEvent()
    var weatherError: SingleLiveEvent<WeatherError> = SingleLiveEvent()

    init {
        passwordIsValid.value = false
        emailIsValid.value = false
    }

    fun validateEmail(text: CharSequence, start: Int, befor: Int, count: Int) {
        emailIsValid.value = EMAIL_REGEX.toRegex().matches(text.toString())
        authIsValid.set(emailIsValid.value!! && passwordIsValid.value!!)
    }

    fun validatePassword(text: CharSequence, start: Int, befor: Int, count: Int) {
        passwordIsValid.value = PASSWORD_REGEX.toRegex().matches(text.toString())
        authIsValid.set(emailIsValid.value!! && passwordIsValid.value!!)
    }

    fun login() {

        GlobalScope.launch {
            try {
                loginButtonEnabled.set(false)
                val weather = RestRepository.getWeather()
                weatherData.value = weather
                loginButtonEnabled.set(true)
            } catch (e: Exception) {
                weatherError.value = WeatherError(e.localizedMessage)
                Timber.e("Something wrong ${e.message}")
                loginButtonEnabled.set(true)
            }
        }
    }
}