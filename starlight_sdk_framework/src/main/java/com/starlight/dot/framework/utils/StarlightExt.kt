package com.starlight.dot.framework.utils

import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URLDecoder
import java.net.URLEncoder

/**
 * 把当前字符串转换成具体的对象
 */
inline fun <reified T> String.parseJSON(): T = JsonUtil.instance.getGson().parseJSON(this)


inline fun <reified T> Gson.parseJSON(json: String?): T {
    return this.fromJson<T>(json, object : TypeToken<T>() {}.type)
}

/**
 * 转换成json格式
 */
fun Any.toJSON(): String? = JsonUtil.instance.getGson().toJson(this);

fun String.md5(): String? {
    return MD5Util.encryptDate(this);
}

fun String.urlEcode(enc : String? = "UTF-8"): String {
    return URLEncoder.encode(this,enc);
}

fun String.urlDecode(enc : String? = "UTF-8"): String {
    return URLDecoder.decode(this,enc);
}
