package com.starlight.dot.framework.network.retrofit.convert

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.starlight.dot.framework.utils.SLog
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.NullPointerException
import java.lang.reflect.Type

@SuppressLint("LongLogTag")
class SDKConverterFactory(private val gson:Gson) : Converter.Factory(){

    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<out Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *> {
        var adapter = gson.getAdapter(TypeToken.get(type));
        SLog.d(TAG,"responseBodyConverter==>")
        return SDKResponseBodyConverter(gson,adapter);
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val adapter = gson.getAdapter(TypeToken.get(type))
        SLog.d(TAG,"requestBodyConverter==>");
        return BaseReqeustBodyConverter(gson,adapter);
    }



    companion object{
        private  val TAG = "SL_BaseConverterFactory=>";

        fun create() : SDKConverterFactory {
            return create(Gson());
        }

        fun create(gson:Gson?) : SDKConverterFactory {
            SLog.d(TAG,"create==>");
            if(gson == null) {
                Log.e(TAG,"create EastConverterFactory when gson is null")
                throw NullPointerException("gson not allow null")
            };
            return SDKConverterFactory(gson);
        }
    }
}
