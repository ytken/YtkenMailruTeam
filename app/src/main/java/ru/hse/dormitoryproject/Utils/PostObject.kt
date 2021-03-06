package ru.hse.dormitoryproject.Utils

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.storage.StorageReference

@IgnoreExtraProperties
data class PostObject(
    var postID:String?="",
    val title: String? = "",
    val descriptor: String? = "",
    val content: String? = "",
    val dateOfPublish: String? = "",
    var isFavorite: Boolean? = false,
    var author: String? = "",
    var storageRef : String? = ""

) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "postID" to postID,
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