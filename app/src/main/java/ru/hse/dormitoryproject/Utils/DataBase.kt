package ru.hse.dormitoryproject.Utils

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import ru.hse.dormitoryproject.PostObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log


class DataBase() {

    companion object {
        // Link to dataBase
        private val db = Firebase.firestore
        private val storage = FirebaseStorage.getInstance()
        private val storageRef = storage.reference
        private const val TOAST_CREATE_POST_SUCCESS = "Post created successfully!"
        private const val TOAST_CREATE_POST_FAIL = "Post created Failure!"
        private const val PHOTO_STORAGE = "images/"


        private fun writeToBase(context: Context?, postObject: PostObject, nameCollection: String) {
            // Add a new document with a generated ID
            db.collection(nameCollection).add(postObject.toMap())
                .addOnSuccessListener {
                    Toast.makeText(context, TOAST_CREATE_POST_SUCCESS, Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, TOAST_CREATE_POST_FAIL, Toast.LENGTH_SHORT).show()
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
                        writeToBase(context, postObject, nameCollection)
                    }
                }
            }
            else{
                writeToBase(context, postObject, nameCollection)
            }
        }

//        private fun downloadImage(ref : String?, imageView: ImageView){
//            if(ref != null && ref != ""){
//                Glide
//            }
//        }

        //fun readAllData(nameCollection: String, adapter: ArrayAdapter<PostObject>, list : ArrayList<PostObject>, dataChangedListener : ()->Unit ) {
        fun readAllData(
            nameCollection: String,
            list: ArrayList<PostObject>,
            dataChangedListener: () -> Unit
        ) {

            // Getting all data from data-base and push this info within Adapter
            db.collection(nameCollection).get()
                .addOnSuccessListener { result ->

                    val taskList = result.toObjects(PostObject::class.java)
                    //adapter.clear()
                    //adapter.addAll(taskList)
                    //adapter.notifyDataSetChanged()

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