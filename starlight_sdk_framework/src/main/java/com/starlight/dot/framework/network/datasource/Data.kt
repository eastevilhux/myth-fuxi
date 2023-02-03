package com.starlight.dot.framework.network.datasource

abstract class Data {

    abstract fun encryptionData(data:String,key : String,tag:String? = null) : String?;

    abstract fun decryptData(data:String,key : String,tag:String? = null) : String?;
}
