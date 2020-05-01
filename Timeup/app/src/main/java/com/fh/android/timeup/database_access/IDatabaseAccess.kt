package com.fh.android.timeup.database_access

import com.fh.android.timeup.beans.IHashable

interface IDatabaseAccess{
    fun <T>loadFromDatabase(pathString : String) : T
    suspend fun updateHashable(pathString : String, obj : IHashable)
}