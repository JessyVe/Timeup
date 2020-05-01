package com.fh.android.timeup.services

import com.fh.android.timeup.beans.IHashable
import java.security.MessageDigest

object HashingEngine {
    /**
     * Set the unique hash of an object.
     */
    fun setHashOfObject(hashingObject : IHashable){
        val bytes = hashingObject.getHashingString().toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        hashingObject.setHash(digest.fold("", { str, it -> str + "%02x".format(it) }))
    }
}