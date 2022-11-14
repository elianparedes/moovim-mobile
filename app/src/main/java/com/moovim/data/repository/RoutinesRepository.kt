package com.moovim.data.repository

import android.util.Log
import com.moovim.data.remote.dto.*
import com.moovim.data.remote.dto.common.Api
import com.moovim.domain.model.Cycle
import com.moovim.domain.model.Routine
import com.moovim.domain.model.RoutineReview
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

    suspend fun getAllFavouriteRoutines(): List<Routine> {
        return api.getAllFavouriteRoutines().content.map { it.toRoutine() }
    }

    suspend fun addRoutineToFavourites(routineId: Int): Unit {
        return api.addRoutineToFavourites(routineId)
    }

    suspend fun deleteRoutineFromFavourites(routineId: Int): Unit {
        return api.deleteRoutineFromFavourites(routineId)
    }

    suspend fun getAllRoutineReviews(routineId: Int): List<RoutineReview> {
        return api.getAllRoutineReviews(routineId).content.map {it.toRoutineReview()}
    }

    suspend fun addRoutineReview(routineId: Int, score: Int, review: String){
        return api.addRoutineReview(routineId, NewRoutineReviewDto(score, review))
    }

}