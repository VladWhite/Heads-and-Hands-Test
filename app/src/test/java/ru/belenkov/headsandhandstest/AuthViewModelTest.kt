package ru.belenkov.headsandhandstest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.belenkov.headsandhandstest.authorization.AuthViewModel


class AuthViewModelTest {

    @get:Rule
    val testRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private var viewModel: AuthViewModel? = null

    @Before
    fun setUp() {
        viewModel = AuthViewModel()
    }

    @Test
    fun passwordValidateTest() {
        viewModel?.validatePassword("Aa123456", start = 0, befor = 0, count = 0)
        assert(viewModel?.passwordIsValid?.value!!)
    }

    @Test
    fun emailValidateTest() {
        viewModel?.validateEmail("email@gmail.com", start = 0, befor = 0, count = 0)
        assert(viewModel?.emailIsValid?.value!!)
    }

    @Test
    fun authIsValidTest() {
        viewModel?.validatePassword("Aa123456", start = 0, befor = 0, count = 0)
        viewModel?.validateEmail("email@gmail.com", start = 0, befor = 0, count = 0)
        assert(viewModel?.authIsValid?.get()!!)
    }
}