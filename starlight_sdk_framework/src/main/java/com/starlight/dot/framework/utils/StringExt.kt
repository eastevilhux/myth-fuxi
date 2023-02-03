package com.starlight.dot.framework.utils

import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import java.util.regex.Matcher
import java.util.regex.Pattern

fun String?.isEmpty(): Boolean {
    if(this == null){
        return true;
    }
    val s = this.replace(" ","");
    if("".equals(this)){
        return true;
    }
    if("null".equals(s)){
        return true;
    }
    return false;
}

fun String?.isNotEmpty(): Boolean {
    if(this == null){
        return false;
    }
    return !this.isEmpty();
}


/**
 * 修改字符串中某几个字符串的颜色
 */
fun String.changeTextColor(
    color: Int,
    vararg tag: String
): SpannableStringBuilder? {
    val s = SpannableStringBuilder(this)
    for (s1 in tag) {
        for (i in 0 until this.length) {
            if (s1.regionMatches(
                    0,
                    this,
                    i,
                    s1.length,
                    ignoreCase = false
                )
            ) {
                s.setSpan(
                    ForegroundColorSpan(color), i, i + s1.length,
                    SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE
                )
            }
        }
    }
    return s;
}

fun String.changeTextColor(
    color: Int,
    start:Int,
    end:Int
): SpannableStringBuilder?{
    val s = SpannableStringBuilder(this)
    s.setSpan(
        ForegroundColorSpan(color), start, end,
        SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE
    )
    return s;
}

/**
 * 星号展示手机号码
 * create by Eastevil at 2023/1/16 16:48
 * @author Eastevil
 * @param haveTrim
 *      是否有空格
 * @param haveLine
 *      手机号码中是否携带-或者_
 * @return
 *      星号手机号码
 */
fun String.asteriskMobile(haveTrim:Boolean = false,haveLine:Boolean = false) : String{
    if(!this.isMobile(haveTrim=haveTrim,haveLine = haveLine)){
        return this;
    }
    var temp = this;
    if(haveTrim){
        if(this.indexOf(" ") != -1)
            temp = temp.replace(" ","");
    }
    if(haveLine){
        if(this.indexOf("-") != -1)
            temp = temp.replace("-","");
        if(this.indexOf("_") != -1)
            temp = temp.replace("_","");
    }
    var start = temp.substring(0,3);
    var end = temp.subSequence(temp.length - 4,temp.length);
    return "${start} **** ${end}";
}


/**
 * 判断字符串是否为正常的中国大陆手机号码
 * @author hux
 * @since 1.0.0
 * @param haveLine
 *     手机号码中是否包含-或者_也可以视为正确的手机号码
 * @param haveTrim
 *      手机号码中是否包含空格也可视为正确的手机号码
 * @return
 *      是否是正确的手机号码
 */
fun String.isMobile(haveTrim:Boolean = false,haveLine:Boolean = false) : Boolean{
    if(this.isEmpty()){
        return false;
    }
    var temp = this;
    if(haveTrim){
        if(this.indexOf(" ") != -1)
            temp = temp.replace(" ","");
    }
    if(haveLine){
        if(this.indexOf("-") != -1)
            temp = temp.replace("-","");
        if(this.indexOf("_") != -1)
            temp = temp.replace("_","");
    }
    val regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(14[5-9])|(166)|(19[8,9])|)\\d{8}$"
    val p = Pattern.compile(regExp)
    val m = p.matcher(temp)
    return m.matches()
}


/**
 * 判断字符串是否是一个正确的邮箱地址
 * @author hux
 * @since 1.0.0
 * @return
 *      是否是正确的邮箱地址
 */
fun String.isEmail() : Boolean{
    if(this.isEmpty()){
        return false;
    }
    val pat = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$";
    val p = Pattern.compile(pat) //实例化Pattern类
    val m: Matcher = p.matcher(this) //验证内容是否合法
    return m.matches();
}
