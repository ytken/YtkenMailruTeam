package ru.hse.dormitoryproject.tasksFeeds

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class TaskObject(
    val description: String? = "",
    val reward: Int = 10,
    var author: String = "",
    val status: Int = 2,
    val deadline: String = "",
    val title: String = "",
    val employee: String? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "description" to description,
            "reward" to reward,
            "author" to author,
            "status" to status,
            "title" to title,
            "deadline" to deadline,
            "employee" to employee
        )
    }

    fun getStatus() = Status.getByValue(status)

    override fun equals(other: Any?): Boolean {
        if (other is TaskObject) {
            return other.author.equals(author) && other.title.equals(title) && other.reward.equals(
                reward
            )
        }
        return super.equals(other)
    }

    enum class Status {
        READY, IN_PROGRESS, NOT_SELECTED;

        companion object {
            fun getByValue(value: Int) = values().firstOrNull { it.ordinal == value }
        }
    }
}