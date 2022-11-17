package com.moovim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.moovim.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    var isLoggedIn by mutableStateOf(false)

    fun isUserLoggedIn(): Boolean {
        return userRepository.isUserLoggedIn()
    }


}