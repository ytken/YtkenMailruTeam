package ru.hse.dormitoryproject.Utils

import android.util.Log
import android.widget.ArrayAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.hse.dormitoryproject.PostObject


class DataBase() {

    companion object {
        private val db = Firebase.firestore
        fun writeToBase(postObject: PostObject, nameCollection: String) {

            // Add a new document with a generated ID
            db.collection(nameCollection).add(postObject.toMap())
                .addOnSuccessListener {
                    Log.d("WRITEBASE", "Success Write Document")
                }
                .addOnFailureListener {
                    Log.w("WRITEBASE", "Failure write Document")
                }
        }


        fun readAllData(nameCollection: String, adapter: ArrayAdapter<PostObject>) {

            db.collection(nameCollection).get()
                .addOnSuccessListener { result ->

                    val taskList = result.toObjects(PostObject::class.java)
                    adapter.clear()
                    adapter.addAll(taskList)
                    adapter.notifyDataSetChanged()

                    Log.d("READ_BASE", "Success Getting documents.")
                }
                .addOnFailureListener { exception ->
                    Log.w("READ_BASE", "Error getting documents.", exception)
                }
        }
    }
}