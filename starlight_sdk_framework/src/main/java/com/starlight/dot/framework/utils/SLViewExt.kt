package com.starlight.dot.framework.utils

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.starlight.dot.framework.R

/**
 * 动态生成GradientDrawable
 * create by Eastevil at 2022/5/13 17:08
 * @author Eastevil
 * @param bgColor
 *      背景颜色,默认为白色
 * @param stroke
 *      边框宽度，如果为0，则无边框，默认为0
 * @param strokeColor
 *      边框颜色
 * @param radius
 *      圆角半径
 * @return
 *      生成的 [GradientDrawable]
 */
fun Context.createRectangleShape(bgColor : Int = Color.WHITE,stroke : Int = 0,
                                   strokeColor : Int = Color.BLACK,radius : Float): GradientDrawable {
    val drawable = GradientDrawable();
    drawable.setColor(bgColor);
    drawable.shape = GradientDrawable.RECTANGLE;
    if(stroke > 0){
        drawable.setStroke(stroke,strokeColor);
    }
    drawable.setCornerRadius(radius);
    return drawable;
}

fun Context.createOvalShape(bgColor : Int = Color.WHITE,stroke : Int = 0,
                   strokeColor : Int = Color.BLACK): GradientDrawable {
    val drawable = GradientDrawable();
    drawable.setColor(bgColor);
    drawable.shape = GradientDrawable.OVAL;
    if(stroke > 0){
        drawable.setStroke(stroke,strokeColor);
    }
    return drawable;
}

/**
 * 更新资源文件中定义的shape背景颜色和边框颜色
 * create by Eastevil at 2022/5/13 17:07
 * @author Eastevil
 * @param shapeId
 *      资源id
 * @param color
 *      背景颜色
 * @param stroke
 *      边框宽度，如果为0，则不做修改，默认为0
 * @param strokeColor
 *      边框颜色
 * @return
 *      修改后的GradientDrawable对象
 */
fun Context.notifyShapeColor(shapeId : Int,color : Int,stroke : Int = 0,strokeColor : Int = Color.BLACK): GradientDrawable? {
    val drawable = ContextCompat.getDrawable(this, shapeId);
    drawable?.let {
        val sd = it as GradientDrawable;
        sd.setColor(color);
        if(stroke > 0){
            sd.setStroke(stroke,strokeColor);
        }
        return sd;
    }
    return null;
}

fun ImageView.setImageViewTint(resId : Int,color : Int){
    val drawable = ContextCompat.getDrawable(this.context,resId);
    drawable?.let {
        this.setImageDrawable(it);
        this.drawable.setTint(color);
    }
}

fun Context.setDrawableTint(resId: Int,color : Int): Drawable? {
    val drawable = ContextCompat.getDrawable(this,resId);
    drawable?.let {
        it.setTint(color);
    }
    return drawable;
}

fun RecyclerView.addLineDivider(){
    val lineDivider = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
    lineDivider.setDrawable(ContextCompat.getDrawable(this.context, R.drawable.line_default)!!)
    this.addItemDecoration(lineDivider)
}

fun Context.defaultWidth(): Int {
    return getScreenSize(this)[0] - 60.dip2px();
}

fun changeAlpha(color: Int, fraction: Float): Int {
    val red: Int = Color.red(color)
    val green: Int = Color.green(color)
    val blue: Int = Color.blue(color)
    val alpha = (Color.alpha(color) * fraction).toInt();
    return Color.argb(alpha, red, green, blue)
}

fun Context.keyBoardIsShow(): Boolean {
    try {
        val im = this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(im is InputMethodManager){
            val method = im.javaClass.getDeclaredMethod("getInputMethodWindowVisibleHeight");
            method.setAccessible(true);
            val height = method.invoke(im).toString().toInt()
            if (height > 0) {
                return true
            }
        }
    }catch (e : Exception){
        e.printStackTrace();
    }
    return false;
}


fun View.getColor(id : Int): Int {
    return this.context.getColor(id);
}

fun String.parseColor(): Int {
    return Color.parseColor(this);
}

fun View.getString(resId : Int): String {
    return this.context.getString(resId);
}

fun View.getString(resId: Int,vararg args : String): String {
    return this.context.getString(resId,*args);
}

/**
 * 强制隐藏软键盘
 * create by Administrator at 2022/7/28 21:50
 * @author Administrator
 * @param v
 *      [View]
 * @return
 *      void
 */
fun Context.hideSoftInputFromWindow(v : View){
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE);
    if(imm is InputMethodManager){
        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0); //强制隐藏键盘
    }
}
