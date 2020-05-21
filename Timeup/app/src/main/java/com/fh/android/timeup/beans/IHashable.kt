package com.fh.android.timeup.beans

interface IHashable{
    fun getHashingString() : String
    fun setHash(projectHash : String)
    fun getHash() : String
}