package com.starlight.dot.framework.network.datasource

class AESFactory : DataFactory {

    override fun create(): Data {
        return AES();
    }

}
