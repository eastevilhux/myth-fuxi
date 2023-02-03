package com.starlight.dot.framework.widget

import android.content.Context
import android.widget.PopupWindow
import com.starlight.dot.framework.utils.isEmpty

abstract class PopupBaseBuilder<P : PopupWindow> {
    internal var context : Context;
    internal var haveCancel : Boolean = false;
    internal var haveTitle : Boolean = true;
    internal var haveSubmit : Boolean = false;
    internal var cancelText : String? = null;
    internal var titleText : String? = null;
    internal var submitText : String? = null;
    internal var width:Int = 0;
    internal var height : Int = 0;
    internal var isTranslucent : Boolean = false;
    internal var onSubmit : (()->Unit)? = null;
    internal var onCancel : (()->Unit)? = null;
    internal var onClose : (()->Unit)? = null;

    constructor(context: Context){
        this.context = context;
    }

    fun title(title : String): PopupBaseBuilder<P> {
        this.titleText = title;
        if(!title.isEmpty()){
            this.haveTitle = true;
        }
        return this;
    }

    fun title(titleResId : Int): PopupBaseBuilder<P> {
        return title(context.getString(titleResId));
    }

    fun title(titleResId: Int,vararg s : String): PopupBaseBuilder<P> {
        return title(context.getString(titleResId,s))
    }

    fun width(width:Int): PopupBaseBuilder<P> {
        this.width = width;
        return this;
    }

    fun height(height:Int): PopupBaseBuilder<P> {
        this.height = height;
        return this;
    }

    fun size(width: Int,height: Int): PopupBaseBuilder<P> {
        this.width = width;
        this.height = height;
        return this;
    }

    fun cancelText(cancelText : String): PopupBaseBuilder<P> {
        this.cancelText = cancelText;
        if(cancelText.isNotEmpty()){
            this.haveCancel = true;
        }
        return this;
    }

    fun cancelText(cancelResId : Int): PopupBaseBuilder<P> {
        return cancelText(context.getString(cancelResId));
    }

    fun cancelText(cancelResId: Int,vararg s : String): PopupBaseBuilder<P> {
        return cancelText(context.getString(cancelResId,s))
    }

    fun submitText(submitText : String): PopupBaseBuilder<P> {
        this.submitText = submitText;
        if(submitText.isNotEmpty()){
            this.haveSubmit = true;
        }
        return this;
    }

    fun submitText(submitResId : Int): PopupBaseBuilder<P> {
        return submitText(context.getString(submitResId));
    }

    fun submitText(submitResId: Int,vararg s : String): PopupBaseBuilder<P> {
        return submitText(context.getString(submitResId,s))
    }

    fun haveTitle(haveTitle : Boolean): PopupBaseBuilder<P> {
        this.haveTitle = haveTitle;
        return this;
    }

    fun haveCancel(haveCancel : Boolean): PopupBaseBuilder<P> {
        this.haveCancel = haveCancel;
        return this;
    }

    fun haveSubmit(haveSubmit : Boolean): PopupBaseBuilder<P> {
        this.haveSubmit = haveSubmit;
        return this;
    }

    fun isTranslucent(isTranslucent : Boolean): PopupBaseBuilder<P> {
        this.isTranslucent = isTranslucent;
        return this;
    }

    fun onSubmit(onSubmit : (()->Unit)): PopupBaseBuilder<P> {
        this.onSubmit = onSubmit;
        return this;
    }

    fun onCancel(onCancel : (()->Unit)): PopupBaseBuilder<P> {
        this.onCancel = onCancel;
        return this;
    }

    fun onClose(onClose : (()->Unit)): PopupBaseBuilder<P> {
        this.onClose = onClose;
        return this;
    }

    fun getContext(): Context {
        return context;
    }

    abstract fun builder() : P;
}
