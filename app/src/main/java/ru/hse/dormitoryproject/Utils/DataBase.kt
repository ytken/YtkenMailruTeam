package ru.hse.dormitoryproject.Utils

import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.hse.dormitoryproject.PostObject
import ru.hse.dormitoryproject.newsFeed.FragmentCreatePost


class DataBase() {

    companion object {
        // Link to dataBase
        private val db = Firebase.firestore
        private const val TOAST_CREATE_POST_SUCCESS = "Post created successfully!"
        private const val TOAST_CREATE_POST_FAIL = "Post created Failure!"


        fun writeToBase(context: Context?, postObject: PostObject, nameCollection: String) {

            // Add a new document with a generated ID
            db.collection(nameCollection).add(postObject.toMap())
                .addOnSuccessListener {
                    Toast.makeText(context, TOAST_CREATE_POST_SUCCESS, Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, TOAST_CREATE_POST_FAIL, Toast.LENGTH_SHORT).show()
                }
        }


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