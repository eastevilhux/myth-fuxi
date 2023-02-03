package com.starlight.dot.framework.network.retrofit.model

import com.starlight.dot.framework.network.retrofit.RetrofitConfigure
import com.starlight.dot.framework.network.retrofit.service.AppService

class AppModel {
    companion object{

        private const val SERVICE_KEY_APP = "appService";
        private const val SERVICE_KEY_ACCOUNT = "accountService";


        val instance : AppModel by lazy(mode=  LazyThreadSafetyMode.SYNCHRONIZED){
            AppModel();
        }

        fun initModel(){
            instance.map;
        }
    }

    private val map : HashMap<String, Any> by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        initRetrofit();
    };

    private fun initRetrofit():HashMap<String,Any>{
        var m = HashMap<String, Class<*>>();
        m.put(SERVICE_KEY_ACCOUNT, AppService::class.java)
        var map =  RetrofitConfigure.registerService(m);
        return map;
    }
}
