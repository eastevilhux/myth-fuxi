package com.starlight.dot.framework.network.datasource

import com.east.network.network.datasource.RSAFactory
import com.starlight.dot.framework.network.DataSecurity
import com.starlight.dot.framework.network.SDKDataSecurity
import java.lang.NullPointerException

class DataHelper {
    private var decryptType : DataType = DataType.TYPE_RSA;
    private var encryptType : DataType = DataType.TYPE_RSA;

    private var key : String? = null;
    private var iv : String? = null;

    private var rsaFactory : RSAFactory = RSAFactory();
    private var aesFactory : AESFactory = AESFactory();

    private var dataSecurity : DataSecurity? = null;
    private var sdkDataSecurity : SDKDataSecurity? = null;

    private constructor(){

    }

    companion object{
        val instance : DataHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { DataHelper() }
    }

    fun dataSecurity(): DataSecurity? {
        return dataSecurity;
    }

    fun setDataSecurity(dataSecurity : DataSecurity){
        this.dataSecurity = dataSecurity;
    }

    fun setSdkDataSecurity(sdkDataSecurity : SDKDataSecurity){
        this.sdkDataSecurity = sdkDataSecurity;
    }

    fun sdkDataSecurity(): SDKDataSecurity? {
        return sdkDataSecurity;
    }

    enum class DataType{
        TYPE_AES,
        TYPE_RSA;
    }
}
