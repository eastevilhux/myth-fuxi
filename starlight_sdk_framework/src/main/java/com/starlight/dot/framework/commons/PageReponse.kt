package com.starlight.dot.framework.commons

abstract class PageReponse<T>{
    /**
     * 总记录数
     */
    var total : Int = 0;

    /**
     * 列表数据
     */
    var list : MutableList<T>? = null;
}
