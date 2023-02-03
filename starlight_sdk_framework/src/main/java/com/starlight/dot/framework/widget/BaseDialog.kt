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
     * 是否可以在点击空白区域时注销当前的Dialog
     * create by Administrator at 2021/5/7 15:50
     * @author Administrator
     * @since 1.0.0
     * @return
     *      是否可以在点击空白区域时注销当前的Dialog
     */
    open fun cancelable() : Boolean{
        return false;
    }

    /**
     * 当Dialog消失时候，调用父类onDismiss方法时，是否触发dismissDialog方法，即如果实现了dismissDialog，
     * 并且该方法返回为true，则dismissDialog方法将会被调用两次,默认返回false
     * create by Administrator at 2021/4/25 14:46
     * @author Administrator
     * @since 1.0.0
     * @return
     *      是否需要触发dismissDialog方法
     */ 
    open fun dismissWithSuperDis() : Boolean{
        return false;
    }

    /**
     * 当Dialog触发父类Cancel方法时，是否需要同时触发cancelDialog方法
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
     * 构造方法中初始化
     * create by Administrator at 2021/4/25 13:53
     * @author Administrator
     * @param
     * @return
     */
    abstract fun init();


    /**
     * 指定当前Dialog使用得布局资源id
     * create by Administrator at 2021/4/25 12:01
     * @author Administrator
     * @since 1.0.0
     * @see onCreate
     * @return
     *      当前Dialog使用得布局资源ID
     */
    abstract fun layoutRes() : Int;

    /**
     * 设置当前弹框的宽度
     * create by Administrator at 2021/4/25 12:02
     * @author Administrator
     * @since 1.0.0
     * @see initDialog
     * @return
     *      当前弹框的宽度
     */
    abstract fun getWidth() : Int;

    /**
     * 设置当前弹框的高度
     * create by Administrator at 2021/4/25 12:03
     * @author Administrator
     * @since 1.0.0
     * @see initDialog
     * @return
     *      当前弹框的高度
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
     * 弹框确定按钮点击事件监听
     * create by Administrator at 2021/7/23 16:32
     * @author Administrator
     * @return
     *      void
     */
    open fun onSubmit(){
        onSubmit?.invoke();
    }

    /**
     * 弹框取消按钮点击事件监听
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
     * 弹框关闭按钮点击事件监听
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
