package com.starlight.dot.framework.network.interceptor

import com.starlight.dot.framework.network.NetworkHelper
import com.starlight.dot.framework.utils.SLog
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.nio.charset.Charset
import java.util.*

class HttpInterceptor : Interceptor{
    companion object{
        private const val TAG = "SL_HttpInterceptor==>";
    }

    private var httpHeader : HttpHeader? = null;

    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()

        val headers: Headers = addCommonHeader(oldRequest)

        val commonParamsUrlBuilder = oldRequest.url()
            .newBuilder()
            .scheme(oldRequest.url().scheme())
            .host(oldRequest.url().host());

        val newRequestBuild = oldRequest.newBuilder()
            .method(oldRequest.method(), oldRequest.body())
            .headers(headers)
            .url(commonParamsUrlBuilder.build())

        //拿到响应体
        val mResponse = chain.proceed(newRequestBuild.build())
        val responseBody = mResponse.body()

        //得到缓冲源

        //得到缓冲源
        val source = responseBody!!.source()

        //请求全部
        source.request(Long.MAX_VALUE) // Buffer the entire body.

        val buffer = source.buffer()
        var charset: Charset = NetworkHelper.instance().httpConfig().httpCharset()

        val contentType = responseBody.contentType()

        if (contentType != null) {
            charset = contentType.charset(NetworkHelper.instance().httpConfig().httpCharset())!!
        }
        //读取返回数据
        val bodyString = buffer.clone().readString(charset)
        return mResponse
    }

    private fun addCommonHeader(request: Request): Headers {
        val builder: Headers.Builder = request.headers().newBuilder();

        var keytype : kotlin.String? = null;
        /*Constants.serviceType?.let {
            when(Constants.serviceType){
                Constants.ServiceType.SERVICE_TYPE_AES->keytype = "2"
                Constants.ServiceType.SERVICE_TYPE_RSA->keytype = "1"
            }
        }
        var userid = Constants.userid;
        var appkey =  Constants.appKey;
        appkey?.let {
            builder.add("appkey", it);
        }
        keytype?.let {
            builder.add("keytype", it);
        }
        userid?.let {
            builder.add("userid", it);
        }*/
        SLog.d("healder==>","to_addhealder");
        val healder = httpHeader?.getHealder();
        healder?.let {
            SLog.d("healder==>","start_addhealder");
            for(entry in healder){
                SLog.d("healder==>","key=>${entry.key},value=>${entry.value}");
                builder.add(entry.key,entry.value);
            }
            SLog.d("healder==>","end_addhealder");
        }
        return builder.build()
    }

    fun addHeader(httpHeader: HttpHeader?){
        this.httpHeader = httpHeader;
    }

    interface HttpHeader{
        fun getHealder() : TreeMap<String,String>;
    }

}
