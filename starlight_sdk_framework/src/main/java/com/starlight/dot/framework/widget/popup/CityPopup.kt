package com.starlight.dot.framework.widget.popup

import android.content.Context
import androidx.databinding.ObservableField
import com.starlight.dot.framework.R
import com.starlight.dot.framework.adapter.CityAdapter
import com.starlight.dot.framework.adapter.HotCityAdapter
import com.starlight.dot.framework.commons.SDKActivity
import com.starlight.dot.framework.databinding.PopupCityListBinding
import com.starlight.dot.framework.entity.SLCity
import com.starlight.dot.framework.utils.createRectangleShape
import com.starlight.dot.framework.utils.notifyShapeColor
import com.starlight.dot.framework.utils.setImageViewTint
import com.starlight.dot.framework.widget.BasePopupWindow
import com.starlight.dot.framework.widget.PopupBaseBuilder
import kotlin.coroutines.coroutineContext
import android.graphics.Color;
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.starlight.dot.framework.utils.setDrawableTint
import com.starlight.dot.framework.widget.ViewToast

class CityPopup : BasePopupWindow<PopupCityListBinding,CityPopup.Builder>{
    private var selectProvince : ObservableField<Boolean>;
    private var selectCity : ObservableField<Boolean>;
    private var selectArea : ObservableField<Boolean>;
    private var normalColor : Int = 0;
    private var selectColor : Int = 0;
    private var lineColor : Int = 0;
    private var context : Context;
    private var province : ObservableField<SLCity>;
    private var city : ObservableField<SLCity>;
    private var area : ObservableField<SLCity>;
    private var hotAdapter : HotCityAdapter? = null;
    private var cityAdapter : CityAdapter? = null;
    private var tag : Int = 0;
    private var onProvinceClickListener : ((province : SLCity)->Unit)? = null;
    private var onCityClickListener : ((city : SLCity)->Unit)? = null;
    private var onAreaClickListener : ((city : SLCity)->Unit)? = null;
    private var showEnter : ObservableField<Boolean>;
    private var showProvinceLisener : (()->Unit)? = null;
    private var onHotCityClickListener : ((city : SLCity)->Unit)? = null;
    private var mustProvince : Boolean = true;
    private var mustCity : Boolean = true;
    private var mustArea : Boolean = true;
    private var onCitySelectListener : ((province : SLCity?,city : SLCity?,area : SLCity?)->Unit)? = null;

    private constructor(builder : Builder) : super(builder) {
        this.normalColor = builder.normalColor;
        this.selectColor = builder.selectColor;
        this.context = builder.context;
        this.lineColor = builder.lineColor;
        this.onProvinceClickListener = builder.onProvinceClickListener;
        this.onCityClickListener = builder.onCityClickListener;
        this.onAreaClickListener = builder.onAreaClickListener;
        this.showProvinceLisener = builder.showProvinceLisener;
        this.onHotCityClickListener = builder.onHotCityClickListener;
        this.onCitySelectListener = builder.onCitySelectListener;
        this.mustProvince = builder.mustProvince;
        this.mustCity = builder.mustCity;
        this.mustArea = builder.mustArea;
        showEnter = ObservableField(builder.selectPause);
        selectProvince = ObservableField(true);
        selectCity = ObservableField(false);
        selectArea = ObservableField(false);
        province = ObservableField();
        city = ObservableField();
        area = ObservableField();
        initView();
    }

    override fun getResourceId(): Int {
        return R.layout.popup_city_list;
    }

    override fun initView() {
        super.initView()
        val norPointShape = context
            .notifyShapeColor(R.drawable.sl_shape_circle_grey_border_white,normalColor);
        dataBinding.ivPopupCityProvincePoint.setImageDrawable(norPointShape);
        dataBinding.ivPopupCityCityPoint.setImageDrawable(norPointShape);
        dataBinding.ivPopupCityAreaPoint.setImageDrawable(norPointShape);

        dataBinding.ivProvinceArrow.setImageViewTint(R.drawable.sl_vector_arrow_default,normalColor);
        dataBinding.ivCityArrow.setImageViewTint(R.drawable.sl_vector_arrow_default,normalColor);
        dataBinding.ivAreaArrow.setImageViewTint(R.drawable.sl_vector_arrow_default,normalColor);

        dataBinding.ivCityLine.setImageViewTint(R.drawable.sl_line_vertical_grey_width_1,normalColor);
        dataBinding.ivAreaLine.setImageViewTint(R.drawable.sl_line_vertical_grey_width_1,normalColor);

        dataBinding.province = province;
        dataBinding.city = city;
        dataBinding.area = area;
        dataBinding.selectProvince = selectProvince;
        dataBinding.selectCity = selectCity;
        dataBinding.selectArea = selectArea;
        dataBinding.showEnter = showEnter;

        hotAdapter = HotCityAdapter(null,context);
        dataBinding.hotAdapter = hotAdapter;

        cityAdapter = CityAdapter(context,null);
        dataBinding.cityAdapter = cityAdapter;

        dataBinding.popup = this;

        val drawable = context.setDrawableTint(R.drawable.sl_line_vertical_grey_width_1,lineColor);
        val lineDivider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        if (drawable != null) {
            lineDivider.setDrawable(drawable)
        }
        dataBinding.rvCity.addItemDecoration(lineDivider)

        cityAdapter?.onCityClickListener {
            onCityClick(it);
        }

        hotAdapter?.onCityClickListener {
            selectHot(it);
        }
    }

    override fun onViewClick(view: View) {
        super.onViewClick(view)
        when(view.id){
            R.id.tv_sl_popup_province->{
                //点击省份，重新选择
                if(province.get() == null || selectProvince.get() == false){
                    return;
                }
                tag = 1;
                showProvinceLisener?.invoke();
                if(selectCity.get() == true){
                    val cityNorPointShape = context
                        .notifyShapeColor(R.drawable.sl_shape_circle_grey_border_white,normalColor,stroke = 1,strokeColor = normalColor);
                    dataBinding.ivPopupCityCityPoint.setImageDrawable(cityNorPointShape);
                    dataBinding.ivCityLine.setImageViewTint(R.drawable.sl_line_vertical_grey_width_1,normalColor);
                }
                if(selectArea.get() == true){
                    val areaNorPointShape = context
                        .notifyShapeColor(R.drawable.sl_shape_circle_grey_border_white,normalColor,stroke = 1,strokeColor = normalColor);
                    dataBinding.ivPopupCityAreaPoint.setImageDrawable(areaNorPointShape);
                    dataBinding.ivAreaLine.setImageViewTint(R.drawable.sl_line_vertical_grey_width_1,normalColor);
                }
                selectCity.set(false);
                selectArea.set(false);
            }
            R.id.tv_sl_popup_city->{
                //重新点击了城市，根据当前选择的省份，查询该省份下所有的城市列表
                val province = this.province.get();
                if(province != null){
                    onProvinceClickListener?.invoke(province);
                }
                if(selectArea.get() == true){
                    val areaNorPointShape = context
                        .notifyShapeColor(R.drawable.sl_shape_circle_grey_border_white,normalColor,stroke = 1,strokeColor = normalColor);
                    dataBinding.ivPopupCityAreaPoint.setImageDrawable(areaNorPointShape);
                    dataBinding.ivAreaLine.setImageViewTint(R.drawable.sl_line_vertical_grey_width_1,normalColor);
                }
                selectArea.set(false);
            }
            R.id.tv_sl_popup_area->{

            }
            R.id.tv_select_enter->{
                val p = if(selectProvince.get() == true ) province.get() else null;
                if(mustProvince && p == null){
                    ViewToast.show(context,R.string.sl_please_select_province,Toast.LENGTH_SHORT);
                    return;
                }
                val c = if(selectCity.get() == true) this.city.get() else null;
                if(mustCity && c == null){
                    ViewToast.show(context,R.string.sl_please_select_city,Toast.LENGTH_SHORT);
                    return;
                }
                val a = if(selectArea.get() == true) area.get() else null;
                if(mustArea && a == null){
                    ViewToast.show(context,R.string.sl_please_select_area,Toast.LENGTH_SHORT);
                    return;
                }
                onCitySelectListener?.invoke(p,c,a);
                dismiss();
            }
        }
    }

    fun notifyHotList(hotList : MutableList<SLCity>){
        hotAdapter?.list = hotList;
        hotAdapter?.notifyDataSetChanged();
    }

    /**
     * 更新城市列表
     * create by Eastevil at 2022/5/17 11:30
     * @author Eastevil
     * @param cityList
     *      城市列表
     * @param tag
     *      1:更新省份,2:更新城市，3：更新区域
     * @return
     *      void
     */
    fun notifyCityList(cityList : MutableList<SLCity>,tag : Int){
        this.tag = tag;
        cityAdapter?.dataList = cityList;
        cityAdapter?.notifyDataSetChanged();
    }

    fun selectProvince(province: SLCity){
        this.province.set(province);
        selectProvince.set(true);
        val selPointShape = context
            .notifyShapeColor(R.drawable.sl_shape_circle_grey_border_white,selectColor,stroke = 1,strokeColor = selectColor);
        dataBinding.ivPopupCityProvincePoint.setImageDrawable(selPointShape);
    }

    fun selectCity(city : SLCity){
        this.city.set(city);
        selectCity.set(true);
        val selPointShape = context
            .notifyShapeColor(R.drawable.sl_shape_circle_grey_border_white,selectColor,stroke = 1,strokeColor = selectColor);
        dataBinding.ivPopupCityCityPoint.setImageDrawable(selPointShape);
        dataBinding.ivCityLine.setImageViewTint(R.drawable.sl_line_vertical_grey_width_1,selectColor);

        val norPointShape = context
            .notifyShapeColor(R.drawable.sl_shape_circle_grey_border_white,normalColor,stroke = 1,strokeColor = normalColor);
        dataBinding.ivPopupCityAreaPoint.setImageDrawable(norPointShape);
        dataBinding.ivAreaLine.setImageViewTint(R.drawable.sl_line_vertical_grey_width_1,normalColor)
    }

    private fun onCityClick(city : SLCity){
        when(tag){
            1->{
                //选择省份
                province.set(city);
                selectProvince.set(true);
                val selPointShape = context
                    .notifyShapeColor(R.drawable.sl_shape_circle_grey_border_white,selectColor,stroke = 1,strokeColor = selectColor);
                dataBinding.ivPopupCityProvincePoint.setImageDrawable(selPointShape);
                onProvinceClickListener?.invoke(city);
            }
            2->{
                //选择城市
                this.city.set(city);
                selectCity.set(true);
                val selPointShape = context
                    .notifyShapeColor(R.drawable.sl_shape_circle_grey_border_white,selectColor,stroke = 1,strokeColor = selectColor);
                dataBinding.ivPopupCityCityPoint.setImageDrawable(selPointShape);

                dataBinding.ivCityLine.setImageViewTint(R.drawable.sl_line_vertical_grey_width_1,selectColor);
                onCityClickListener?.invoke(city);
            }
            3->{
                this.area.set(city);
                selectArea.set(true);
                val selPointShape = context
                    .notifyShapeColor(R.drawable.sl_shape_circle_grey_border_white,selectColor,stroke = 1,strokeColor = selectColor);
                dataBinding.ivPopupCityAreaPoint.setImageDrawable(selPointShape);
                dataBinding.ivAreaLine.setImageViewTint(R.drawable.sl_line_vertical_grey_width_1,selectColor);
                if(showEnter.get() == true){
                    //do some things
                }else{
                    val p = province.get();
                    if(mustProvince && p == null){
                        ViewToast.show(context,R.string.sl_please_select_province,Toast.LENGTH_SHORT);
                        return;
                    }
                    val c = this.city.get();
                    if(mustCity && c == null){
                        ViewToast.show(context,R.string.sl_please_select_city,Toast.LENGTH_SHORT);
                        return;
                    }
                    val a = area.get();
                    if(mustArea && a == null){
                        ViewToast.show(context,R.string.sl_please_select_area,Toast.LENGTH_SHORT);
                        return;
                    }
                    onCitySelectListener?.invoke(p,c,a);
                    dismiss();
                }
                onAreaClickListener?.invoke(city);
            }
        }
    }

    private fun selectHot(city : SLCity){
        onHotCityClickListener?.invoke(city);
    }

    class Builder(activity: SDKActivity<*,*>) : PopupBaseBuilder<CityPopup>(activity) {
        internal val normalColor : Int = Color.parseColor("#C8C8C8");
        internal val selectColor : Int = Color.parseColor("#FA960A");
        internal var lineColor : Int = activity.getColor(R.color.slColorLine);
        internal var onProvinceClickListener : ((province : SLCity)->Unit)? = null;
        internal var onCityClickListener : ((city : SLCity)->Unit)? = null;
        internal var onAreaClickListener : ((city : SLCity)->Unit)? = null;
        internal var selectPause : Boolean = false;
        internal var showProvinceLisener : (()->Unit)? = null;
        internal var onHotCityClickListener : ((city : SLCity)->Unit)? = null;
        internal var onCitySelectListener : ((province : SLCity?,city : SLCity?,area : SLCity?)->Unit)? = null;
        internal var mustProvince : Boolean = true;
        internal var mustCity : Boolean = true;
        internal var mustArea : Boolean = true;

        fun onProvinceClickListener(onProvinceClickListener : ((province : SLCity)->Unit)): Builder {
            this.onProvinceClickListener = onProvinceClickListener;
            return this;
        }

        fun onCityClickListener(onCityClickListener : ((city : SLCity)->Unit)): Builder {
            this.onCityClickListener = onCityClickListener;
            return this;
        }

        fun onAreaClickListener(onAreaClickListener : ((city : SLCity)->Unit)): Builder {
            this.onAreaClickListener = onAreaClickListener;
            return this;
        }

        fun selectPause(selectPause : Boolean): Builder {
            this.selectPause = selectPause;
            return this;
        }

        fun showProvinceLisener(showProvinceLisener : (()->Unit)): Builder {
            this.showProvinceLisener = showProvinceLisener;
            return this;
        }

        fun onHotCityClickListener(onHotCityClickListener : ((city : SLCity)->Unit)): Builder {
            this.onHotCityClickListener = onHotCityClickListener;
            return this;
        }

        fun onCitySelectListener(onCitySelectListener : ((province : SLCity?,city : SLCity?,area : SLCity?)->Unit)): Builder {
            this.onCitySelectListener = onCitySelectListener;
            return this;
        }

        fun lineColor(lineColor : Int): Builder {
            this.lineColor = lineColor;
            return this;
        }

        fun mustProvince(mustProvince : Boolean): Builder {
            this.mustProvince = mustProvince;
            return this;
        }

        fun mustCity(mustCity : Boolean): Builder {
            this.mustCity = mustCity;
            return this;
        }

        fun mustArea(mustArea : Boolean): Builder {
            this.mustArea = mustArea;
            return this;
        }

        override fun builder(): CityPopup {
            return CityPopup(this);
        }
    }
}
