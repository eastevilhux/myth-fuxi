package com.starlight.dot.framework.utils

import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

/**
 * 默认时间格式化值，yyyy-MM-dd HH:mm:ss
 */
const val DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss"

/**
 * 获取当前时间，并对时间进行格式化处理
 * create by Eastevil at 2022/9/14 10:19
 * @author Eastevil
 * @param sdf
 *      指定的格式
 * @return
 *      格式化后的当前时间
 */
fun currentTime(sdf: String) : String{
    var sdf = SimpleDateFormat(sdf);
    return sdf.format(Date());
}

fun currentYear(): Int {
    val c = Calendar.getInstance();
    return c.get(Calendar.YEAR);
}

/**
 * 获取当前月份
 * create by Eastevil at 2022/9/19 11:02
 * @author Eastevil
 * @return
 *      当前月份，该值为[java.util.Calendar]对象获取月份值加1，即：1-12
 */
fun currentChineseMonth(): Int {
    val c = Calendar.getInstance();
    return c.get(Calendar.MONTH) + 1;
}

/**
 * 获取当前月份
 * create by Eastevil at 2022/9/19 11:01
 * @author Eastevil
 * @return
 *     前月份
 */
fun currentMonth():Int{
    val c = Calendar.getInstance();
    return c.get(Calendar.MONTH);
}

fun Calendar.getYear(): Int {
    return this.get(Calendar.YEAR);
}

fun Calendar.getMonth(): Int {
    return this.get(Calendar.MONTH);
}

fun Calendar.getDay(): Int {
    return this.get(Calendar.DAY_OF_MONTH);
}

/**
 * 获取当前时间对应的小时值，24小时制
 * create by Eastevil at 2022/9/19 11:04
 * @author Eastevil
 * @return
 *      当前时间对应的小时值，24小时制
 */
fun Calendar.getHour(): Int {
    return this.get(Calendar.HOUR_OF_DAY);
}

fun Calendar.getMinute(): Int {
    return this.get(Calendar.MINUTE);
}

fun Calendar.getSecond(): Int {
    return this.get(Calendar.SECOND);
}

/**
 * 获取当前月份，如果小于10，则在前位补0
 * create by Eastevil at 2022/9/20 10:30
 * @author Eastevil
 * @param
 * @return
 */
fun Calendar.getMonthText(): String {
    return this.get(Calendar.MONTH).lessTenText();
}

/**
 * 获取当前日期，如果小于10，则在前位补0
 */
fun Calendar.getDayText(): String {
    return this.get(Calendar.DAY_OF_MONTH).lessTenText();
}

/**
 * 获取当前小时，如果小于10，则在前位补0
 * create by Eastevil at 2022/9/20 10:30
 * @author Eastevil
 * @param
 * @return
 */
fun Calendar.getHourText(): String {
    return this.get(Calendar.HOUR_OF_DAY).lessTenText();
}

/**
 * 获取当前分钟，如果小于10，则在前位补0
 */
fun Calendar.getMinuteText(): String {
    return this.get(Calendar.MINUTE).lessTenText();
}

/**
 * 获取当前妙，如果小于10，则在前位补0
 */
fun Calendar.getSecondText(): String {
    return this.get(Calendar.SECOND).lessTenText();
}

fun Calendar.getWeek(): Int {
    return this.get(Calendar.DAY_OF_WEEK);
}

fun Calendar.chineseWeekText(): String {
    val w = this.getWeek();
    return when(w){
        1 -> "星期一"
        2 -> "星期二"
        3 -> "星期三"
        4 -> "星期四"
        5 -> "星期五"
        6 -> "星期六"
        7 -> "星期日"
        else-> "星期"
    }
}

/**
 * 根据[Calendar]对象，获取对应得最小天数值
 * create by Eastevil at 2022/9/20 10:44
 * @author Eastevil
 * @return
 *      最小天数值
 */
fun Calendar.getMinDay(): Int {
    return this.getActualMinimum(Calendar.DAY_OF_MONTH);
}

/**
 * 根据[Calendar]对象，获取对应得最大天数值
 * create by Eastevil at 2022/9/20 10:45
 * @author Eastevil
 * @return
 *      最大天数值
 */
fun Calendar.getMaxDay() : Int{
    return this.getActualMaximum(Calendar.DAY_OF_MONTH);
}

/**
 * 根据[Calendar]对象，获取对应的月份第一天是星期几，返回的值范围为1-7
 * create by Eastevil at 2022/9/20 10:51
 * @author Eastevil
 * @return
 *      月份第一天是星期几，对应返回值已减1
 */
fun Calendar.monthFirstDayWeek(): Int {
    val minDay = this.getMinDay();
    this.set(Calendar.DAY_OF_MONTH, minDay);
    val w = this.get(Calendar.DAY_OF_WEEK);
    if(w == 1){
        return 7;
    }else{
        return w-1;
    }
}

/**
 * 根据[Calendar]对象，获取对应的月份最后一天是星期几,返回的值范围为1-7
 * create by Eastevil at 2022/9/20 10:53
 * @author Eastevil
 * @return
 *      月份最后一天是星期几,对应返回值已减1
 */
fun Calendar.monthLastDayWeek(): Int {
    val maxDay = this.getMaxDay();
    this.set(Calendar.DAY_OF_MONTH, maxDay);
    val w = this.get(Calendar.DAY_OF_WEEK);
    if(w == 1){
        return 7;
    }else{
        return w-1;
    }
}

/**
 * 日期天数增加指定值
 * create by Eastevil at 2022/9/20 10:55
 * @author Eastevil
 * @param value
 *      需要增加的值
 * @return
 *      增加后的[Calendar]对象
 */
fun Calendar.addDay(value: Int): Calendar {
    this.add(Calendar.DAY_OF_MONTH, value);
    val c = this;
    return c;
}

/**
 * 月份增加指定值
 * create by Eastevil at 2022/9/20 10:56
 * @author Eastevil
 * @param value
 *      需要增加的值
 * @return
 *      增加后的[Calendar]对象
 */
fun Calendar.addMonth(value: Int): Calendar {
    this.add(Calendar.MONTH, value);
    val c = this;
    return c;
}

fun Calendar.beforNow(): Boolean {
    val cc = Calendar.getInstance();
    cc.time = Date(System.currentTimeMillis());
    return this.before(cc);
}

fun Calendar.afterNow(): Boolean {
    val cc = Calendar.getInstance();
    cc.time = Date(System.currentTimeMillis());
    return this.after(cc);
}

/**
 * 格式化时间
 * create by Eastevil at 2022/9/19 11:09
 * @author Eastevil
 * @return
 *      按默认时间格式格式化后的时间字符串
 */
fun Long.format(): String {
    val sdf = SimpleDateFormat(DEFAULT_FORMAT);
    val date = Date(this);
    return sdf.format(date)
}

/**
 * 按指定格式进行时间格式化处理
 * create by Eastevil at 2022/9/19 11:10
 * @author Eastevil
 * @param format
 *      时间格式
 * @return
 *      指定格式进行时间格式化处理后的时间字符串
 */
fun Long.format(format: String): String {
    val sdf = SimpleDateFormat(format);
    val date = Date(this);
    return sdf.format(date)
}

/**
 * 传入的参数小于10，则在前面补0
 * create by Eastevil at 2022/9/20 10:27
 * @author Eastevil
 * @param num
 *      值
 * @return
 *      如果小于10，则前位补0，否则为传入值
 */
private fun Int.lessTenText(): String {
    return if(this < 10) "0${this}" else this.toString();
}

/**
 * 将Long值的毫秒数值转换为时：分：秒
 * create by Eastevil at 2022/11/2 17:31
 * @author Eastevil
 * @param hourUnit
 *      小时单位
 * @param minuteUnit
 *      分钟单位
 * @param secondUnit
 *      秒单位
 * @param connUnit
 *      连接单位
 * @return
 *      转换后的时间
 */
fun Long.durationTime(hourUnit : String, minuteUnit : String,
                      secondUnit : String,connUnit : String): String {
    val ss = 1000L
    val mi = ss * 60L
    val hh = mi * 60L

    val hour = this / hh;
    val minute = (this - hour * hh)/mi;
    val second = (this - hour * hh - minute * mi)/ss;
    val sb = StringBuilder();
    if(hour < 10){
        sb.append("0").append(hour);
    }else{
        sb.append(hour);
    }
    sb.append(hourUnit);
    sb.append(connUnit);
    if(minute < 10){
        sb.append("0").append(minute);
    }else{
        sb.append(minute);
    }
    sb.append(minuteUnit);
    sb.append(connUnit);
    if(second < 10){
        sb.append("0").append(second);
    }else{
        sb.append(second);
    }
    return sb.toString();
}

/**
 * 格式化时间戳
 * create by Eastevil at 2022/11/4 17:44
 * @author Eastevil
 * @param sdf
 *      格式
 * @return
 *      指定格式时间文本
 */
fun Long.formatTime(sdf:String) : String{
    var sdf = SimpleDateFormat(sdf);
    return sdf.format(Date(this));
}
