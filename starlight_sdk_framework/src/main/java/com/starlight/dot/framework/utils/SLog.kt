package com.starlight.dot.framework.utils

import android.util.Log

object SLog {
    private var debug = false
    private const val TAG = "SL_H_APP_LOGS"
    private const val MAX_STR_LENGTH = 1024

    fun e(tag: String?, msg: String?) {
        if (isDebug()) {
            Log.e(tag, msg?:"")
        }
    }

    fun i(tag: String?, msg: String?) {
        if (isDebug()) {
            Log.i(tag, msg?:"")
        }
    }

    fun d(tag: String?, msg: String?) {
        if (isDebug()) {
            Log.d(tag, msg?:"")
        }
    }

    fun w(tag: String?, msg: String?) {
        if (isDebug()) {
            Log.w(tag, msg?:"")
        }
    }

    fun isDebug(): Boolean {
        return debug;
    }

    fun setDebug(debug: Boolean) {
        SLog.debug = debug
    }

    fun longI(tag: String,message: String? = null) {
        var msg = message?:"";
        val max_str_length = MAX_STR_LENGTH - tag.length
        while (msg.length > max_str_length) {
            i(tag, "long_log_flag_${msg.substring(0, max_str_length)}")
            msg = msg.substring(max_str_length)
        }
        i(tag, msg)
    }

    fun longD(tag: String, message: String?) {
        var msg = message?:"";
        val max_str_length = MAX_STR_LENGTH - tag.length
        while (msg.length > max_str_length) {
            d(tag, "long_log_flag_${msg.substring(0, max_str_length)}")
            msg = msg.substring(max_str_length)
        }
        d(tag, msg)
    }

    fun longW(tag: String, message: String?) {
        var msg = message?:"";
        val max_str_length = MAX_STR_LENGTH - tag.length
        while (msg.length > max_str_length) {
            w(tag, "long_log_flag_${msg.substring(0, max_str_length)}")
            msg = msg.substring(max_str_length)
        }
        w(tag, msg)
    }
}
