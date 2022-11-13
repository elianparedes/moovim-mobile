package com.moovim.ui.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    var state by mutableStateOf(LoginState())

    fun onUsernameChange(user: String){
        state = state.copy(user = user);
    }

    fun onPasswordChange(password: String){
        state = state.copy(password = password);
    }

    fun login(username: String, password: String){
        viewModelScope.launch {
            val token = repository.login(username, password)
            state = state.copy(token = token, isLoggedIn = true)
        }
    }

}