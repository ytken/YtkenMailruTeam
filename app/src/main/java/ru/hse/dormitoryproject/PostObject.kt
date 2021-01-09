package ru.hse.dormitoryproject

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.storage.StorageReference

@IgnoreExtraProperties
data class PostObject(
    val title: String? = "",
    val descriptor: String? = "",
    val content: String? = "",
    val dateOfPublish: String? = "",
    var isFavorite: Boolean? = false,
    val author: String? = "",
    var storageRef : String? = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "title" to title,
            "descriptor" to descriptor,
            "content" to content,
            "dateOfPublish" to dateOfPublish,
            "isFavorite" to isFavorite, // Почему-то он всегда придает постам изначальные значения вне зависимости от данных на сервере
            "author" to author,
            "storageRef" to storageRef
        )
    }
}