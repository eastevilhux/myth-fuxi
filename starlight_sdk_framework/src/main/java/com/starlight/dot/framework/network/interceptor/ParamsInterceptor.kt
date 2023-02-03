package com.starlight.dot.framework.network.interceptor

import com.starlight.dot.framework.network.datasource.DataHelper
import com.starlight.dot.framework.utils.SLog
import okhttp3.*
import org.json.JSONObject
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import java.util.*


class ParamsInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val method = request.method().toLowerCase().trim()
        //字符集
        var charset: Charset? = Charset.forName("UTF-8")
        //返回url
        val url = request.url()
        //http://192.168.0.108:8080/interface/xxx   //@get @delete 时候需要拼接在请求地址后面  ?userName=xiaoming&userPassword=12345
        val scheme = url.scheme() //协议http
        val host = url.host() //host地址,192.168.0.108
        val port = url.port() //端口8080
        val path = url.encodedPath() //  /interface/login
        val originalPath = "$scheme://$host:$port$path"
        if(!METHOD_GET.equals(method) &&  !METHOD_DELETE.equals(method)){
            //获取body中的参数
            val requestBody = request.body()
            requestBody?.let {requestBody->
                //判断类型
                val contentType: MediaType? = requestBody.contentType()
                if(contentType != null){
                    charset = contentType.charset(charset);
                    /*如果是二进制上传  则不进行加密*/
                    if (contentType.type().toLowerCase().equals("multipart")) {
                        return chain.proceed(request);
                    }
                }
                // 获取请求的数据
                var buffer = okio.Buffer();
                requestBody.writeTo(buffer);
                //val requestData = URLDecoder.decode(buffer.readString(charset).trim(), "utf-8")
                //SLog.d(TAG,"requestData==>${requestData}");
                val body = request.body();
                var requestData : String? = null;
                if(body is FormBody){
                    var i = 0;
                    var keyName : String? = null;
                    while(i<body.size()){
                        keyName = body.encodedName(i);
                        if("data".equals(keyName)){
                            requestData = URLDecoder.decode(body.encodedValue(i),"UTF-8");
                            break;
                        }
                        i++;
                    }
                }
                SLog.d(TAG,"reqeust_data=>${requestData}");
                //对排序后的参数按keyvalue形式拼接
                val sb = StringBuilder("");
                if(requestData != null && requestData.isNotEmpty()){
                    //这里的参数未经过加密和校验的，按加密规则进行加密处理，这里的data为json格式数据
                    val paramsMap = TreeMap<String,Any>();
                    var json = JSONObject(requestData);
                    val keys = json.keys();
                    //使用data中所有的key按a-z的字典顺序先排序
                    keys.forEach {
                        paramsMap.put(it,json.opt(it));
                    }
                    if(!paramsMap.isNullOrEmpty()){
                        paramsMap.forEach {
                            sb.append(it.key).append(it.value);
                        }
                    }
                }
                //对拼接好的数据进行签名,获得到签名数据
                val signData = DataHelper.instance.dataSecurity()?.signData(sb.toString());
                SLog.d(TAG,"signData=>${signData}");
                //重新组合新的请求数据
                var json = JSONObject();
                json.put("data",requestData);
                json.put("signData",signData);
                requestData = json.toString();
                SLog.d(TAG,"requestData_befor=>${requestData}");
                requestData = DataHelper.instance.dataSecurity()?.aesEncrypt(requestData);
                SLog.d(TAG,"requestData_encrypt_result=>${requestData}");
                //对请求数据进行URLEcode
                requestData = URLEncoder.encode(requestData,"UTF-8");
                if(requestData != null && !requestData.isNullOrEmpty()){
                    //重新构建新的请求
                    val bodyBuilder = FormBody.Builder();
                    bodyBuilder.add("data",requestData);
                    val newBody = bodyBuilder.build();
                    //构建新的requestBuilder
                    val newRequestBuilder = request.newBuilder()
                    newRequestBuilder.post(newBody);
                    return chain.proceed(newRequestBuilder.build());
                }
            }
        }
        //模式使用原有的request
        return chain.proceed(request);
    }

    private fun addCommonHeader(request: Request): Headers {
        val builder: Headers.Builder = request.headers().newBuilder()
        return builder.build()
    }

    companion object {
        private const val TAG = "SL_ParamsInterceptor==>"
        const val METHOD_GET = "get"
        const val METHOD_DELETE = "delete";
        const val METHOD_POST = "post"

        /**
         * 服务端接口定义需要接受的参数key为data
         */
        const val SERVICE_DATA_KEY = "data";
        const val HEADER_KEY_USER_AGENT = "User-Agent"
    }
}
