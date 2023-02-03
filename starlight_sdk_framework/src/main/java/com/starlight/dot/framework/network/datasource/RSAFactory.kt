package com.east.network.network.datasource

import com.starlight.dot.framework.network.datasource.Data
import com.starlight.dot.framework.network.datasource.DataFactory
import com.starlight.dot.framework.network.datasource.RSA

class RSAFactory : DataFactory {
    override fun create(): Data {
        return RSA();
    }
}
