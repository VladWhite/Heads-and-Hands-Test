package ru.belenkov.headsandhandstest.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_auth.*
import ru.belenkov.headsandhandstest.R
import ru.belenkov.headsandhandstest.databinding.FragmentAuthBinding
import ru.belenkov.headsandhandstest.util.createSnackBar

class AuthFragment : DialogFragment() {

    private var viewModel: AuthViewModel? = null

    private lateinit var passwordSnack: Snackbar

    private lateinit var weatherSnack: Snackbar

    private lateinit var weatherErrorSnack: Snackbar

    private fun getBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<FragmentAuthBinding>(inflater, R.layout.fragment_auth, container, false)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding?.vm = viewModel

        return binding.root
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return getBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOservable()
        initListener()
        initSnacks(view)
    }

    private fun initSnacks(view: View) {
        passwordSnack = createSnackBar(view = view, text = getString(R.string.dialog_password_descr))
        weatherErrorSnack = createSnackBar(view = view)
        weatherSnack = createSnackBar(view = view)
    }

    private fun initOservable() {
        observeWeather()
        observeWeatherError()
        observeEmail()
        observePassword()
    }

    private fun initListener() {
        initPasswordTipListener()
    }

    private fun observeWeather() {
        viewModel?.weatherData?.observe(this, Observer {
            val tempCel = it.main?.temp?.div(32)
            weatherSnack.setText("${getString(R.string.weather_title)} $tempCel \u2103")
            weatherSnack.show()
        })
    }

    private fun observeWeatherError() {
        viewModel?.weatherError?.observe(this, Observer {
            weatherErrorSnack.setText("${getString(R.string.error_get_data)} ${it.errorMessage}")
            weatherErrorSnack.show()
        })
    }


    private fun observeEmail() {
        viewModel?.emailIsValid?.observe(this, Observer {
            if (emailField.isFocused) {
                if (!it) {
                    emailInput.error = getString(R.string.error_email)
                } else {
                    emailInput.error = null
                }
            }
        })
    }

    private fun observePassword() {
        viewModel?.passwordIsValid?.observe(this, Observer {
            if (passwordField.isFocused) {
                if (!it) {
                    passwordInput.error = getString(R.string.error_password)
                } else {
                    passwordInput.error = null
                }
            }
        })
    }

    private fun initPasswordTipListener() {
        passwordField.setOnTouchListener { v, event ->
            val drawableRight = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (passwordField.right - passwordField.compoundDrawables[drawableRight].bounds.width())) {
                    passwordSnack.show()
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
    }
}
