package com.starlight.dot.framework.network.retrofit.adapter

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

class BaseCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type?,
        annotations: Array<out Annotation>?,
        retrofit: Retrofit?
    ): CallAdapter<*, *> {
        return BaseCallAdapter<Any>(returnType!!);
    }

    companion object{
        fun create() : BaseCallAdapterFactory = BaseCallAdapterFactory();
    }
}
