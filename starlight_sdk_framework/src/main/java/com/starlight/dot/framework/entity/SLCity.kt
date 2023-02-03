package com.starlight.dot.framework.entity

import java.io.Serializable

class SLCity : Serializable {
    var id: Long = 0;
    var code: Int = 0
    var cityName: String? = null
    var pcode: Int = 0

    var abbreviation: String? = null

    var alphabetic : String? = null;
    var hot : Int = 0;

    /**
     * 1:省份
     * 2:城市
     * 3:区域
     */
    var type: Int = 0

    companion object {
        private const val serialVersionUID = 2042063859090731219L
    }
}
