package com.fh.android.timeup.services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonConverter {
    /**
     * Returns an object of the specified type.
     */
    fun <T>deserializeJsonString(jsonString : String) : T {
         try {
            val objectType = object : TypeToken<T>() {}.type
            return Gson().fromJson(jsonString, objectType)
        } catch(ex : java.lang.Exception){
            throw ex
        }
    }

    /**
     * Returns a the json string depicting the given object.
     */
    fun <T>serializeObject(serializeObject : T) : String {
        try {
            return Gson().toJson(serializeObject)
        } catch(ex : java.lang.Exception){
            throw ex
        }
    }
}