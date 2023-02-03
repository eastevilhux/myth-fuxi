package com.starlight.dot.framework.widget

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ViewDataBinding
import com.starlight.dot.framework.R
import com.starlight.dot.framework.commons.SDKActivity


abstract class BasePopupWindow<D : ViewDataBinding,B : PopupBaseBuilder<*>> : PopupWindow{
    var dataBinding: D;
    private var isTranslucent : Boolean = false;
    private var activity : SDKActivity<*, *>? = null;
    val haveTitle = ObservableField<Boolean>();
    val haveCancel = ObservableField<Boolean>();
    val haveSubmit = ObservableField<Boolean>();
    val titleText = ObservableField<String>();
    val cancelText = ObservableField<String>();
    val submitText = ObservableField<String>();

    private var onSubmit : (()->Unit)? = null;
    private var onCancel : (()->Unit)? = null;
    private var onClose : (()->Unit)? = null;


    constructor(builder : B) : super(builder.context){
        if(builder.context is SDKActivity<*,*>){
            activity = builder.context as SDKActivity<*, *>;
        }

        dataBinding =  DataBindingUtil.inflate(
            LayoutInflater.from(builder.context), getResourceId(), null,false);
        setContentView(dataBinding.root);
        updateSize();
        if(getOutsideTouchable()){
            // 设置SelectPicPopupWindow弹出窗体可点击
            this.setFocusable(true);
            this.setOutsideTouchable(true);
        }
        setAnimationStyle(android.R.style.Animation_Dialog);
        val dw = ColorDrawable(0x00000000)
        setBackgroundDrawable(dw);
        haveTitle.set(builder.haveTitle);
        haveCancel.set(builder.haveCancel);
        haveSubmit.set(builder.haveSubmit);
        titleText.set(builder.titleText);
        cancelText.set(builder.cancelText);
        submitText.set(builder.submitText);

        width = builder.width;
        height = builder.height;
        updateSize();

        isTranslucent = builder.isTranslucent;
        onSubmit = builder.onSubmit;
        onClose = builder.onClose;
        onCancel = builder.onCancel;
        // 刷新状态
        this.update();
    }

    open fun initView(){
        this.update();
    }

    open fun onViewClick(view : View){
        when(view.id){
            R.id.tv_sl_popup_submit->{
                submit();
            }
            R.id.tv_sl_popup_close->{
                close();
            }
            R.id.tv_sl_popup_cancel->{
                cancel();
            }
        }
    }

    open fun submit(){
        onSubmit?.invoke();
    }

    open fun cancel(){
        onCancel?.invoke();
    }

    open fun close(){
        onClose?.invoke();
    }


    fun updateSize(width:Int,height:Int){
        this.width = width;
        this.height = height;
        updateSize();
        this.update();
    }

    open fun updateSize(){

    }

    open fun haveTitle(haveTitle : Boolean){
        this.haveTitle.set(haveTitle);
    }

    open fun haveCancel(haveCancel : Boolean){
        this.haveCancel.set(haveCancel);
    }


    open fun haveSubmit(haveSubmit : Boolean){
        this.haveSubmit.set(haveSubmit);
    }

    open fun setTitleText(titleText : String){
        this.titleText.set(titleText);
    }

    open fun setCancelText(cancelText : String){
        this.cancelText.set(cancelText);
    }

    open fun setSubmitText(submitText : String){
        this.submitText.set(submitText);
    }

    open fun setTitleText(titleTextResId : Int){
        this.titleText.set(dataBinding.root.context.getString(titleTextResId));
    }

    open fun setTitleText(titleTextResId : Int,vararg s:String){
        this.titleText.set(dataBinding.root.context.getString(titleTextResId,s));
    }

    open fun setCancelText(cancelTextResId : Int){
        this.cancelText.set(dataBinding.root.context.getString(cancelTextResId));
    }

    open fun setCancelText(cancelTextResId : Int,vararg s:String){
        this.cancelText.set(dataBinding.root.context.getString(cancelTextResId,s));
    }

    open fun setSubmitText(submitTextResId : Int){
        this.submitText.set(dataBinding.root.context.getString(submitTextResId));
    }

    open fun setSubmitText(submitTextResId : Int,vararg s:String){
        this.submitText.set(dataBinding.root.context.getString(submitTextResId,s));
    }

    open fun showViewDown(view : View){
        if(isTranslucent){
            val window = activity?.window;
            //需要讲背景设置为半透明
            val lp = window?.attributes
            lp?.alpha = 0.5f;
            window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window?.attributes = lp;
        }
        val imm: InputMethodManager? =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.let {
            it.showSoftInput(view,InputMethodManager.SHOW_FORCED);
            it.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        showAsDropDown(view, Gravity.BOTTOM,0,0);
    }

    override fun dismiss() {
        if(isTranslucent){
            //设置背景不透明
            val window = activity?.window;
            //需要讲背景设置为半透明
            val lp = window?.attributes
            lp?.alpha = 1f;
            window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window?.attributes = lp;
        }
        super.dismiss()
    }

    abstract fun getResourceId() : Int;

    open fun getOutsideTouchable() : Boolean{
        return true;
    }

    /**
     * 设置PopupWindow再显示得时候，当前所在的Activity的背景是否为半透明
     * create by Administrator at 2021/5/12 16:03
     * @author Administrator
     * @since 1.0.0
     * @param isTranslucent
     *      是否为半透明
     * @return
     *      void
     */
    fun setTranslucent(isTranslucent : Boolean){
        this.isTranslucent = isTranslucent;
    }

}
