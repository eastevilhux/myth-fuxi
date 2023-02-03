package com.starlight.dot.framework.commons

abstract class PageRequest {
    var page : Long = 1;
    var limit : Long = 15;

    /**
     * 排序方式
     */
    var order : String = "asc";

    var orderField : String = "id";

}
