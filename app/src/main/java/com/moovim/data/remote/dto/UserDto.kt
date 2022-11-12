package com.moovim.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.moovim.domain.model.Routine
import com.moovim.domain.model.User

data class UserDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("avatarUrl")
    val avatarUrl: String,
    @SerializedName("birthdate")
    val birthdate: Long,
    @SerializedName("date")
    val date: Long,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("lastActivity")
    val lastActivity: Long,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("metadata")
    val metadata: Any,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("verified")
    val verified: Boolean
)

fun UserDto.toUser(): User {
    return User(
        id = id,
        username = username,
        firstName = firstName,
        lastName = lastName,
        avatarUrl = avatarUrl,
        gender = gender,
        isVerified = verified
    )
}