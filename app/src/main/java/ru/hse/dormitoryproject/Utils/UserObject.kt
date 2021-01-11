package ru.hse.dormitoryproject.Utils

import android.net.Uri
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
data class UserObject(
    val name: String? = "",
    val surname: String? = "",
    val email: String? = "",
    val phoneNumber: String? = "",
    val photoProfile: String? = "",
    val vk: String? = "",
    val countCoins: Int? = 0,
    val favoriteIds: ArrayList<String>? = arrayListOf(),
    val postIds: ArrayList<String>? = arrayListOf(),
    val workIds: ArrayList<String>? = arrayListOf()


) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "surname" to surname,
            "vk" to vk,
            "email" to email,
            "phoneNumber" to phoneNumber,
            "photoProfile" to photoProfile,
            "countCoins" to countCoins,
            "favoriteIds" to favoriteIds,
            "postIds" to postIds,
            "workIds" to workIds
        )
    }
}