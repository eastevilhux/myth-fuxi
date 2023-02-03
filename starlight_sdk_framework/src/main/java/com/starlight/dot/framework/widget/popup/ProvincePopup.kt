package com.starlight.dot.framework.widget.popup

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import com.starlight.dot.framework.R
import com.starlight.dot.framework.adapter.CityAdapter
import com.starlight.dot.framework.commons.SDKActivity
import com.starlight.dot.framework.databinding.PopupProvinceListBinding
import com.starlight.dot.framework.entity.SLCity
import com.starlight.dot.framework.utils.setDrawableTint
import com.starlight.dot.framework.widget.BasePopupWindow
import com.starlight.dot.framework.widget.PopupBaseBuilder

class ProvincePopup : BasePopupWindow<PopupProvinceListBinding,ProvincePopup.Builder> {
    private var lineColor : Int = 0;
    private var context : Context;
    private var cityAdapter : CityAdapter? = null;
    private var provinceList : MutableList<SLCity>? = null;
    private var onProvinceClickListener : ((province : SLCity)->Unit)? = null;

    private constructor(builder: Builder) : super(builder){
        this.context = builder.context;
        this.lineColor = builder.lineColor;
        this.onProvinceClickListener = builder.onProvinceClickListener;
        initView();
    }

    override fun getResourceId(): Int {
        return R.layout.popup_province_list;
    }

    override fun initView() {
        super.initView()
        cityAdapter = CityAdapter(context,provinceList);
        dataBinding.cityAdapter = cityAdapter;

        val drawable = context.setDrawableTint(R.drawable.sl_line_vertical_grey_width_1,lineColor);
        val lineDivider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        if (drawable != null) {
            lineDivider.setDrawable(drawable)
        }
        dataBinding.rvCity.addItemDecoration(lineDivider)

        cityAdapter?.onCityClickListener { onProvinceClickListener?.invoke(it) }
    }

    fun notifyProvinceList(provinceList : MutableList<SLCity>){
        this.provinceList = provinceList;
        cityAdapter?.dataList = provinceList;
        cityAdapter?.notifyDataSetChanged();
    }

    class Builder(activity : SDKActivity<*,*>) : PopupBaseBuilder<ProvincePopup>(activity) {
        internal var lineColor : Int = activity.getColor(R.color.slColorLine);
        internal var onProvinceClickListener : ((province : SLCity)->Unit)? = null;

        fun onProvinceClickListener(onProvinceClickListener : ((province : SLCity)->Unit)): Builder {
            this.onProvinceClickListener = onProvinceClickListener;
            return this;
        }

        fun lineColor(lineColor : Int): Builder {
            this.lineColor = lineColor;
            return this;
        }

        override fun builder(): ProvincePopup {
            return ProvincePopup(this);
        }

    }
}
