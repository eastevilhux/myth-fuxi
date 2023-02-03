package com.starlight.dot.framework.commons

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.starlight.dot.framework.entity.VMData
import com.starlight.dot.framework.entity.vmdata.MediatorData
import com.starlight.dot.framework.utils.isMainThread
import com.starlight.dot.framework.utils.mainThread

abstract class MediatorViewModel<D : MediatorData>(application: Application) : SDKViewModel<D>(application) {
    val mediatorData = MediatorLiveData<D>();

    override fun initModel() {
        super.initModel()
    }

    fun <V : VMData> addSource(liveData: MutableLiveData<D>){
        mediatorData.addSource(liveData, Observer {
            setMediatorData(it);
        })
    }

    fun postMediatorData(data : D){
        mediatorData.postValue(data);
    }

    fun setMediatorData(data : D){
        if(isMainThread()){
            mediatorData.value = data;
        }else{
            mainThread {
                mediatorData.value = data;
            }
        }
    }

}
