package com.starlight.dot.framework.entity

import android.graphics.Color
import com.starlight.dot.framework.utils.dip2px
import com.starlight.dot.framework.widget.popup.ListPopup
import java.io.File

open class BaseItem{
    var select : Boolean = false;
}

abstract class SLItem<B : SLBuilder<*>> : BaseItem {
    var id : Long = 0;
    var code : String? = null;
    var itemText : String;
    var haveSubtext : Boolean = false;
    var subText : String? = null;
    var iconUrl : String? = null;
    var iconResourceId : Int = 0;
    var iconFile : File? = null;
    var textColor : String = "#101010";
    var subTextSize : Int = 12;
    var subTextColor : String = "#C8C8C8";

    /**
     * 图标icon调用类型
     * 0:不显示icon，1:本地资源类型，2：网络URL地址类型，3：文件类型
     */
    var iconType : Int;

    var textSize : Int = 0;


    constructor(builder: B){
        this.id = builder.id;
        this.code = builder.code;
        this.itemText = builder.itemText;
        this.iconType = builder.iconType
        this.haveSubtext = builder.haveSubtext;
        this.subText = builder.subText;
        this.iconUrl = builder.iconUrl;
        this.iconResourceId = builder.iconResourceId;
        this.iconFile = builder.iconFile;
        this.textColor = builder.textColor;
        this.subTextSize = builder.subTextSize;
        this.subTextColor = builder.subTextColor;
        this.textSize = builder.textSize;
    }
}

abstract class SLBuilder<S : BaseItem>{
    internal var id : Long = 0;
    internal var code : String? = null;
    internal var itemText : String;
    internal var iconType : Int = 0;
    internal var haveSubtext : Boolean = false;
    internal var subText : String? = null;
    internal var iconUrl : String? = null;
    internal var iconResourceId : Int = 0;
    internal var iconFile : File? = null;
    internal var textSize : Int = 13;
    internal var textColor : String = "#101010";
    internal var subTextSize : Int = 12;
    internal var subTextColor : String = "#C8C8C8";

    constructor(itemText : String){
        this.itemText = itemText;
    }

    fun id(id : Long): SLBuilder<S> {
        this.id = id;
        return this;
    }

    fun code(code : String): SLBuilder<S> {
        this.code = code;
        return this;
    }

    fun iconType(iconType : Int): SLBuilder<S> {
        this.iconType = iconType;
        return this;
    }

    fun haveSubtext(haveSubtext : Boolean): SLBuilder<S> {
        this.haveSubtext = haveSubtext;
        return this;
    }

    fun subText(subText : String): SLBuilder<S> {
        this.subText = subText;
        return this;
    }

    fun subTextSize(subTextSize : Int): SLBuilder<S> {
        this.subTextSize = subTextSize;
        return this;
    }

    fun subTextColor(subTextColor : String): SLBuilder<S> {
        this.subTextColor = subTextColor;
        return this;
    }

    fun iconUrl(iconUrl : String): SLBuilder<S> {
        this.iconUrl = iconUrl;
        return this;
    }

    fun iconResourceId(iconResourceId : Int): SLBuilder<S> {
        this.iconResourceId = iconResourceId;
        return this;
    }

    fun iconFile(iconFile : File): SLBuilder<S>{
        this.iconFile = iconFile;
        return this;
    }

    fun textSize(textSize : Int): SLBuilder<S> {
        this.textSize = textSize;
        return this;
    }

    fun textColor(textColor : String): SLBuilder<S> {
        this.textColor = textColor;
        return this;
    }

    abstract fun builder() : S;
}
