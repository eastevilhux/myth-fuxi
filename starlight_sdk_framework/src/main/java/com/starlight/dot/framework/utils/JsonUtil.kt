package com.starlight.dot.framework.utils

import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

class JsonUtil private constructor(){


    companion object{
        private val gson : Gson by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED ){
            val gsonBuilder = GsonBuilder()
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
            gsonBuilder.create();
        }

        val instance : JsonUtil by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){
            JsonUtil();
        }
    }

    fun getGson() : Gson{
        return gson;
    }
}


fun <T> String.toJsonList(): List<T> {
    val listType = object : TypeToken<ArrayList<T>>() {}.type
    val gson = JsonUtil.instance.getGson();
    return gson.fromJson(this, listType)
}

fun <T> String.fastJsonToList(clazz: Class<T>) : List<T>{
    return JSON.parseArray(this,clazz);
}
