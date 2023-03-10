package com.starlight.dot.framework.widget

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.starlight.dot.framework.R


abstract class BaseDialog<D : ViewDataBinding> : Dialog, DialogInterface.OnDismissListener,
    DialogInterface.OnCancelListener {
    lateinit var dataBinding : D;

    companion object{
        private const val TAG = "BaseDialog";
    }

    var onDismiss : (()->Unit)? = null;
    var onSubmit : (()->Unit)? = null;
    var onCancel : (()->Unit)? = null;
    var onMessage : (()->Unit)? = null;

    constructor(context: Context, themeResId: Int) : super(context,themeResId){
        Log.d(TAG,"constructor=>")
        dataBinding =  DataBindingUtil.inflate(layoutInflater, layoutRes(), null,false);
        setContentView(dataBinding.root);
        Log.d(TAG,"INIT=>")
        init();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate=>")
        super.onCreate(savedInstanceState)
        initView();
    }

    private fun setSize(){
        Log.d(TAG,"setSize=>WIDTH=>${getWidth()},HEIGHT=>${getHeight()}")
        val params: WindowManager.LayoutParams? = getWindow()?.getAttributes()
        params?.width = getWidth();
        params?.height = getHeight();
        getWindow()?.setAttributes(params);
    }

    fun updateSize(width:Int,height:Int){
        Log.d(TAG,"updateSize=>WIDTH=>${width},HEIGHT=>${height}")
        val params: WindowManager.LayoutParams? = getWindow()?.getAttributes()
        params?.width = width;
        params?.height = height;
        getWindow()?.setAttributes(params);
    }

    open protected fun initView(){
        Log.d(TAG,"initView=>")
        setCancelable(cancelable())
        setSize();
        initListener();
    }

    open fun setTitle(title : String){
        super.setTitle(title);
    }

    open fun setMessage(message : String){

    }

    fun isCancelable(cancelable : Boolean){
        setCancelable(cancelable);
    }

    /**
     * ???????????????????????????????????????????????????Dialog
     * create by Administrator at 2021/5/7 15:50
     * @author Administrator
     * @since 1.0.0
     * @return
     *      ???????????????????????????????????????????????????Dialog
     */
    open fun cancelable() : Boolean{
        return false;
    }

    /**
     * ???Dialog???????????????????????????onDismiss????????????????????????dismissDialog???????????????????????????dismissDialog???
     * ????????????????????????true??????dismissDialog???????????????????????????,????????????false
     * create by Administrator at 2021/4/25 14:46
     * @author Administrator
     * @since 1.0.0
     * @return
     *      ??????????????????dismissDialog??????
     */ 
    open fun dismissWithSuperDis() : Boolean{
        return false;
    }

    /**
     * ???Dialog????????????Cancel????????????????????????????????????cancelDialog??????
     * create by Administrator at 2021/4/25 14:50
     * @author Administrator
     * @return
     */
    open fun cancelWithSuperCancel() : Boolean{
        return false;
    }

    open fun initListener(){
        setOnDismissListener(this)
        setOnCancelListener(this)
    }

    fun getString(resId : Int): String {
        return context.getString(resId);
    }

    fun getString(resId : Int,vararg s : String): String {
        return context.getString(resId,*s);
    }

    /**
     * ????????????????????????
     * create by Administrator at 2021/4/25 13:53
     * @author Administrator
     * @param
     * @return
     */
    abstract fun init();


    /**
     * ????????????Dialog?????????????????????id
     * create by Administrator at 2021/4/25 12:01
     * @author Administrator
     * @since 1.0.0
     * @see onCreate
     * @return
     *      ??????Dialog?????????????????????ID
     */
    abstract fun layoutRes() : Int;

    /**
     * ???????????????????????????
     * create by Administrator at 2021/4/25 12:02
     * @author Administrator
     * @since 1.0.0
     * @see initDialog
     * @return
     *      ?????????????????????
     */
    abstract fun getWidth() : Int;

    /**
     * ???????????????????????????
     * create by Administrator at 2021/4/25 12:03
     * @author Administrator
     * @since 1.0.0
     * @see initDialog
     * @return
     *      ?????????????????????
     */
    abstract fun getHeight() : Int;

    open fun onViewClick(view : View){
        when(view.id){
            R.id.tv_sl_dialog_close->{
                onClose();
            }
            R.id.tv_sl_dialog_submit->{
                onSubmit();
            }
            R.id.tv_sl_dialog_cancel->{
                onCancel();
            }
            R.id.tv_sl_dialog_dismiss->{
                dismiss();
            }
        }
    }

    /**
     * ????????????????????????????????????
     * create by Administrator at 2021/7/23 16:32
     * @author Administrator
     * @return
     *      void
     */
    open fun onSubmit(){
        onSubmit?.invoke();
    }

    /**
     * ????????????????????????????????????
     * create by Administrator at 2021/7/23 16:32
     * @author Administrator
     * @return
     *      void
     */
    open fun onCancel(){
        dismissDialog();
        onCancel?.invoke();
    }

    /**
     * ????????????????????????????????????
     * create by Administrator at 2021/7/23 16:33
     * @author Administrator
     * @return
     *      void
     */
    open fun onClose(){
        dismissDialog();
    }

    override fun dismiss() {
        super.dismiss()
        onDismiss?.invoke();
    }

    fun onDismiss(onDismiss : (()->Unit)){
        this.onDismiss = onDismiss;
    }

    fun onSubmit(onSubmit : (()->Unit)){
        this.onSubmit = onSubmit;
    }

    fun onCancel(onCancel : (()->Unit)){
        this.onCancel = onCancel;
    }


    open fun dismissDialog(){
        Log.d(TAG,"dismissDialog==>")
        dismiss();
    }

    fun submit(){
        Log.d(TAG,"submit==>")
        onSubmit?.invoke();
    }

    fun cancelDialog(){
        Log.d(TAG,"cancelDialog==>")
        onCancel?.invoke();
    }

    /**
     * This method will be invoked when the dialog is canceled.
     *
     * @param dialog the dialog that was canceled will be passed into the
     * method
     */
    override fun onCancel(dialog: DialogInterface?) {
        if(cancelWithSuperCancel()){
            cancelDialog();
        }
    }

    /**
     * This method will be invoked when the dialog is dismissed.
     *
     * @param dialog the dialog that was dismissed will be passed into the
     * method
     */
    override fun onDismiss(dialog: DialogInterface?) {
        if(dismissWithSuperDis()){
            dismissDialog();
        }
    }
}
