package ru.hse.dormitoryproject

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class PostObject(
    val title: String? = "",
    val descriptor: String? = "",
    val content: String? = "",
    val dateOfPublish: String? = "",
    val isFavorite: Boolean? = false
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "title" to title,
            "descriptor" to descriptor,
            "content" to content,
            "dateOfPublish" to dateOfPublish,
            "isFavorite" to isFavorite
        )
    }
}