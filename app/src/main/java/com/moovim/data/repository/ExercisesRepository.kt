package com.moovim.data.repository

import com.moovim.data.remote.dto.common.Api
import com.moovim.data.remote.dto.toExercise
import com.moovim.data.remote.dto.toExerciseImage
import com.moovim.domain.model.Exercise
import com.moovim.domain.model.ExerciseDetails
import com.moovim.domain.model.ExerciseImage
import com.moovim.util.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExercisesRepository @Inject constructor(
    private val api: Api
) {

    suspend fun getAllExercises(query: String? = null): Result<List<Exercise>> {
        return try {
            val exercises = api.getAllExercises(search = query).content.map { it.toExercise() }
            Result.Success(exercises)
        } catch (e: Exception) {
            Result.Error("Error message",0);
        }
    }

    suspend fun getExercise(exerciseId: Int): Result<Exercise> {
        return try {
            val exercise = api.getExercise(exerciseId).toExercise()
            Result.Success(exercise)
        } catch (e: Exception) {
            Result.Error("Error message",0)
        }
    }

    suspend fun getExerciseImages(exerciseId: Int): Result<List<ExerciseImage>> {
        return try {
            val exerciseImages = api.getExerciseImages(exerciseId).content.map { it.toExerciseImage() }
            Result.Success(exerciseImages)
        } catch (e: Exception) {
            Result.Error("Error message",0)
        }
    }

    suspend fun getExerciseDetails(exerciseId: Int): Result<ExerciseDetails> {
        return try {
            val exercise = api.getExercise(exerciseId).toExercise()
            val images = api.getExerciseImages(exerciseId).content.map { it.toExerciseImage() }
            Result.Success(ExerciseDetails(exercise, images))
        } catch (e: Exception){
            Result.Error("Error message",0)
        }

    }

}