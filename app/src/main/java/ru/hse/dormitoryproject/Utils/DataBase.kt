package ru.hse.dormitoryproject.Utils

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class DataBase() {

    companion object {
        // Link to dataBase
        private val db = Firebase.firestore
        // Current user
        private val user = Firebase.auth.currentUser

        private const val COLLECTION_FEEDS = "PageWork"
        private const val COLLECTION_USERS = "users"

        private const val TOAST_CREATE_POST_SUCCESS = "Post created successfully!"
        private const val TOAST_CREATE_POST_FAIL = "Post created Failure!"


        @RequiresApi(Build.VERSION_CODES.N)
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
                                user.displayName,
                                user.email,
                                user.phoneNumber,
                                user.photoUrl.toString(),
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


        fun writePost(context: Context?, postObject: PostObject) {
            if (user != null) {


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
    }
}