package com.starlight.dot.framework.network.entity

import com.starlight.dot.framework.network.NetworkHelper

class Result<T>(var data:T? = null) {

    var code = 0;
    var tag : String? = null;
    var msg : String? = null;
    var encryption : Boolean = false;
    var state : Boolean = false;

    /**
     * 是否需要进行base64解密
     */
    var base64 : Boolean = false;

    /**
     * 是否需要进行URLEcode解密
     */
    var urlEncoder : Boolean = false;

    /**
     * 扩展参数
     */
    var extended : String? = null;

    val isSuccess
        get() = code == NetworkHelper.instance().httpConfig().successCode();

}
