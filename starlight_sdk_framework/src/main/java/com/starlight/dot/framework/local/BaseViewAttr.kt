package com.starlight.dot.framework.local

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.starlight.dot.framework.R
import com.starlight.dot.framework.utils.SLog
import com.starlight.dot.framework.utils.isEmpty

private const val TAG = "SL_BaseViewAttr==>";

object BaseViewAttr {

    @JvmStatic
    @BindingAdapter("android:text")
    fun setText(view : TextView,text : String?){
        when(view.id){
            R.id.tv_sl_popup_province->{
                text?.let {
                    if(it.isEmpty()){
                        view.setText(R.string.sl_popup_title_selectcity);
                    }else{
                        view.setText(it);
                    }
                }?:let {
                    view.setText(R.string.sl_popup_title_selectcity);
                }
            }
            R.id.tv_city_name->{
                SLog.i(TAG,"show city name=>${text}")
                view.setText(text)
            }
            else-> view.setText(text);
        }
    }

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibile(view: View, flag: Boolean) {
        when (view.id) {
            else -> if (flag) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.GONE
            }
        }
    }
}
