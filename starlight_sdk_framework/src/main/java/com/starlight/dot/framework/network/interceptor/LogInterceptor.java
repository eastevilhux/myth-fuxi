package com.starlight.dot.framework.network.interceptor;

import com.starlight.dot.framework.utils.JsonUtil;
import com.starlight.dot.framework.utils.SLog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;


public class LogInterceptor implements Interceptor {

    public static String TAG = "SL_HTTP_LOG==>";

    public static int httpId = 0;

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {

        httpId = createRandomNumber(10000,99999);

        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration=endTime-startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        SLog.INSTANCE.d(TAG,httpId+"----------Start----------------");
        SLog.INSTANCE.d(TAG,httpId+"==URL=>"+request.url());
        String method=request.method();
        SLog.INSTANCE.d(TAG,httpId+"==METHOD=>"+method);
        RequestBody b = request.body();
        if(b != null){
            SLog.INSTANCE.d(TAG,httpId+"==REQUEST=>"+b.toString());
        }

        Set<String> headers = request.headers().names();
        if(headers != null && !headers.isEmpty()){
            Iterator<String> iter = headers.iterator();
            Map<String,String> map = new HashMap<>();
            String key = null;
            while(iter.hasNext()){
                key = iter.next();
                map.put(key,request.header(key));
            }
            SLog.INSTANCE.d(TAG,httpId+"==HEADERS=>"+ JsonUtil.Companion.getInstance().getGson().toJson(map));
        }
        if(request.method().equals(method)){
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                SLog.INSTANCE.d(TAG, httpId+"==PARAMS=>"+sb.toString());
            }
        }
        SLog.INSTANCE.longD(TAG,httpId+"==REPONSE=>" + content);
        SLog.INSTANCE.d(TAG,httpId+"----------End:"+duration+"毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }

    private static int createRandomNumber(int min,int max){
        return (int)(min+Math.random()*(max));
    }
}
