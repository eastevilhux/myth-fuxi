package com.starlight.dot.framework.network.retrofit.adapter

import com.starlight.dot.framework.network.NetworkHelper
import com.starlight.dot.framework.utils.SLog
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.starlight.dot.framework.network.entity.Result;

class BaseCallAdapter<R>(private val type: Type) : CallAdapter<R, R> {

    override fun adapt(call: Call<R>): R {
        return try {
            val response = call.execute();
            if(response.isSuccessful){
                SLog.e(TAG,"response_result:SUCCESS");
                var body = response.body() ?: emptyResponse();
                val result = body as Result<*>;
                when(result.code){
                    NetworkHelper.instance().httpConfig().loginErrorCode() -> loginError();
                    NetworkHelper.instance().httpConfig().emptyCode()-> emptyResponse()
                }
                return body;
            }else{
                SLog.e(TAG,"response_result:error");
                errorResponse(response);
            }
        }catch (e:Exception){
            e.printStackTrace();
            SLog.e(TAG,"response_result:error");
            parseException(e)
        }
    }

    override fun responseType(): Type = type;

    private fun loginError(): R {
        SLog.d(TAG,"login_error");
        return exception(
            Result<Any>(),"login_error",
            NetworkHelper.instance().httpConfig().loginErrorCode()) as R;
    }

    private fun errorResponse(response: Response<R>) : R{
        SLog.d(TAG,"errorResponse");
        return error(Result<Any>(),response) as R;
    }

    private fun emptyResponse() : R{
        SLog.d(TAG,"emptyResponse");
        return empty(Result<Any>()) as R;
    }

    private fun parseException(e:Exception) : R{
        SLog.d(TAG,e?.let { e.message }?:"no error message")
        return when(e){
            is IOException,
            is ConnectException,
            is UnknownHostException,
            is SocketTimeoutException -> networkError()
            else -> exceptionResponse(e.message);
        }
    }


    private fun networkError() : R{
        SLog.d(TAG,"networkError");
        return exception(
            Result<Any>(),"network_error",
            NetworkHelper.instance().httpConfig().networkErrorCode()) as R;
    }

    private fun exceptionResponse(message: String?) : R{
        SLog.d(TAG,"exceptionResponse");
        return exception(Result<Any>(),message, NetworkHelper.instance().httpConfig().serviceErrorCode()) as R;
    }

    companion object{
        private const val TAG = "SL_BaseCallAdapter=>";

        fun <T : Result<*>> empty(response: T, code: Int = NetworkHelper.instance().httpConfig().emptyCode()): T {
            response.code = code
            response.msg = "";
            return response
        }


        fun <T : Result<*>> error(response: T, retrofitResponse: Response<*>): T {
            response.code = retrofitResponse.code()
            response.msg = retrofitResponse.message()
            return response
        }

        fun <T : Result<*>> exception(
            response: T,
            message: String?,
            code: Int = NetworkHelper.instance().httpConfig().emptyCode()
        ): Result<*> {
            response.code = code
            response.msg = message
            return response
        }
    }

}
