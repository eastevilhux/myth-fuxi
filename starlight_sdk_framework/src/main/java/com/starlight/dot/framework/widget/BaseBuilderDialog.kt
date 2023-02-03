package com.starlight.dot.framework.widget

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.widget.ListView
import androidx.databinding.ObservableField
import androidx.databinding.ViewDataBinding
import com.starlight.dot.framework.utils.defaultWidth

abstract class BaseBuilderDialog<V : ViewDataBinding,B : DialogBuilder<*>> : BaseDialog<V> {
    var title = ObservableField<String>();
    var message = ObservableField<String>();
    var cancelText = ObservableField<String>();
    var submitText = ObservableField<String>();
    var haveTitle = ObservableField<Boolean>();
    var haveMessage = ObservableField<Boolean>();
    var haveSubmit = ObservableField<Boolean>();
    var haveCancel = ObservableField<Boolean>();
    var cancelableFlag : Boolean = false;
    internal var width : Int = 0;
    internal var height : Int = 0

    companion object{
        private const val TAG = "BaseBuilderDialog==>";
    }

    constructor(builder: B,themeResId: Int) : super(builder.context,themeResId) {
        title.set(builder.title);
        message.set(builder.message);
        cancelText.set(builder.cancelText);
        submitText.set(builder.submitText);
        haveTitle.set(builder.haveTitle);
        haveMessage.set(builder.haveMessage);
        haveSubmit.set(builder.haveSubmit);
        haveCancel.set(builder.haveCancel);
        cancelableFlag = builder.isCancelable;
        onCancel = builder.onCancel;
        onMessage = builder.onMessage;
        onSubmit = builder.onSubmit;
        onDismiss = builder.onDismiss;
        width = builder.width;
        height = builder.height;
        initDialog(builder);
    }

    override fun initView() {
        super.initView()
        if(width == 0){
            width = getWidth();
        }
        if(height == 0){
            height = getHeight();
        }
        updateSize(width,height);
    }

    override fun init() {
        Log.d(TAG,"INIT");
    }

    abstract fun initDialog(buider : B);

    override fun cancelable(): Boolean {
        return cancelableFlag;
    }

    fun updateDialogSize(width : Int,height : Int){
        if(width > 0 && height > 0){
            updateSize(width,height);
        }
    }

    fun setSubmitText(submitText: String?){
        this.submitText.set(submitText);
    }

    fun setSubmitText(submitTextRes : Int){
        val s = context.getString(submitTextRes);
        this.submitText.set(s);
    }

    fun setSubmitText(submitTextResId: Int,vararg s : String){
        val s = context.getString(submitTextResId,s);
        this.submitText.set(s);
    }

    fun setTitleText(title : String){
        this.title.set(title);
    }

    fun setTitleText(resId : Int){
        val s = getString(resId);
        setTitleText(s);
    }

    fun setTitleText(resId : Int,vararg args : String){
        val s = getString(resId,*args);
        setTitleText(s);
    }

    fun setCancelText(cancelText : String){
        this.cancelText.set(cancelText);
    }

    fun setCancelText(resId : Int){
        val s = getString(resId);
        setCancelText(s);
    }

    fun setCancelText(resId : Int,vararg args : String){
        val s = getString(resId,*args);
        setCancelText(s);
    }

    fun setMessageText(message : String){
        this.message.set(message);
    }

    fun setMessageText(resId : Int){
        val s = getString(resId);
        setMessageText(s);
    }

    fun setMessageText(resId : Int,vararg args : String){
        val s = getString(resId,*args);
        setMessageText(s);
    }

    override fun getWidth(): Int {
        return context.defaultWidth();
    }
}


abstract class DialogBuilder<D : Dialog>{
    internal var context : Context;
    internal var title : String? = null;
    internal var message : String? = null;
    internal var cancelText : String? = null;
    internal var submitText : String? = null;
    internal var haveTitle : Boolean = false;
    internal var haveMessage : Boolean = false;
    internal var haveSubmit : Boolean = false;
    internal var haveCancel : Boolean = false;
    internal var isCancelable : Boolean = false;
    internal var onCancel : (()->Unit)? = null;
    internal var onMessage : (()->Unit)? = null;
    internal var onSubmit : (()->Unit)? = null;
    internal var onDismiss : (()->Unit)? = null;

    internal var width : Int = 0;
    internal var height : Int = 0;

    constructor(context: Context){
        this.context = context;
    }

    fun title(title : String): DialogBuilder<D> {
        this.title = title;
        this.haveTitle = true;
        return this;
    }

    fun title(titleResId : Int): DialogBuilder<D> {
        return title(context.getString(titleResId));
    }

    fun title(titleResId : Int,vararg s : String): DialogBuilder<D> {
        return title(context.getString(titleResId,s));
    }

    fun message(message : String): DialogBuilder<D> {
        this.message = message;
        this.haveMessage = true;
        return this;
    }

    fun message(messageResId : Int): DialogBuilder<D> {
        return message(context.getString(messageResId));
    }

    fun message(messageResId : Int,vararg s : String): DialogBuilder<D> {
        return message(context.getString(messageResId,s));
    }

    fun cancelText(cancelText : String): DialogBuilder<D> {
        this.cancelText = cancelText;
        this.haveCancel = true;
        return this;
    }

    fun cancelText(cancelTextResId : Int): DialogBuilder<D> {
        return cancelText(context.getString(cancelTextResId));
    }

    fun cancelText(cancelTextResId : Int,vararg s : String): DialogBuilder<D> {
        return cancelText(context.getString(cancelTextResId,s));
    }

    fun submitText(submitText : String): DialogBuilder<D> {
        this.submitText = submitText;
        this.haveSubmit = true;
        return this;
    }

    fun submitText(submitTextResId : Int): DialogBuilder<D> {
        return submitText(context.getString(submitTextResId));
    }

    fun submitText(submitTextResId : Int,vararg s : String): DialogBuilder<D> {
        return submitText(context.getString(submitTextResId,s));
    }

    fun isCancelable(isCancelable : Boolean): DialogBuilder<D> {
        this.isCancelable = isCancelable;
        return this;
    }

    fun onCancel(onCancel : (()->Unit)): DialogBuilder<D> {
        this.onCancel = onCancel;
        return this;
    }

    fun onMessage(onMessage : (()->Unit)): DialogBuilder<D> {
        this.onMessage = onMessage;
        return this;
    }

    fun onSubmit(onSubmit : (()->Unit)): DialogBuilder<D> {
        this.onSubmit = onSubmit;
        return this;
    }

    fun onDismiss(onDismiss : (()->Unit)): DialogBuilder<D> {
        this.onDismiss = onDismiss;
        return this;
    }

    fun width(width : Int): DialogBuilder<D> {
        this.width = width;
        return this;
    }

    fun height(height : Int): DialogBuilder<D> {
        this.height = height;
        return this;
    }

    abstract fun builder() : D;

}
