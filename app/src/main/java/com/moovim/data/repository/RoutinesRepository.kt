package com.moovim.data.repository

import com.moovim.data.remote.dto.*
import com.moovim.data.remote.dto.common.Api
import com.moovim.domain.model.Cycle
import com.moovim.domain.model.Routine
import com.moovim.domain.model.RoutineDetails
import com.moovim.domain.model.RoutineReview
import com.moovim.util.Result
import com.moovim.util.handleApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoutinesRepository @Inject constructor(
    private val api: Api
) {

    suspend fun getAllRoutines(query: String? = null, categoryId: Int? = null): Result<List<Routine>> {
        return handleApiResponse({
            api.getAllRoutines(search = query, categoryId = categoryId)
        }, { data -> data.content.map { it.toRoutine() } })
    }


    suspend fun getCurrentUserRoutines(): Result<List<Routine>> {
        return try {
            val currentUserRoutines =
                api.getCurrentUserRoutines().content.map { it.toUserRoutine() }
            Result.Success(currentUserRoutines)
        } catch (e: Exception) {
            Result.Error("Error message",0)
        }
    }

    suspend fun getRoutine(routineId: Int): Result<Routine> {
        return try {
            val routine = api.getRoutine(routineId).toRoutine()
            Result.Success(routine)
        } catch (e: Exception) {
            Result.Error("Error message",0)
        }
    }

    suspend fun getRoutineCycles(routineId: Int): Result<List<Cycle>> {
        return try {
            val cycles = api.getRoutineCycles(routineId).content.map { it.toCycle() }

            for (cycle in cycles)
                cycle.cycleExercises =
                    api.getCycleExercises(cycle.id).content.map { it.toCycleExercise() }

            Result.Success(cycles)
        } catch (e: Exception) {
            Result.Error("Error message",0)
        }

    }

    suspend fun getAllFavouriteRoutines(): Result<List<Routine>> {
        return try {
            val favouriteRoutines = api.getAllFavouriteRoutines().content.map { it.toRoutine() }
            Result.Success(favouriteRoutines)
        } catch (e: Exception) {
            Result.Error("Error message",0)
        }
    }

    suspend fun addRoutineToFavourites(routineId: Int): Result<Int> {
        return handleApiResponse ({
            api.addRoutineToFavourites(routineId)
        }, { it })
    }

    suspend fun deleteRoutineFromFavourites(routineId: Int): Result<Int> {
        return handleApiResponse ({
            api.deleteRoutineFromFavourites(routineId)
        }, { it })
    }

    suspend fun getAllRoutineReviews(routineId: Int): Result<List<RoutineReview>> {
        return try {
            val routineReviews =
                api.getAllRoutineReviews(routineId).content.map { it.toRoutineReview() }
            Result.Success(routineReviews)
        } catch (e: Exception) {
            Result.Error("Error message",0)
        }
    }

    suspend fun addRoutineReview(routineId: Int, score: Int, review: String): Result<Int> {
        return handleApiResponse ({
            api.addRoutineReview(routineId, NewRoutineReviewDto(score, review))
        }, { it })
    }

    suspend fun getRoutineDetails(routineId: Int): Result<RoutineDetails> {
        return try {
            val routine = api.getRoutine(routineId).toRoutine()
            val cycles = api.getRoutineCycles(routineId).content.map { it.toCycle() }

            for (cycle in cycles)
                cycle.cycleExercises =
                    api.getCycleExercises(cycle.id).content.map { it.toCycleExercise() }

            Result.Success(RoutineDetails(routine, cycles))
        } catch (e: Exception) {
            Result.Error("Error message",0)
        }
    }

}