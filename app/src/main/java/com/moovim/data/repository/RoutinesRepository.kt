package com.moovim.data.repository

import com.moovim.data.remote.RoutinesApi
import com.moovim.data.remote.dto.ResponseDto
import com.moovim.data.remote.dto.RoutineDto
import com.moovim.data.remote.dto.toRoutine
import com.moovim.domain.model.Routine
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoutinesRepository @Inject constructor(
    private val api: RoutinesApi
) {

    suspend fun getAllRoutines(): List<Routine> {
        return api.getAllRoutines().content.map { it.toRoutine() }
    }

}