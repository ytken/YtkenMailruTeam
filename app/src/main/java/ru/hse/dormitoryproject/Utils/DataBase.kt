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
import java.util.*
import kotlin.collections.ArrayList


class DataBase() {

    companion object {
        // Link to dataBase
        private val db = Firebase.firestore
        // Current user
        private val user = Firebase.auth.currentUser

        private const val COLLECTION_FEEDS = "PageWork"
        private const val COLLECTION_USERS = "users"

        private val storage = FirebaseStorage.getInstance()
        private val storageRef = storage.reference
        private const val TOAST_CREATE_POST_SUCCESS = "Post created successfully!"
        private const val TOAST_CREATE_POST_FAIL = "Post created Failure!"
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
            if(user!=null) {
                return db.collection(COLLECTION_USERS).document(user.uid)
            }
            return null
        }

        private fun writePost(context: Context?, postObject: PostObject) {
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

        public fun uploadImage(path : Uri?, context: Context?, postObject: PostObject, nameCollection: String) {
            if(path!=null) {
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
            }
            else{
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
    }
}