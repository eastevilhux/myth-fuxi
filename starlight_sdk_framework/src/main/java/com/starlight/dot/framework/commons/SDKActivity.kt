package com.starlight.dot.framework.commons

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.starlight.dot.framework.entity.Loading
import com.east.evil.huxlyn.entity.Target
import com.starlight.dot.framework.entity.VMData
import com.starlight.dot.framework.local.EastRouter
import com.starlight.dot.framework.utils.StatusBarUtil
import com.starlight.dot.framework.widget.LoadingDialog

abstract class SDKActivity<D : ViewDataBinding, V : SDKViewModel<*>> : AppCompatActivity(){

    lateinit var dataBinding: D;
    lateinit var viewModel : V;

    private var loading : LoadingDialog? = null;

    companion object{
        private const val TAG = "SL_SDKActivity==>"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate==>")
        dataBinding = DataBindingUtil.setContentView(this, getLayoutRes())
        var vp = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
        );
        viewModel = vp.get(getVMClass()!!);
        viewModel.setLifecycleOwner(this);
        lifecycle.addObserver(viewModel!!);
        dataBinding.lifecycleOwner = this;
        Log.d(TAG,"sdk activity create viewModel success");
        if (isImmersion()) {
            //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
            StatusBarUtil.setRootViewFitsSystemWindows(this, false)
            //设置状态栏透明
            StatusBarUtil.setTranslucentStatus(this)
            //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
            //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
            if (!StatusBarUtil.setStatusBarDarkTheme(this, false)) {
                //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
                //这样半透明+白=灰, 状态栏的文字能看得清
                StatusBarUtil.setStatusBarColor(this, 0x55000000)
            }
        }

        viewModel.initModel();
        Log.d(TAG,"sdk activity invoe viewModel initModel method success");
        initView();
    }

    abstract fun getLayoutRes() : Int;

    abstract fun getVMClass() : Class<V>;

    open fun initView(){
        addObserve();
    }

    open fun isImmersion() : Boolean{
        return true;
    }

    open fun addObserve(){
        viewModel.vmData.observe(this, Observer {
            onVmdataChanged(it);
        })

        viewModel.loading.observe(this, Observer {
            if(it.loadingFlag){
                showLoading(it);
            }else{
                dismissLoading(it);
            }
        })

        viewModel.target.observe(this, Observer {
            openTarget(it);
        })
    }

    open fun openTarget(target: Target){
        EastRouter.with(this)
            .target(target.cls!!)
            .isFinish(target.isFinish)
            .bundle(target.bundle)
            .start()
    }

    private fun onVmdataChanged(data: VMData){
        when(data.code){
            VMData.Code.CODE_DEFAULT->vmdataDefault(data);
            VMData.Code.CODE_SHOW_MSG->showVmDataToast(data);
            VMData.Code.CODE_SUCCESS->onVmdataSuccess(data);
            VMData.Code.CODE_ERROR->onVmdataError(data);
        }
    }

    open fun vmdataDefault(data:VMData){

    }

    open fun showVmDataToast(data: VMData){

    }

    open fun onVmdataSuccess(data: VMData){

    }

    open fun onVmdataError(data : VMData){

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    open fun back(){
        finish();
    }

    /**
     * 在加载框显示的时候，是否允许取消
     * create by Administrator at 2021/4/22 18:29
     * @author Administrator
     * @param
     * @return
     */
    open fun allowedCancelWithNotEnding(): Boolean {
        return false;
    }


    open fun showLoading(loading: Loading){
        when(loading.type){
            Loading.LoadingType.TYPE_DIALOG->{
                this.loading?:let {
                    this.loading = LoadingDialog(this);
                    if(allowedCancelWithNotEnding()){
                        this.loading!!.setCancelable(true);
                        this.loading!!.setCanceledOnTouchOutside(true);
                    }
                }
                if(this.loading?.isShowing != true){
                    this.loading?.show();
                }
            }
            Loading.LoadingType.TYPE_TOAST->{
                loadingToast(loading)
            }
            Loading.LoadingType.TYPE_VIEW->{
                loadingView(loading)
            }
        }
    }

    open fun dismissLoading(loading: Loading){
        when(loading.type){
            Loading.LoadingType.TYPE_DIALOG->{
                this.loading?.let {
                    it.dismiss();
                }
            }
            Loading.LoadingType.TYPE_TOAST->{
                disLoadingToast(loading)
            }
            Loading.LoadingType.TYPE_VIEW->{
                disLadingView(loading);
            }
        }
    }

    /**
     * 使用View方式进行Loading加载
     * create by hux at 2020-11-28 19:30
     * @author hux
     * @param loading
     *      Loading
     * @return
     *      void
     */
    open fun loadingView(loading: Loading){

    }

    /**
     * 加载完毕，并切加载的方式为View，进行加载完毕后的操作
     * create by hux at 2020-11-28 19:31
     * @author hux
     * @param loading
     *      Loading
     * @return
     *      void
     */
    open fun disLadingView(loading : Loading){

    }

    /**
     * 使用Toast方式进行加载
     * create by hux at 2020-11-28 19:33
     * @author hux
     * @param loading
     *      Loading
     * @return
     *      void
     */
    open fun loadingToast(loading: Loading){

    }

    /**
     * 使用Toast进行加载完毕，进行加载完毕后的操作
     * create by hux at 2020-11-28 19:34
     * @author hux
     * @param loading
     *      Loading
     * @return
     *      void
     */
    open fun disLoadingToast(loading: Loading){

    }

}
