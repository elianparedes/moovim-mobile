package com.moovim.data.repository

import com.moovim.data.remote.dto.common.Api
import com.moovim.data.remote.dto.toRoutine
import com.moovim.domain.model.Routine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoutinesRepository @Inject constructor(
    private val api: Api
) {

    suspend fun getAllRoutines(): List<Routine> {
        return api.getAllRoutines().content.map { it.toRoutine() }
    }

    suspend fun getCurrentUserRoutines(): List<Routine> {
        return api.getCurrentUserRoutines().content.map { it.toRoutine() }
    }

}