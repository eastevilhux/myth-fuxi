package com.starlight.dot.framework.network.retrofit

import com.starlight.dot.framework.network.retrofit.adapter.BaseCallAdapterFactory
import com.starlight.dot.framework.network.retrofit.convert.BaseConverterFactory
import com.starlight.dot.framework.network.NetworkHelper
import com.starlight.dot.framework.network.interceptor.HttpInterceptor
import com.starlight.dot.framework.network.interceptor.LogInterceptor
import com.starlight.dot.framework.network.interceptor.NetInterceptor
import com.starlight.dot.framework.network.interceptor.ParamsInterceptor
import com.starlight.dot.framework.network.retrofit.convert.SDKConverterFactory
import com.starlight.dot.framework.utils.JsonUtil
import com.starlight.dot.framework.utils.SLog
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class RetrofitConfigure {
    companion object{
        fun createByRetrofit(retrofit:Retrofit,map: HashMap<String, Class<*>>): HashMap<String, Any> {
            synchronized(RetrofitConfigure::class.java){
                var httpInterceptor = HttpInterceptor();
                httpInterceptor.addHeader(NetworkHelper.instance().httpHeader())
                val okBuilder = OkHttpClient().newBuilder()
                    .connectTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .readTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .writeTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    );

                if(NetworkHelper.instance().httpConfig().isEncryptParams()){
                    okBuilder.addInterceptor(ParamsInterceptor())
                }

                val interceptorList = NetworkHelper.instance().httpConfig().interceptorList();
                interceptorList?.let {
                    it.forEach {
                        okBuilder.addInterceptor(it);
                    }
                }
                val okHttpClient = okBuilder.addInterceptor(HttpInterceptor())
                    .addInterceptor(LogInterceptor())
                    .addInterceptor(NetInterceptor())
                    .addNetworkInterceptor(HttpInterceptor())
                    .build();

                var newRetrofit = retrofit.newBuilder()
                    .client(okHttpClient)
                    .addConverterFactory(BaseConverterFactory.create(GsonUtil.getInstance().gson)) //添加gson转换器
                    .addCallAdapterFactory(BaseCallAdapterFactory()) //添加rxjava转换器
                    .build();

                val m = HashMap<String, Any>(map.size)
                for(en in map){
                    val t = newRetrofit.create(en.value);
                    m[en.key] = t;
                }
                return m
            }
        }

        fun createByUrl(baseUrl:String,map: HashMap<String, Class<*>>): HashMap<String, Any> {
            synchronized(RetrofitConfigure::class.java){
                val okBuilder = OkHttpClient().newBuilder()
                    .connectTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .readTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .writeTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    );

                if(NetworkHelper.instance().httpConfig().isEncryptParams()){
                    okBuilder.addInterceptor(ParamsInterceptor())
                }

                val interceptorList = NetworkHelper.instance().httpConfig().interceptorList();
                interceptorList?.let {
                    it.forEach {
                        okBuilder.addInterceptor(it);
                    }
                }

                val httpInterceptor = HttpInterceptor();
                httpInterceptor.addHeader(NetworkHelper.instance().httpHeader())

                val okHttpClient = okBuilder.addInterceptor(httpInterceptor)
                    .addInterceptor(LogInterceptor())
                    .addInterceptor(NetInterceptor())
                    .addNetworkInterceptor(HttpInterceptor()).build();

                val mRetrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(SDKConverterFactory.create(GsonUtil.getInstance().gson)) //添加gson转换器
                    .addCallAdapterFactory(BaseCallAdapterFactory()) //添加rxjava转换器
                    .client(okHttpClient)
                    .build()

                val m = HashMap<String, Any>(map.size)
                for(en in map){
                    val t = mRetrofit.create(en.value);
                    m[en.key] = t;
                }
                return m
            }
        }

        fun registerService(baseUrl:String,clzzz : Class<*>) : Any {
            synchronized(RetrofitConfigure::class.java){
                val okBuilder = OkHttpClient().newBuilder()
                    .connectTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .readTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .writeTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    );

                if(NetworkHelper.instance().httpConfig().isEncryptParams()){
                    okBuilder.addInterceptor(ParamsInterceptor())
                }

                val interceptorList = NetworkHelper.instance().httpConfig().interceptorList();
                interceptorList?.let {
                    it.forEach {
                        okBuilder.addInterceptor(it);
                    }
                }

                val httpInterceptor = HttpInterceptor();
                httpInterceptor.addHeader(NetworkHelper.instance().httpHeader())

                val okHttpClient = okBuilder.addInterceptor(httpInterceptor)
                    .addInterceptor(LogInterceptor())
                    .addInterceptor(NetInterceptor())
                    .addNetworkInterceptor(HttpInterceptor()).build();

                val mRetrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(SDKConverterFactory.create(GsonUtil.getInstance().gson)) //添加gson转换器
                    .addCallAdapterFactory(BaseCallAdapterFactory()) //添加rxjava转换器
                    .client(okHttpClient)
                    .build()

                val s = mRetrofit.create(clzzz);
                return s;
            }
        }

        fun registerService(map: HashMap<String, Class<*>>): HashMap<String, Any> {
            synchronized(RetrofitConfigure::class.java) {
                var httpInterceptor = HttpInterceptor();
                httpInterceptor.addHeader(NetworkHelper.instance().httpHeader())
                val okBuilder = OkHttpClient().newBuilder()
                    .connectTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .readTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .writeTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    );
                if(NetworkHelper.instance().httpConfig().isEncryptParams()){
                    okBuilder.addInterceptor(ParamsInterceptor())
                }
                val okHttpClient = okBuilder.addInterceptor(httpInterceptor)
                    .addInterceptor(LogInterceptor())
                    .addInterceptor(NetInterceptor())
                    .addNetworkInterceptor(HttpInterceptor()).build();

                val mRetrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(NetworkHelper.instance().httpConfig().baseUrl())
                    .addConverterFactory(BaseConverterFactory.create(GsonUtil.getInstance().gson)) //添加gson转换器
                    .addCallAdapterFactory(BaseCallAdapterFactory()) //添加rxjava转换器
                    .client(okHttpClient)
                    .build()

                val m = HashMap<String, Any>(map.size)
                for(en in map){
                    val t = mRetrofit.create(en.value);
                    m[en.key] = t;
                }
                return m
            }
        }
    }
}
