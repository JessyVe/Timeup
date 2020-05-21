package com.fh.android.timeup.database_access

import com.fh.android.timeup.beans.IHashable
import com.fh.android.timeup.services.JsonConverter.deserializeJsonString
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object FirebaseAccess : IDatabaseAccess {
     private var database = Firebase.database.reference

     /**
      * Returns all projects currently saved in the database.
      */
     override fun <T> loadFromDatabase(pathString: String): T {
         val result = database.child(pathString)
         return deserializeJsonString(result.toString())
     }

     /**
      * Updates the specified object in the database.
      */
     override suspend fun updateHashable(pathString: String, hashingObject: IHashable) {
         database.child(pathString).child(hashingObject.getHash()).setValue(hashingObject).addOnSuccessListener {
             println("Updated successfully")
         }.addOnFailureListener {
             println("Update failed $it")
         }
     }

    fun addData(pathString: String, obj : Any){
        database.child(pathString).setValue(obj).addOnSuccessListener {
            println("Updated successfully")
        }.addOnFailureListener {
            println("Update failed $it")
        }
    }
 }
