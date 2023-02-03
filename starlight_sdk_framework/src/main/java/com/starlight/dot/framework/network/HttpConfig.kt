package com.starlight.dot.framework.network

import okhttp3.Interceptor
import okhttp3.MediaType
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.*

class HttpConfig private constructor(builder: Builder) {
    //请求服务器地址URL
    private var SERVICE_URL = "http://localhost:8080/appservice/"

    //请求Token地址URL
    private var TOKEN_URL = "http://localhost:8080/token"

    private var CODE_SUCCESS = 66;
    private var CODE_ERROR = -1;
    private var CODE_LOGIN = -2;
    private var CODE_NETWORK = 404;
    private var CODE_EMPTY = 44;
    private var CODE_SERVICE_ERROR = -4;
    private var charset : String = "UTF-8";
    private var TIME_OUT : Long = 20L;
    private var NEED_URL_DECODE : Boolean = true;
    private var NEED_BASE64 : Boolean = true;

    //Retrofit缓存时间为1小时
    private var MAX_AGE : Int = 60;

    private var HTTP_CHARSET = Charset.forName(charset);

    private var MEDIA_TYPE = "multipart/form-data";

    private var APPID : String? = null;

    private var ENCRYPT_PARAMS : Boolean = true;

    /**
     * 数据解密类型，在回调调用方的时候回传给调用者
     */
    private var DECRYPT_TYPE : Int = 0;

    private var INTERCEPTOR_LIST : MutableList<Interceptor>? = null;

    init {
        SERVICE_URL = builder.SERVICE_URL
        TOKEN_URL = builder.TOKEN_URL
        CODE_SUCCESS = builder.CODE_SUCCESS
        CODE_ERROR = builder.CODE_ERROR
        CODE_EMPTY = builder.CODE_EMPTY
        CODE_LOGIN = builder.CODE_LOGIN
        CODE_NETWORK = builder.CODE_NETWORK
        CODE_SERVICE_ERROR = builder.CODE_SERVICE_ERROR
        TIME_OUT = builder.TIME_OUT
        MAX_AGE = builder.MAX_AGE
        charset = builder.charset
        NEED_URL_DECODE = builder.NEED_URL_DECODE
        NEED_BASE64 = builder.NEED_BASE64
        HTTP_CHARSET = Charset.forName(builder.charset)
        MEDIA_TYPE = ""
        APPID = builder.appId;
        ENCRYPT_PARAMS = builder.encryptParams;
        DECRYPT_TYPE = builder.decrypeType;
        INTERCEPTOR_LIST = builder.INTERCEPTOR_LIST;
    }

    class Builder {
        //请求服务器地址URL
        internal var SERVICE_URL : String;
        internal var appId : String?;

        constructor(serviceUrl: String,appId : String){
            SERVICE_URL = serviceUrl;
            this.appId = appId;
        }

        constructor(serviceUrl: String){
            SERVICE_URL = serviceUrl;
            appId = null;
        }

        //请求Token地址URL
        internal var TOKEN_URL = "http://localhost:8080/token"

        internal var CODE_SUCCESS = 66;
        internal var CODE_ERROR = -1;
        internal var CODE_LOGIN = -2;
        internal var CODE_NETWORK = 404;
        internal var CODE_EMPTY = 704;
        internal var CODE_SERVICE_ERROR = -4;
        internal var charset : String = "UTF-8";
        internal var TIME_OUT : Long = 20L;
        internal var NEED_URL_DECODE : Boolean = true;
        internal var NEED_BASE64 : Boolean = true;
        internal var MEDIA_TYPE : String = "multipart/form-data";

        //Retrofit缓存时间为1小时
        internal var MAX_AGE : Int = 60;

        internal var healder : TreeMap<String,String>? = null;

        internal var encryptParams : Boolean = true;
        internal var decrypeType : Int = 0;
        internal var INTERCEPTOR_LIST : MutableList<Interceptor>? = null;

        fun tokenUrl(tokenUrl:String) : Builder {
            TOKEN_URL = tokenUrl;
            return this;
        }

        fun successCode(code:Int): Builder {
            this.CODE_SUCCESS = code;
            return this;
        }

        fun loginErrorCode(code:Int): Builder {
            CODE_LOGIN = code;
            return this;
        }

        fun networkErrorCode(code:Int): Builder {
            CODE_NETWORK = code;
            return this;
        }

        fun serviceErrorCode(code:Int): Builder {
            CODE_ERROR = code;
            return this;
        }

        fun emptyErrorCode(code:Int): Builder {
            CODE_EMPTY = code;
            return this;
        }

        fun systemErrorCode(code:Int): Builder {
            CODE_SERVICE_ERROR = code;
            return this;
        }

        fun charset(charset:String): Builder {
            this.charset = charset;
            return this;
        }

        fun timeOut(time:Long): Builder {
            TIME_OUT = time;
            return this;
        }

        fun maxAge(maxAge:Int): Builder {
            MAX_AGE = maxAge;
            return this;
        }

        fun addHealder(key:String,value:String){
            healder?:let {
                healder = TreeMap();
            }
            healder!![key] = value;
        }

        fun needURLDecoder(decodeFlag : Boolean): Builder {
            NEED_URL_DECODE = decodeFlag;
            return this;
        }

        fun needBase64(base64Flag : Boolean): Builder {
            NEED_BASE64 = base64Flag;
            return this;
        }

        fun mediaType(mediaType: String): Builder {
            this.MEDIA_TYPE = mediaType;
            return this;
        }

        fun encryptParams(encryptParams : Boolean): Builder {
            this.encryptParams = encryptParams;
            return this;
        }

        fun decrypeType(decrypeType : Int): Builder {
            this.decrypeType = decrypeType;
            return this;
        }

        fun addInterceptor(interceptor: Interceptor): Builder {
            if(INTERCEPTOR_LIST == null){
                INTERCEPTOR_LIST = mutableListOf();
            }
            INTERCEPTOR_LIST?.add(interceptor);
            return this;
        }

        fun builder() : HttpConfig {
            return HttpConfig(this);
        }

    }

    fun baseUrl(): String {
        return SERVICE_URL;
    }

    fun successCode(): Int {
        return CODE_SUCCESS;
    }

    fun loginErrorCode(): Int {
        return CODE_LOGIN;
    }

    fun emptyCode(): Int {
        return CODE_EMPTY;
    }

    fun networkErrorCode(): Int {
        return CODE_NETWORK;
    }

    fun serviceErrorCode(): Int {
        return CODE_SERVICE_ERROR;
    }

    fun httpCharset(): Charset{
        return HTTP_CHARSET;
    }

    fun charset(): String {
        return charset;
    }

    fun timeOut(): Long {
        return TIME_OUT;
    }

    fun maxAge(): Int {
        return MAX_AGE;
    }

    fun isNeedURLDecode(): Boolean {
        return NEED_URL_DECODE;
    }

    fun isNeedBase64(): Boolean {
        return NEED_BASE64;
    }

    fun mediaType(): String {
        return MEDIA_TYPE;
    }

    fun isEncryptParams(): Boolean {
        return ENCRYPT_PARAMS;
    }

    fun decrypeType(): Int {
        return DECRYPT_TYPE;
    }

    fun interceptorList(): MutableList<Interceptor>? {
        return INTERCEPTOR_LIST;
    }

    fun setMediaType(mediaType : String){
        this.MEDIA_TYPE = mediaType;
    }

}
