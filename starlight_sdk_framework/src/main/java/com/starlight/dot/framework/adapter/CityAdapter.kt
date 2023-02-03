package com.starlight.dot.framework.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.starlight.dot.framework.BR
import com.starlight.dot.framework.R
import com.starlight.dot.framework.commons.EastAdapter
import com.starlight.dot.framework.entity.SLCity
import com.starlight.dot.framework.presenter.impl.CityPresenterImpl

class CityAdapter(context: Context?, dataList: MutableList<SLCity>?) :
    EastAdapter<SLCity>(context, dataList) {

    private var onCityClickListener : ((city : SLCity)->Unit)? = null;

    override fun getViewItemType(position: Int): Int {
        return R.layout.recycler_item_city;
    }

    override fun bindItem(map: MutableMap<Int, Int>?) {
        map?.put(R.layout.recycler_item_city,BR.city);
    }

    override fun setBean(dataBinding: ViewDataBinding?, varid: Int, position: Int) {
        super.setBean(dataBinding, varid, position)
        dataBinding?.setVariable(BR.presenter,presenter);
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
