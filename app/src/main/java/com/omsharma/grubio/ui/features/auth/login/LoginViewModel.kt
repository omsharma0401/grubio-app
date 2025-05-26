package com.omsharma.grubio.ui.features.auth.login

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.omsharma.grubio.data.FoodApi
import com.omsharma.grubio.data.auth.GoogleAuthUiProvider
import com.omsharma.grubio.data.model.LoginRequest
import com.omsharma.grubio.data.model.OAuthRequest
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

    val googleAuthUiProvider = GoogleAuthUiProvider()

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

    fun onGoogleLoginClick(context: Context) {
        viewModelScope.launch {
            _uiState.value = LoginEvent.Loading
            val response = googleAuthUiProvider.login(
                context,
                CredentialManager.create(context)
            )

            if (response!=null) {

                val request = OAuthRequest(
                    token = response.token,
                    provider = "google"
                )
                val res = foodApi.oAuth(request)
                if(res.token.isNotEmpty()) {
                    Log.d("LoginViewModel", "onGoogleLoginClick: ${res.token}")
                    _uiState.value = LoginEvent.Success
                    _navigationEvent.emit(LoginNavigationEvent.NavigateToHome)
                } else {
                    _uiState.value = LoginEvent.Error
                }

            } else {
                _uiState.value = LoginEvent.Error
            }
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