package com.moovim.data.repository

import android.util.Log
import com.moovim.data.remote.dto.common.Api
import com.moovim.data.remote.dto.toCycle
import com.moovim.data.remote.dto.toCycleExercise
import com.moovim.data.remote.dto.toExercise
import com.moovim.data.remote.dto.toRoutine
import com.moovim.domain.model.Cycle
import com.moovim.domain.model.Routine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoutinesRepository @Inject constructor(
    private val api: Api
) {

    suspend fun getAllRoutines(query: String? = null): List<Routine> {
        return api.getAllRoutines(search = query).content.map { it.toRoutine() }
    }

    suspend fun getCurrentUserRoutines(): List<Routine> {
        return api.getCurrentUserRoutines().content.map { it.toRoutine() }
    }

    suspend fun getRoutine(routineId: Int): Routine {
        return api.getRoutine(routineId).toRoutine()
    }

    suspend fun getRoutineCycles(routineId: Int): List<Cycle> {
        val cycles = api.getRoutineCycles(routineId).content.map { it.toCycle() }

        for (cycle in cycles)
            cycle.cycleExercises = api.getCycleExercises(cycle.id).content.map { it.toCycleExercise() }

        return cycles
    }

}