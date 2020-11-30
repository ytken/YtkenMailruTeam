package ru.hse.dormitoryproject.Utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.hse.dormitoryproject.PostObject
import java.util.*


class DataBase() {


    companion object {
        private val db =  Firebase.firestore

        fun writeToBase(postObject: PostObject, nameCollection: String) {

            // Add a new document with a generated ID
            db.collection(nameCollection).add(postObject.toMap())
                .addOnSuccessListener { Log.d("WRITEBASE","Success Write Document")
            }
                .addOnFailureListener{
                Log.w("WRITEBASE","Failure write Document")
            }
        }


        fun readAllBase(nameCollection: String) :  List<PostObject> {

            var taskList:List<PostObject> = listOf<PostObject>()




            Log.e("DEBUG_FR", "get invoke()")
            db.collection(nameCollection).get()
                .addOnSuccessListener { result ->
                     taskList = result.toObjects(PostObject::class.java)
                    Log.d("READ_BASE","Success Getting documents.")
                }
                .addOnCompleteListener {
                    Log.d("READ_BASE","Complete Getting documents.")
                }
                .addOnCanceledListener{
                    Log.w("READ_BASE", "Canceled getting documents.")
                }
                .addOnFailureListener { exception ->
                    Log.w("READ_BASE", "Error getting documents.", exception)
                }

            return taskList
        }

    }
}