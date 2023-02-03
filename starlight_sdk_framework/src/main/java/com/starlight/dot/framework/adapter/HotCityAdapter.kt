package com.starlight.dot.framework.adapter

import android.content.Context
import com.starlight.dot.framework.BR
import com.starlight.dot.framework.R
import com.starlight.dot.framework.commons.BaseListAdapter
import com.starlight.dot.framework.databinding.GridItemHotcityBinding
import com.starlight.dot.framework.entity.SLCity
import com.starlight.dot.framework.presenter.impl.CityPresenterImpl

class HotCityAdapter(list: MutableList<SLCity>?, context: Context?) :
    BaseListAdapter<GridItemHotcityBinding, SLCity>(list, context, BR.city) {
    private var onCityClickListener : ((city : SLCity)->Unit)? = null;

    override fun getLayoutRes(): Int {
        return R.layout.grid_item_hotcity;
    }

    override fun setBean(t: SLCity?) {
        dataBinding.city = t;
        dataBinding.presenter = presenter;
    }

    fun onCityClickListener(onCityClickListener : ((city : SLCity)->Unit)){
        this.onCityClickListener = onCityClickListener;
    }

    private val presenter = object : CityPresenterImpl() {

        override fun onItemSelectListener(entity: SLCity) {
            super.onItemSelectListener(entity)
            onCityClickListener?.invoke(entity);
        }
    }
}
