package com.moovim.data.repository

import com.moovim.data.remote.dto.*
import com.moovim.data.remote.dto.common.Api
import com.moovim.domain.model.Cycle
import com.moovim.domain.model.Routine
import com.moovim.domain.model.RoutineDetails
import com.moovim.domain.model.RoutineReview
import com.moovim.util.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoutinesRepository @Inject constructor(
    private val api: Api
) {

    suspend fun getAllRoutines(query: String? = null, categoryId: Int? = null): Response<List<Routine>> {
        return try {
            val routines = api.getAllRoutines(search = query, categoryId = categoryId).content.map { it.toRoutine() }
            Response.Success(routines)
        } catch (e: Exception) {
            Response.Error("Error message")
        }
    }



    suspend fun getCurrentUserRoutines(): Response<List<Routine>> {
        return try {
            val currentUserRoutines =
                api.getCurrentUserRoutines().content.map { it.toUserRoutine() }
            Response.Success(currentUserRoutines)
        } catch (e: Exception) {
            Response.Error("Error message")
        }
    }

    suspend fun getRoutine(routineId: Int): Response<Routine> {
        return try {
            val routine = api.getRoutine(routineId).toRoutine()
            Response.Success(routine)
        } catch (e: Exception) {
            Response.Error("Error message")
        }
    }

    suspend fun getRoutineCycles(routineId: Int): Response<List<Cycle>> {
        return try {
            val cycles = api.getRoutineCycles(routineId).content.map { it.toCycle() }

            for (cycle in cycles)
                cycle.cycleExercises =
                    api.getCycleExercises(cycle.id).content.map { it.toCycleExercise() }

            Response.Success(cycles)
        } catch (e: Exception) {
            Response.Error("Error message")
        }

    }

    suspend fun getAllFavouriteRoutines(): Response<List<Routine>> {
        return try {
            val favouriteRoutines = api.getAllFavouriteRoutines().content.map { it.toRoutine() }
            Response.Success(favouriteRoutines)
        } catch (e: Exception) {
            Response.Error("Error message")
        }
    }

    suspend fun addRoutineToFavourites(routineId: Int): Response<Int> {
        return try {
            api.addRoutineToFavourites(routineId)
            Response.Success(routineId)
        } catch (e: Exception) {
            Response.Error("Error message")
        }
    }

    suspend fun deleteRoutineFromFavourites(routineId: Int): Response<Int> {
        return try {
            api.deleteRoutineFromFavourites(routineId)
            Response.Success(routineId)
        } catch (e: Exception) {
            Response.Error("Error message")
        }
    }

    suspend fun getAllRoutineReviews(routineId: Int): Response<List<RoutineReview>> {
        return try {
            val routineReviews =
                api.getAllRoutineReviews(routineId).content.map { it.toRoutineReview() }
            Response.Success(routineReviews)
        } catch (e: Exception) {
            Response.Error("Error message")
        }
    }

    suspend fun addRoutineReview(routineId: Int, score: Int, review: String): Response<Int> {
        return try {
            api.addRoutineReview(routineId, NewRoutineReviewDto(score, review))
            Response.Success(routineId)
        } catch (e: Exception) {
            Response.Error("Error message")
        }
    }

    suspend fun getRoutineDetails(routineId: Int): Response<RoutineDetails> {
        return try {
            val routine = api.getRoutine(routineId).toRoutine()
            val cycles = api.getRoutineCycles(routineId).content.map { it.toCycle() }

            for (cycle in cycles)
                cycle.cycleExercises =
                    api.getCycleExercises(cycle.id).content.map { it.toCycleExercise() }

            Response.Success(RoutineDetails(routine, cycles))
        } catch (e: Exception) {
            Response.Error("Error message")
        }
    }

}