package com.starlight.dot.framework.local

import android.content.Context
import com.starlight.dot.framework.R
import com.starlight.dot.framework.widget.TitleLayout

abstract class SDKApiBuilder<A : BaseSDKApi> {

    internal var title : String? = null;
    internal var haveTitle : Boolean = true;
    internal var titleBackgroundColor : String = "#FA960A";
    internal var titleTextColor : String = "#000000"
    internal var titleSize : Int = 0;
    internal var backResouceId : Int = 0;
    internal var backType : Int = TitleLayout.BackType.TYPE_IMAGE;
    internal var backText : String? = null;
    internal var backTextSize : Int = 12;
    internal var backTextStyle : Int = 3;
    internal var backTextColor : String = "#101010";
    internal var itemBackGroundColor : String = "#FFFFFF";
    internal var itemTitleTextColor : String = "#000000"
    internal var itemTitleTextSize : Int = 0;
    internal var itemDetailColor : String = "#101010";
    internal var itemDetailSize : Int = 0;
    internal var itemOptionColor : String = "#737373";
    internal var itemOptionSize : Int = 0;
    internal var itemLineColor : String = "#C8C8C8";
    internal var dividerItemColor : String = "#F4F4F4";
    internal var menuType : Int = TitleLayout.MenuType.TYPE_NONE;
    internal var menuText : String? = null;
    internal var menuTextSize : Int = 0;
    internal var menuImageResId : Int = 0;
    internal var menuTextColor : String = "#000000"
    internal var menuTextStyle : Int = 3;
    internal var isFinish : Boolean = false;
    internal var themeColor : String = "#FFFFFF";
    internal var context : Context;
    internal var cls: Class<*>;

    constructor(context : Context, cls: Class<*>){
        this.context = context;
        this.cls = cls;
    }

    fun haveTitle(haveTitle : Boolean): SDKApiBuilder<A> {
        this.haveTitle = haveTitle;
        return this;
    }

    fun isFinish(isFinish : Boolean): SDKApiBuilder<A> {
        this.isFinish = isFinish;
        return this;
    }

    fun title(title : String): SDKApiBuilder<A> {
        this.title = title;
        return this;
    }

    fun titleBackgroundColor(titleBackgroundColor : String): SDKApiBuilder<A> {
        this.titleBackgroundColor = titleBackgroundColor;
        return this;
    }

    fun titleTextColor(titleTextColor : String): SDKApiBuilder<A> {
        this.titleTextColor = titleTextColor;
        return this;
    }

    fun titleSize(titleSize : Int): SDKApiBuilder<A> {
        this.titleSize = if(titleSize <= 0){
            15;
        }else{
            titleSize
        }
        return this;
    }

    fun backResouceId(backResouceId : Int): SDKApiBuilder<A> {
        this.backResouceId = if(backResouceId <= 0){
            R.drawable.icon_sl_back
        }else{
            backResouceId;
        }
        return this;
    }

    fun itemLineColor(itemLineColor : String): SDKApiBuilder<A> {
        this.itemLineColor = itemLineColor
        return this;
    }

    fun dividerItemColor(dividerItemColor : String): SDKApiBuilder<A> {
        this.dividerItemColor = dividerItemColor;
        return this;
    }

    fun backType(backType : Int): SDKApiBuilder<A> {
        this.backType = backType;
        return this;
    }

    fun backText(backText : String): SDKApiBuilder<A> {
        this.backText = backText;
        return this;
    }

    fun backTextSize(backTextSize : Int): SDKApiBuilder<A> {
        this.backTextSize = backTextSize;
        return this;
    }

    fun backTextStyle(backTextStyle : Int): SDKApiBuilder<A> {
        this.backTextStyle = backTextStyle;
        return this;
    }

    fun backTextColor(backTextColor : String): SDKApiBuilder<A> {
        this.backTextColor = backTextColor;
        return this;
    }

    fun itemBackGroundColor(itemBackGroundColor : String): SDKApiBuilder<A> {
        this.itemBackGroundColor = itemBackGroundColor;
        return this;
    }

    fun itemTitleColor(itemTitleColor : String): SDKApiBuilder<A> {
        this.itemTitleTextColor = itemTitleColor;
        return this;
    }

    fun itemTitleSize(itemTitleTextSize : Int): SDKApiBuilder<A> {
        this.itemTitleTextSize = itemTitleTextSize;
        return this;
    }

    fun itemDetailColor(itemDetailColor : String): SDKApiBuilder<A> {
        this.itemDetailColor = itemDetailColor
        return this;
    }

    fun itemDetailSize(itemDetailSize : Int): SDKApiBuilder<A> {
        this.itemDetailSize = if(itemDetailSize <= 0){
            14;
        }else{
            itemDetailSize
        }
        return this;
    }

    fun itemOptionColor(itemOptionColor : String): SDKApiBuilder<A> {
        this.itemOptionColor = itemOptionColor;
        return this;
    }

    fun itemOptionSize(itemOptionSize :  Int): SDKApiBuilder<A> {
        this.itemOptionSize = if(itemOptionSize <= 0){
            12
        }else{
            itemOptionSize;
        }
        return this;
    }

    fun menuType(menuType : Int): SDKApiBuilder<A> {
        this.menuType = menuType;
        return this;
    }

    fun menuText(menuText : String): SDKApiBuilder<A> {
        this.menuText = menuText;
        return this;
    }

    fun menuTextSize(menuTextSize : Int): SDKApiBuilder<A> {
        this.menuTextSize = menuTextSize;
        return this;
    }

    fun menuIcon(menuImageResId : Int): SDKApiBuilder<A> {
        this.menuImageResId = menuImageResId;
        return this;
    }

    fun menuTextStyle(menuStyle : Int): SDKApiBuilder<A> {
        this.menuTextStyle = menuStyle;
        return this;
    }

    fun menuTextColor(menuTextColor : String): SDKApiBuilder<A> {
        this.menuTextColor = menuTextColor;
        return this;
    }

    fun themeColor(themeColor : String): SDKApiBuilder<A> {
        this.themeColor = themeColor;
        return this;
    }

    abstract fun builder() : A;
}
