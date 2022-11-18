package com.moovim.ui.screens.main.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.UserRepository
import com.moovim.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    ): ViewModel() {

    var state by mutableStateOf(ProfileState())

    init {
        getCurrentUser()
    }

    fun getCurrentUser(){
        viewModelScope.launch{
            when (val result = userRepository.getCurrentUser()) {
                is Result.Success -> {
                    val data = result.data
                    if (data != null) {
                        Log.d("profile", "getCurrentUser: entre")
                        state = state.copy(id = data.id, email = data.email, username = data.username,
                            firstName = data.firstName, lastName = data.lastName,
                            avatarUrl = data.avatarUrl, gender = data.gender,
                            isVerified = data.isVerified
                        )
                    }
                }
                is Result.Error -> {
                }
            }
        }
    }

    fun logout(){
        viewModelScope.launch{
            userRepository.logout()
        }
    }
    }