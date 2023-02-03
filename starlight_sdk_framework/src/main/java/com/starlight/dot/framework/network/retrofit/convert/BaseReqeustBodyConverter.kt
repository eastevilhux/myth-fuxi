package com.starlight.dot.framework.network.retrofit.convert

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.starlight.dot.framework.utils.SLog
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Converter
import java.io.OutputStreamWriter
import java.nio.charset.Charset

class BaseReqeustBodyConverter<T>(private val gson:Gson, val adapter: TypeAdapter<T>) : Converter<T,RequestBody>{
    override fun convert(value: T): RequestBody {
        SLog.d(TAG,"value=>111");

        val buffer = Buffer()
        val writer = OutputStreamWriter(buffer.outputStream(), UTF_8)
        val jsonWriter = gson.newJsonWriter(writer)
        SLog.d(TAG,"value=>${value}");
        adapter.write(jsonWriter, value)
        jsonWriter.close()
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString())
    }

    companion object {
        private const val TAG = "SL_BaseReqeustBodyConverter==>";
        private val MEDIA_TYPE: MediaType? = MediaType.parse("application/json; charset=UTF-8")
        private val UTF_8 = Charset.forName("UTF-8")
    }

}
