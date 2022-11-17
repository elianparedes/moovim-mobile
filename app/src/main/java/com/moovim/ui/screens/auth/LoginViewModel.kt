package com.moovim.ui.screens.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.UserRepository
import com.moovim.util.Response
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
            when (val response = repository.login(username, password)){
                is Response.Success -> {
                    state = state.copy(token = response.data, isLoggedIn = true)
                }
                is Response.Error -> {
                    return@launch
                }
            }

        }
    }

}