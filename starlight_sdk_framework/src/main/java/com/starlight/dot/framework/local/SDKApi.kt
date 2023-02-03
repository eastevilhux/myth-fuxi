package com.starlight.dot.framework.local

import android.content.Context
import android.graphics.Color
import com.starlight.dot.framework.R
import com.starlight.dot.framework.utils.SLog
import com.starlight.dot.framework.utils.dip2px
import com.starlight.dot.framework.utils.toJSON
import java.lang.NullPointerException

abstract class SDKApi<B : SDKApiBuilder<*>> : BaseSDKApi{
    val context : Context;
    var haveTitle : Boolean = true;
    var title : String? = null;
    var titleBackgroundColor : String = "#000000";
    var titleTextColor : String = "#000000"
    var titleSize : Int = 0;
    var backResouceId : Int = 0;
    var backType : Int = 0;
    var backText : String? = null;
    var backTextSize : Int = 0;
    var backTextStyle : Int = 0;
    var backTextColor : String = "#101010"
    var itemBackGroundColor : String = "#FFFFFF";
    var itemTitleTextColor : String = "#000000"
    var itemDetailColor : String = "#101010";
    var itemTitleTextSize : Int = 0;
    var itemDetailSize : Int = 0;
    var itemOptionColor : String = "#737373";
    var itemOptionSize : Int = 0;
    var itemLineColor : String = "#C8C8C8";
    var dividerItemColor : String = "#F4F4F4";
    var menuType : Int = 0;
    var menuText : String? = null;
    var menuTextSize : Int = 0;
    var menuImageResId : Int = 0;
    var menuTextStyle : Int = 0;
    var menuTextColor : String = "#000000"
    var apiCode : Int = 1000;
    var isFinish : Boolean = false;
    var themeColor : String = "#FFFFFF"
    var cls: Class<*>;

    constructor(builder : B){
        this.context = builder.context;
        this.cls = builder.cls;
        this.haveTitle = builder.haveTitle;
        this.title = builder.title;
        this.titleBackgroundColor = builder.titleBackgroundColor;
        this.titleTextColor = builder.titleTextColor;
        this.titleSize = if(builder.titleSize <= 0){
            15;
        }else{
            builder.titleSize;
        }
        this.backResouceId = if(builder.backResouceId <= 0){
            R.drawable.icon_sl_back;
        }else{
            builder.backResouceId;
        }
        this.backText = builder.backText;
        this.backType = builder.backType;
        this.backTextStyle = builder.backTextStyle;
        this.backTextSize = if(builder.backTextSize <= 0){
            12;
        }else{
            builder.backTextSize;
        }
        this.backTextColor = builder.backTextColor;
        this.itemBackGroundColor = builder.itemBackGroundColor;
        this.itemTitleTextColor = builder.itemTitleTextColor;
        this.itemLineColor = builder.itemLineColor;
        this.dividerItemColor = builder.dividerItemColor;
        this.itemTitleTextSize = if(builder.itemTitleTextSize <= 0){
            14
        }else{
            builder.itemTitleTextSize
        }
        this.itemDetailColor = builder.itemDetailColor;
        this.itemDetailSize = if(builder.itemDetailSize <= 0){
            13;
        }else{
            builder.itemDetailSize
        }
        this.itemOptionColor = builder.itemOptionColor
        this.itemOptionSize = if(builder.itemOptionSize <= 0){
            12;
        }else{
            builder.itemOptionSize
        }
        this.menuType = builder.menuType;
        this.menuImageResId = builder.menuImageResId;
        this.menuTextSize = if(builder.menuTextSize <= 0){
            12;
        }else{
            builder.menuTextSize;
        }
        this.menuTextStyle = builder.menuTextStyle;
        this.menuText = builder.menuText;
        this.menuTextColor = builder.menuTextColor;
        this.themeColor = builder.themeColor;
        isFinish = builder.isFinish;
        initApi(builder);
    }

    open fun api(){
        val map = HashMap<String,Any?>();
        map.put("isFinish",isFinish);
        map.put("title",title);
        map.put("haveTitle",haveTitle);
        map.put("titleBackgroundColor",titleBackgroundColor);
        map.put("titleTextColor",titleTextColor);
        map.put("titleSize",titleSize);
        map.put("backResouceId",backResouceId);
        map.put("backType",backType);
        map.put("backText",backText);
        map.put("backTextSize",backTextSize);
        map.put("backTextStyle",backTextStyle);
        map.put("backTextColor",backTextColor);
        map.put("itemBackGroundColor",itemBackGroundColor);
        map.put("itemTitleTextColor",itemTitleTextColor);
        map.put("itemTitleTextSize",itemTitleTextSize);
        map.put("itemDetailColor",itemDetailColor);
        map.put("itemDetailSize",itemDetailSize);
        map.put("itemOptionColor",itemOptionColor);
        map.put("itemOptionSize",itemOptionSize);
        map.put("menuType",menuType);
        map.put("menuText",menuText);
        map.put("menuTextSize",menuTextSize);
        map.put("menuImageResId",menuImageResId);
        map.put("menuTextStyle",menuTextStyle);
        map.put("menuTextColor",menuTextColor);
        map.put("itemLineColor",itemLineColor);
        map.put("dividerItemColor",dividerItemColor);
        map.put("themeColor",themeColor);
        SLog.d(TAG,"api");
        requestApi(map);
    }

    fun apiParams(): String? {
        val map = HashMap<String,Any?>();
        map.put("isFinish",isFinish);
        map.put("title",title);
        map.put("haveTitle",haveTitle);
        map.put("titleBackgroundColor",titleBackgroundColor);
        map.put("titleTextColor",titleTextColor);
        map.put("titleSize",titleSize);
        map.put("backResouceId",backResouceId);
        map.put("backType",backType);
        map.put("backText",backText);
        map.put("backTextSize",backTextSize);
        map.put("backTextStyle",backTextStyle);
        map.put("backTextColor",backTextColor);
        map.put("itemBackGroundColor",itemBackGroundColor);
        map.put("itemTitleTextColor",itemTitleTextColor);
        map.put("itemTitleTextSize",itemTitleTextSize);
        map.put("itemDetailColor",itemDetailColor);
        map.put("itemDetailSize",itemDetailSize);
        map.put("itemOptionColor",itemOptionColor);
        map.put("itemOptionSize",itemOptionSize);
        map.put("menuType",menuType);
        map.put("menuText",menuText);
        map.put("menuTextSize",menuTextSize);
        map.put("menuImageResId",menuImageResId);
        map.put("menuTextStyle",menuTextStyle);
        map.put("menuTextColor",menuTextColor);
        map.put("itemLineColor",itemLineColor);
        map.put("dividerItemColor",dividerItemColor);
        map.put("themeColor",themeColor);
        SLog.d(TAG,"apiParams");
        return map.toJSON();
    }

    protected open fun initApi(builder : B){
        SLog.d(TAG,"initApi");
    }

    protected abstract fun requestApi(params : HashMap<String,Any?>);

    fun titleBackgroundColor(): Int {
        return Color.parseColor(titleBackgroundColor);
    }

    fun itemLineColor(): Int {
        return Color.parseColor(itemLineColor);
    }

    fun dividerItemColor(): Int {
        return Color.parseColor(dividerItemColor);
    }

    fun mainThemeColor(): Int {
        return Color.parseColor(themeColor);
    }

    companion object{
        private const val TAG = "SL_SDKApi==>";
    }
}

