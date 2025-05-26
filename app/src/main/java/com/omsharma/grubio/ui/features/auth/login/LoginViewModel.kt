package com.omsharma.grubio.ui.features.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omsharma.grubio.data.FoodApi
import com.omsharma.grubio.data.model.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val foodApi: FoodApi
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginEvent>(LoginEvent.Nothing)
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<LoginNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onLoginClick() {
        viewModelScope.launch {
            _uiState.value = LoginEvent.Loading

            try {
                val response = foodApi.login(
                    LoginRequest(
                        email = _email.value,
                        password = _password.value
                    )
                )
                if (response.token.isNotEmpty()) {
                    _uiState.value = LoginEvent.Success
                    _navigationEvent.emit(LoginNavigationEvent.NavigateToHome)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = LoginEvent.Error
            }
        }

    }

    fun onSignupClick() {
        viewModelScope.launch {
            _navigationEvent.emit(LoginNavigationEvent.NavigateToSignup)
        }
    }

    sealed class LoginNavigationEvent {
        object NavigateToSignup : LoginNavigationEvent()
        object NavigateToHome : LoginNavigationEvent()
    }

    sealed class LoginEvent {
        object Nothing : LoginEvent()
        object Success : LoginEvent()
        object Error : LoginEvent()
        object Loading : LoginEvent()
    }
}