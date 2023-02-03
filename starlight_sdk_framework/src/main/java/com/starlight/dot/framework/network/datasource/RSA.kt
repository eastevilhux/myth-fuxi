package com.starlight.dot.framework.network.datasource

import com.starlight.dot.framework.network.datasource.Data
import com.starlight.dot.framework.utils.RSAUtil

class RSA : Data() {
    override fun encryptionData(data: String, key: String, tag: String?): String? {
        return RSAUtil.encryptByPublicKey(data,key);
    }

    override fun decryptData(data: String, key: String, tag: String?): String? {
        return RSAUtil.decryptByPublicKey(data,key);
    }


}
