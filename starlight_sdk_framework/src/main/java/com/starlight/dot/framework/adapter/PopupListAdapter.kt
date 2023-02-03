package com.starlight.dot.framework.adapter

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.starlight.dot.framework.BR
import com.starlight.dot.framework.R
import com.starlight.dot.framework.commons.EastAdapter
import com.starlight.dot.framework.entity.SLItem
import com.starlight.dot.framework.presenter.impl.SLItemPresneterImpl
import com.starlight.dot.framework.utils.dip2px

class PopupListAdapter(context: Context?, dataList: MutableList<SLItem<*>>?) :
    EastAdapter<SLItem<*>>(context, dataList) {

    private var showImage : ((imageView : ImageView,slitem : SLItem<*>)->Unit)? = null;
    private var onSLItemClickListener : ((item : SLItem<*>)->Unit)? = null;

    override fun getViewItemType(position: Int): Int {
        return R.layout.recycler_item_popuplist;
    }

    override fun bindItem(map: MutableMap<Int, Int>?) {
        map?.put(R.layout.recycler_item_popuplist,BR.slItem);
    }

    fun addShowImage(showImage : ((imageView : ImageView,slitem : SLItem<*>)->Unit)){
        this.showImage = showImage;
    }

    override fun setView(rootView: View?, viewType: Int, position: Int) {
        super.setView(rootView, viewType, position)
        rootView?.let {
            if(dataList.get(position).iconType != 1){
                showImage?.invoke(it.findViewById(R.id.iv_item_icon),dataList.get(position));
            }
            it.findViewById<TextView>(R.id.tv_popup_list_text).setTextSize(TypedValue.COMPLEX_UNIT_PX,
                dataList.get(position).textSize.dip2px().toFloat())
            it.findViewById<TextView>(R.id.tv_popup_list_text).setTextColor(Color.parseColor(dataList.get(position).textColor));

            if(dataList.get(position).haveSubtext){
                it.findViewById<TextView>(R.id.tv_popup_list_subtext).setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    dataList.get(position).textSize.dip2px().toFloat())
                it.findViewById<TextView>(R.id.tv_popup_list_subtext)
                    .setTextColor(Color.parseColor(dataList.get(position).subTextColor));
            }
        }
    }

    override fun setBean(dataBinding: ViewDataBinding?, varid: Int, position: Int) {
        super.setBean(dataBinding, varid, position)
        dataBinding?.setVariable(BR.presenter,presenter);
    }

    fun onSLItemClickListener(onSLItemClickListener : ((item : SLItem<*>)->Unit)){
        this.onSLItemClickListener = onSLItemClickListener;
    }

    private val presenter = object : SLItemPresneterImpl() {

        override fun onItemSelectListener(entity: SLItem<*>) {
            super.onItemSelectListener(entity)
            onSLItemClickListener?.invoke(entity);
        }
    }
}
