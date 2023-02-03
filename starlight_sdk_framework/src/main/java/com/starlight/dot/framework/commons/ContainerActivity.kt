package com.starlight.dot.framework.commons

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.starlight.dot.framework.R
import com.starlight.dot.framework.databinding.ActivityContainerBinding
import com.starlight.dot.framework.entity.ContainerData
import com.starlight.dot.framework.utils.SLog
import com.starlight.dot.framework.widget.TitleLayout
import org.json.JSONObject

abstract class ContainerActivity<V : SDKViewModel<*>> : SDKActivity<ActivityContainerBinding, V>() {
    companion object{
        private const val TAG = "SL_ContainerActivity==>";
    }
    var showKey : String? = null;
    private lateinit var fragmentMap : HashMap<String, SDKFragment<*, *>>;

    override fun getLayoutRes(): Int {
        return R.layout.activity_container;
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val key = intent?.getStringExtra(getDefaultShowtKey());
        if (key != null) {
            showFragment(key,intent)
        }
    }

    override fun initView() {
        super.initView()
        fragmentMap = createFragment();
        val key = intent.getStringExtra(getDefaultShowtKey());
        //dataBinding.titleLayout.onTitleListener = this;
       /* val key = intent.getStringExtra(getDefaultShowtKey());
        key?.let {key->
            val dataKey = getDefaultDataKey();
            dataKey?.let {dataKey->
                val dataValue = intent.getStringExtra(dataKey);
                dataValue?.let {
                    showFragment(key,dataKey,dataValue);
                }
            }?: showFragment(key)
        }*/
        if (key != null) {
            showFragment(key,intent)
        }
    }

    abstract fun createFragment() : HashMap<String, SDKFragment<*, *>>;

    /**
     * 获取在初始化的时候从Intent中获取显示Fragment的key标识
     * create by Administrator at 2020/12/2 18:21
     * @author Administrator
     * @return
     *      intent的key标识
     */
    abstract fun getDefaultShowtKey() : String;

    open fun getDefaultDataKey(): String? {
        return null;
    }

    open fun showFragment(key:String){
        SLog.e(TAG,"show_release_type=>${key}");
        val f = fragmentMap.get(showKey);
        f?.let {
            f.onContainerHideLast();
        }
        this.showKey = key;
        var fragmentManager = supportFragmentManager;
        var transaction = fragmentManager.beginTransaction();
        fragmentMap?.let {
            for(f in it){
                transaction.hide(f.value);
                f.value.onContainerHide();
            }
        }
        var fragment = fragmentMap.get(key);
        if(fragment != null){
            if(fragment.isAdded){
                SLog.e(TAG,"show_release_type=>${key},the fragment is added");
                fragment.onContainerShow(fragment.arguments);
                transaction.show(fragment);
            }else{
                SLog.e(TAG,"show_release_type=>${key},the fragment is not added,now add it");
                transaction.add(R.id.container_fragment,fragment);
                transaction.show(fragment);
            }
        }
        transaction.commit();
        fragment?.onContainerShow();
        initTitle(key,dataBinding.slTitleLayout);
    }

    open fun showFragment(key:String,dataKey:String,data:String){
        SLog.e(TAG,"show_release_type=>${key}");
        val f = fragmentMap.get(showKey);
        f?.let {
            f.onContainerHideLast();
        }
        this.showKey = key;
        var fragmentManager = supportFragmentManager;
        var transaction = fragmentManager.beginTransaction();
        fragmentMap?.let {
            for(f in it){
                transaction.hide(f.value);
                f.value.onContainerHide();
            }
        }
        var fragment = fragmentMap.get(key);
        if(fragment != null){
            SLog.d(TAG,"DATA=>${data}")
            val bundle = Bundle();
            bundle.putString(dataKey,data)
            fragment.arguments = bundle;
            if(fragment.isAdded){
                SLog.e(TAG,"show_release_type=>${key},the fragment is added");
                fragment.onContainerShow(fragment.arguments);
                transaction.show(fragment);
            }else{
                SLog.e(TAG,"show_release_type=>${key},the fragment is not added,now add it");
                transaction.add(R.id.container_fragment,fragment);
                transaction.show(fragment);
            }
        }
        transaction.commit();
        fragment?.onContainerShow();
        initTitle(key,dataBinding.slTitleLayout);
    }

    fun showFragment(key : String,data : ContainerData){
        val intent = intent;
        val keyList = data.keys();
        keyList.forEach {k->
            val v = data.get(k);
            if(v != null){
                if(v is Int){
                    intent.putExtra(k,v);
                }else if(v is Long){
                    intent.putExtra(k,v)
                }else if(v is Double){
                    intent.putExtra(k,v);
                }else if(v is Float){
                    intent.putExtra(k,v);
                }else if(v is String){
                    intent.putExtra(k,v);
                }
            }
        }
        showFragment(key,intent);
    }

    fun showFragment(key : String, dataList : MutableList<ContainerData>){
        val intent = intent;
        dataList.forEach {
            val keyList = it.keys();
            keyList.forEach {k->
                val v = it.get(k);
                if(v != null){
                    if(v is Int){
                        intent.putExtra(k,v);
                    }else if(v is Long){
                        intent.putExtra(k,v)
                    }else if(v is Double){
                        intent.putExtra(k,v);
                    }else if(v is Float){
                        intent.putExtra(k,v);
                    }else if(v is String){
                        intent.putExtra(k,v);
                    }
                }
            }
        }
        showFragment(key,intent);
    }

    private fun showFragment(key:String,intent : Intent?){
        SLog.e(TAG,"showFragment with intent =>${key}");
        intent?.let {
            val f = fragmentMap.get(showKey);
            f?.let {
                f.onContainerHideLast();
            }
            this.showKey = key;
            var fragmentManager = supportFragmentManager;
            var transaction = fragmentManager.beginTransaction();
            fragmentMap?.let {
                for(f in it){
                    transaction.hide(f.value);
                    f.value.onContainerHide();
                }
            }
            var fragment = fragmentMap.get(key);
            if(fragment != null){
                val extras: Bundle? = it.extras;
                val bundle = Bundle();
                extras?.let {
                    SLog.d(TAG,"put all data at ContainerActivity");
                    bundle.putAll(it)
                    if(SLog.isDebug()){
                        val keyset = bundle.keySet();
                        val json = JSONObject();
                        keyset.forEach {
                            json.put(it,bundle.get(key));
                        }
                        SLog.d(TAG,"ContainerActivity data is =>${json}");
                    }
                }?:let {
                    SLog.d(TAG,"no data at ContainerActivity");
                }
                fragment.arguments = bundle;
                if(fragment.isAdded){
                    SLog.e(TAG,"show_release_type=>${key},the fragment is added");
                    fragment.onContainerShow(fragment.arguments);
                    transaction.show(fragment);
                }else{
                    SLog.e(TAG,"show_release_type=>${key},the fragment is not added,now add it");
                    transaction.add(R.id.container_fragment,fragment);
                    transaction.show(fragment);
                }
            }
            transaction.commit();
            fragment?.onContainerShow();
            initTitle(key,dataBinding.slTitleLayout);
        }?: showFragment(key)
    }

    fun getFragment(key:String): SDKFragment<*, *>? {
        return fragmentMap.get(key);
    }

    override fun addObserve() {
        super.addObserve()
        Log.d(TAG,"addObserve");
    }

    /**
     * 初始化标题信息
     * create by Eastevil at 2022/7/4 13:36
     * @author Eastevil
     * @since 1.0.0
     * @param key
     *      当前显示的key
     * @param titleLayout
     *      标题布局
     * @return
     *      void
     */
    open fun initTitle(key : String,titleLayout: TitleLayout){

    }

}
