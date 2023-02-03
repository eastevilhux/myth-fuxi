package com.starlight.dot.framework.commons

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer

abstract class MediatorFragment<D : ViewDataBinding,V : MediatorViewModel<*>> : SDKFragment<D, V>() {

    override fun addObserve() {
        super.addObserve()
        viewModel.mediatorData.observe(this, Observer {

        })
    }
}
