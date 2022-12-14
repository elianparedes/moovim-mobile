package com.moovim.ui.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.R
import com.moovim.data.repository.UserRepository
import com.moovim.util.Result
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
                is Result.Success -> {
                    state = state.copy(token = response.data, isLoggedIn = true)
                }
                is Result.Error -> {
                    if (response.code == 4) {
                        state = state.copy(errorMessageId = R.string.user_pass_error)
                    }
                    else {
                        state = state.copy(errorMessageId = R.string.no_connection)
                    }
                    state = state.copy(isError = true)
                    return@launch
                }
            }

        }
    }

}