package com.starlight.dot.framework.utils

import android.content.Context
import android.content.res.Resources
import android.os.Looper
import com.starlight.dot.framework.network.retrofit.GsonUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.security.SecureRandom
import java.util.*

private const val SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
private const val NUMBERS = "0123456789"
private val RANDOM: Random = SecureRandom()

/**
 * 当前是否在主线程
 */
fun isMainThread(): Boolean = Looper.myLooper() == Looper.getMainLooper();

/**
 * 通过协程  在主线程上运行
 */
fun mainThread(block: () -> Unit) = GlobalScope.launch(Dispatchers.Main) {
    block()
}

fun createRandomNumber(min: Int = 0, max: Int = 10): Int {
    return ((min + Math.random() * (max)).toInt())
}

/**
 * 随机生成指定长度的字母加数字组合字符串
 * @author Administrator
 * @createTime 2022/6/18 13:30
 * @param num
 * 指定生成位数
 * @return
 * 生成的字符串
 */
fun createRandomSymbols(num: Int): String? {
    var strLength = 0
    do {
        strLength = RANDOM.nextInt(num)
    } while (strLength == 0)
    val strChars = CharArray(strLength)
    for (index in strChars.indices) {
        strChars[index] = SYMBOLS[RANDOM.nextInt(SYMBOLS.length)]
    }
    val s1 = String(strChars)
    val numLength = num - strLength
    val numChars = CharArray(numLength)
    for (index in numChars.indices) {
        numChars[index] = NUMBERS[RANDOM.nextInt(NUMBERS.length)]
    }
    val s2 = String(numChars)
    return s1 + s2
}

fun Int.dip2px(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (this * scale + 0.5f).toInt();
}

fun Int.sp2px(): Int {
    val fontScale = Resources.getSystem().displayMetrics.scaledDensity
    return (this * fontScale + 0.5f).toInt()
}

fun Int.px2dip(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (this / scale + 0.5f).toInt()
}

fun getScreenSize(content: Context) : Array<Int> {
    var dm = content.resources.displayMetrics;
    return arrayOf(dm.widthPixels, dm.heightPixels);
}

fun delay(long: Long, scope: GlobalScope = GlobalScope, block: () -> Unit) = scope.launch(
    Dispatchers.Main
) {
    kotlinx.coroutines.delay(long)
    block()
}

fun <T> Any.sourceToTarget(classOfT: Class<T>): T {
    val jsonData = this.toJSON();
    return GsonUtil.getInstance().gson.fromJson(jsonData, classOfT);
}
