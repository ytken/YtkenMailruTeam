package ru.hse.dormitoryproject

import java.io.FileDescriptor

data class PostObject(
    val title : String,
    val descriptor: String,
    val content : String,
    val dateOfPublish: String,
    val isFavorite : Boolean
)
