package ru.hse.dormitoryproject.Utils

import android.net.Uri
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
data class UserObject(
    val fullName: String?,
    val email: String?,
    val phoneNumber: String?,
    val photoProfile: String?,
    val favoriteIds: ArrayList<String>?,
    val postIds: ArrayList<String>?,
    val workIds: ArrayList<String>?


) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "full_name" to fullName,
            "email" to email,
            "phone_number" to phoneNumber,
            "photo_profile" to photoProfile,
            "favoriteIds" to favoriteIds,
            "postIds" to postIds,
        "workIds" to workIds
        )
    }
}