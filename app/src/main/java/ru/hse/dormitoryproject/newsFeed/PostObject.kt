package ru.hse.dormitoryproject.newsFeed

import java.io.FileDescriptor

data class PostObject(
    /**
     * Заголовок новости
     */
    val title : String,

    /**
     * ?
     */
    val descriptor: String,

    /**
     * Описание новости. Основной текст.
     */
    val content : String,

    /**
     * Дата публикации.
     */
    val dateOfPublish: String,

    /**
     * Автор публикации.
     */
    val author : String,

    /**
     * Находится ли новость в избранном.
     */
    var isFavorite : Boolean
)
