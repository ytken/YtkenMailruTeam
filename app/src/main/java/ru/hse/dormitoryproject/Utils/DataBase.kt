package ru.hse.dormitoryproject.Utils

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import ru.hse.dormitoryproject.tasksFeeds.TaskObject
import java.util.*
import kotlin.collections.ArrayList


class DataBase() {

    companion object {
        // Link to dataBase
        private val db = Firebase.firestore

        // Current user
        private val user = Firebase.auth.currentUser

        private const val COLLECTION_FEEDS = "PageWork"
        private const val COLLECTION_TASKS = "Tasks"
        private const val COLLECTION_USERS = "users"

        private val storage = FirebaseStorage.getInstance()
        private val storageRef = storage.reference
        private const val TOAST_CREATE_POST_SUCCESS = "Post was created successfully!"
        private const val TOAST_CREATE_POST_FAIL = "Post was created Failure!"
        private const val TOAST_CREATE_TASK_SUCCESS = "Task was created successfully!"
        private const val TOAST_CREATE_TASK_FAIL = "Task was created Failure!"
        private const val TOAST_MONEY_SUCCESS = "Task was taken successfully!"
        private const val TOAST_MONEY_FAIL = "Task was taken Failure!"
        private const val PHOTO_STORAGE = "images/"


        fun createCurrentUser(context: Context?) {

            if (user != null) {

                // Получаем базу данных пользователей
                db.collection(COLLECTION_USERS)
                    .addSnapshotListener { value, error ->

                        val userContains =
                            value?.documents?.stream()?.anyMatch { x -> x.id == user.uid }


                        // Если полььзователя еще нет, добавляем его
                        if (userContains != null && !userContains) {

                            val userObject = UserObject(
                                user.displayName?.substringBeforeLast(" "),
                                user.displayName?.substringAfterLast(" "),
                                user.email,
                                user.phoneNumber,
                                user.photoUrl.toString(),
                                "",
                                1000,
                                arrayListOf(),
                                arrayListOf(),
                                arrayListOf()
                            )

                            db.collection(COLLECTION_USERS).document(user.uid)
                                .set(userObject.toMap())
                        }
                    }
            }
        }


        fun getCurrentUser(): DocumentReference? {
            if (user != null) {
                return db.collection(COLLECTION_USERS).document(user.uid)
            }
            return null
        }

        private fun writePost(context: Context?, postObject: PostObject) {
            if (user != null) {

                postObject.author = user.uid

                // Add a new document with a generated ID
                db.collection(COLLECTION_FEEDS).add(postObject.toMap())
                    .addOnSuccessListener { post ->


                        // Получаем профиль пользователя
                        val userDocument = db.collection(COLLECTION_USERS).document(user.uid)


                        userDocument.get().addOnSuccessListener {
                            // Получаем лист опубликованых постов
                            val postIds: ArrayList<String> = it.get("postIds") as ArrayList<String>
                            // Добовляем id нового поста
                            postIds.add(post.id)
                            // Обновляем профиль пользователя
                            userDocument.update("postIds", postIds)
                        }

                        Toast.makeText(context, TOAST_CREATE_POST_SUCCESS, Toast.LENGTH_SHORT)
                            .show()
                    }

                    .addOnFailureListener {
                        Toast.makeText(context, TOAST_CREATE_POST_FAIL, Toast.LENGTH_SHORT).show()
                    }
            }
        }

        fun writeTask(context: Context?, taskObject: TaskObject, successListener: () -> Unit) {
            if (user != null) {
                taskObject.author = user.uid

                // Получаем профиль пользователя
                val userDocument = db.collection(COLLECTION_USERS).document(user.uid)

                userDocument.get().addOnSuccessListener {
                    val coins = it.get("countCoins") as Int

                    if (coins >= taskObject.reward) {
                        // Add a new document with a generated ID
                        db.collection(COLLECTION_TASKS).add(taskObject.toMap())
                            .addOnSuccessListener { task ->

                                userDocument.get().addOnSuccessListener {
                                    // Получаем лист опубликованых задач
                                    val taskIds: ArrayList<String> =
                                        it.get("workIds") as ArrayList<String>
                                    // Добовляем id новой задачи
                                    taskIds.add(task.id)
                                    // Обновляем профиль пользователя
                                    userDocument.update("workIds", taskIds)
                                    userDocument.update("countCoins", coins - taskObject.reward)
                                }

                                Toast.makeText(
                                    context,
                                    TOAST_CREATE_TASK_SUCCESS,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                            }.addOnFailureListener {
                                Toast.makeText(context, TOAST_CREATE_TASK_FAIL, Toast.LENGTH_SHORT)
                                    .show()
                            }

                        Toast.makeText(context, TOAST_MONEY_SUCCESS, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, TOAST_MONEY_FAIL, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        fun uploadImage(
            path: Uri?,
            context: Context?,
            postObject: PostObject,
            nameCollection: String
        ) {
            if (path != null) {
                val name = UUID.randomUUID().toString()
                val ref = storageRef.child(PHOTO_STORAGE + name)

                val uploadTask = ref.putFile(path)
                val urlTask = uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    ref.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //return task.result.toString()
                        postObject.storageRef = task.result.toString()
                        writePost(context, postObject)
                    }
                }
            } else {
                writePost(context, postObject)
            }
        }

        fun readAllPost(
            list: ArrayList<PostObject>,
            dataChangedListener: () -> Unit
        ) {

            // Getting all data from data-base and push this info within Adapter
            db.collection(COLLECTION_FEEDS).get()
                .addOnSuccessListener { result ->

                    val taskList = result.toObjects(PostObject::class.java)

                    list.clear()
                    list.addAll(taskList)
                    dataChangedListener()

                    Log.d("READ_BASE", "Success Getting documents.")
                }
                .addOnFailureListener { exception ->
                    Log.w("READ_BASE", "Error getting documents.", exception)
                }
        }

        fun readAllTasks(
            list: ArrayList<TaskObject>,
            dataChangedListener: () -> Unit
        ) {

            // Getting all data from data-base and push this info within Adapter
            db.collection(COLLECTION_TASKS).get()
                .addOnSuccessListener { result ->

                    val taskList = result.toObjects(TaskObject::class.java)

                    list.clear()
                    list.addAll(taskList.filter { it.author != user?.uid && it.employee != user?.uid })
                    dataChangedListener()

                    Log.d("READ_BASE", "Success Getting tasks.")
                }
                .addOnFailureListener { exception ->
                    Log.w("READ_BASE", "Error getting tasks.", exception)
                }
        }

        fun readAllTasksMadeByUser(
            list: ArrayList<TaskObject>,
            dataChangedListener: () -> Unit
        ) {

            // Getting all data from data-base and push this info within Adapter
            db.collection(COLLECTION_TASKS).get()
                .addOnSuccessListener { result ->

                    val taskList = result.toObjects(TaskObject::class.java)

                    list.clear()
                    list.addAll(taskList.filter { it.author == user?.uid })
                    dataChangedListener()

                    Log.d("READ_BASE", "Success Getting tasks.")
                }
                .addOnFailureListener { exception ->
                    Log.w("READ_BASE", "Error getting tasks.", exception)
                }
        }

        fun readAllTasksPerformedByUser(
            list: ArrayList<TaskObject>,
            dataChangedListener: () -> Unit
        ) {

            // Getting all data from data-base and push this info within Adapter
            db.collection(COLLECTION_TASKS).get()
                .addOnSuccessListener { result ->

                    val taskList = result.toObjects(TaskObject::class.java)

                    list.clear()
                    list.addAll(taskList.filter { it.employee == user?.uid })
                    dataChangedListener()

                    Log.d("READ_BASE", "Success Getting tasks.")
                }
                .addOnFailureListener { exception ->
                    Log.w("READ_BASE", "Error getting tasks.", exception)
                }
        }


        fun takeTaskByUser(taskObject: TaskObject) {
            if (user == null) {
                return
            }
            val userTask = db.collection(COLLECTION_USERS).document(user.uid)

            db.collection(COLLECTION_TASKS).get()
                .addOnSuccessListener { result ->
                    for (i in result) {
                        if ((result.toObjects(TaskObject::class.java)).equals(taskObject)) {
                            db.collection(COLLECTION_TASKS).document(i.id).apply {
                                update("status", TaskObject.Status.IN_PROGRESS)
                                update("employee", user.uid) // Обновляем данные поста

                                userTask.get().addOnSuccessListener { result ->
                                    val takenTasks =
                                        result.get("takenTaskIds") as ArrayList<String>
                                    takenTasks.add(i.id)
                                    userTask.update(
                                        "takenTaskIds",
                                        takenTasks
                                    ) // Обновляем данные у того, кто будет выполнять задание
                                }
                            }
                        }
                    }
                }
        }

        fun confirmTaskCompletion(taskObject: TaskObject, punish: Boolean) {
            if (user == null) {
                return
            }

            val userTask = db.collection(COLLECTION_USERS).document(taskObject.employee!!)

            var id = ""

            db.collection(COLLECTION_TASKS).get()
                .addOnSuccessListener { result ->
                    for (i in result) {
                        if ((result.toObjects(TaskObject::class.java)).equals(taskObject)) {
                            id = i.id
                            break;
                        }
                    }

                    userTask.get().addOnSuccessListener {
                        if (punish) {
                            userTask.update(
                                "rating",
                                (it.get("rating") as Int - 1)
                            )
                        } else {
                            userTask.update(
                                "countCoins",
                                taskObject.reward + (it.get("countCoins") as Int)
                            )
                            userTask.update(
                                "rating",
                                (it.get("rating") as Int + 1)
                            )
                        }

                        val takenTasks =
                            it.get("takenTaskIds") as ArrayList<String>
                        takenTasks.remove(id)
                        userTask.update(
                            "takenTaskIds",
                            takenTasks
                        )
                    }

                    db.collection(COLLECTION_USERS).document(user.uid).get()
                        .addOnSuccessListener {
                            val tasks = it.get("workIds") as ArrayList<String>
                            tasks.remove(id)
                            userTask.update(
                                "workIds",
                                tasks
                            )
                        }.addOnSuccessListener {
                            db.collection(COLLECTION_TASKS).document(id).delete()
                        }
                }
        }

        fun tellAboutTaskCompletion(taskObject: TaskObject) {
            if (user == null) {
                return
            }

            val userTask = db.collection(COLLECTION_USERS).document(user.uid)

            var id = ""

            db.collection(COLLECTION_TASKS).get()
                .addOnSuccessListener { result ->
                    for (i in result) {
                        if ((result.toObjects(TaskObject::class.java)).equals(taskObject)) {
                            id = i.id
                            break;
                        }
                    }

                    db.collection(COLLECTION_TASKS).document(id).apply {
                        update("status", TaskObject.Status.READY)

                        userTask.get().addOnSuccessListener { result ->

                            val takenTasks =
                                result.get("takenTaskIds") as ArrayList<String>
                            takenTasks.remove(id)
                            userTask.update(
                                "takenTaskIds",
                                takenTasks
                            )
                        }
                    }
                }
        }
    }
}