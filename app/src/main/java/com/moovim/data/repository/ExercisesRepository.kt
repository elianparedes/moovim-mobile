package com.moovim.data.repository

import com.moovim.data.remote.dto.common.Api
import com.moovim.data.remote.dto.toExercise
import com.moovim.data.remote.dto.toRoutine
import com.moovim.domain.model.Exercise
import com.moovim.domain.model.Routine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExercisesRepository @Inject constructor(
    private val api: Api
) {

    suspend fun getAllExercises(query: String? = null): List<Exercise> {
        return api.getAllExercises(search = query).content.map { it.toExercise() }
    }

}