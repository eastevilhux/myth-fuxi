package com.starlight.dot.framework.network.datasource

import com.east.network.utils.AESUtil

class AES : Data() {

    override fun encryptionData(data: String, key: String, tag: String?): String? {
        return AESUtil.aesEncrypt(data,key);
    }

    override fun decryptData(data: String, key: String, tag: String?): String? {
        return AESUtil.aesDecrypt(data,key);
    }

}
