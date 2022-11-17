package com.moovim.data.repository

import com.moovim.data.remote.dto.common.Api
import com.moovim.data.remote.dto.toExercise
import com.moovim.data.remote.dto.toExerciseImage
import com.moovim.domain.model.Exercise
import com.moovim.domain.model.ExerciseDetails
import com.moovim.domain.model.ExerciseImage
import com.moovim.util.Response
import kotlinx.coroutines.async
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExercisesRepository @Inject constructor(
    private val api: Api
) {

    suspend fun getAllExercises(query: String? = null): Response<List<Exercise>> {
        return try {
            val exercises = api.getAllExercises(search = query).content.map { it.toExercise() }
            Response.Success(exercises)
        } catch (e: Exception) {
            Response.Error("Error message");
        }
    }

    suspend fun getExercise(exerciseId: Int): Response<Exercise> {
        return try {
            val exercise = api.getExercise(exerciseId).toExercise()
            Response.Success(exercise)
        } catch (e: Exception) {
            Response.Error("Error message")
        }
    }

    suspend fun getExerciseImages(exerciseId: Int): Response<List<ExerciseImage>> {
        return try {
            val exerciseImages = api.getExerciseImages(exerciseId).content.map { it.toExerciseImage() }
            Response.Success(exerciseImages)
        } catch (e: Exception) {
            Response.Error("Error message")
        }
    }

    suspend fun getExerciseDetails(exerciseId: Int): Response<ExerciseDetails> {
        return try {
            val exercise = api.getExercise(exerciseId).toExercise()
            val images = api.getExerciseImages(exerciseId).content.map { it.toExerciseImage() }
            Response.Success(ExerciseDetails(exercise, images))
        } catch (e: Exception){
            Response.Error("Error message")
        }

    }

}