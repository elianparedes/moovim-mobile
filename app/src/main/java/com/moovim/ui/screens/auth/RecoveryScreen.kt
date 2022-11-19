package com.moovim.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.moovim.R
import com.moovim.Screen

@Composable
fun RecoveryScreen() {
    Screen(stringResource(id = R.string.recover_password))
}