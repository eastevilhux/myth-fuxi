package com.starlight.dot.framework.widget.popup

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.starlight.dot.framework.R
import com.starlight.dot.framework.adapter.PopupListAdapter
import com.starlight.dot.framework.databinding.PopupListBinding
import com.starlight.dot.framework.entity.SLBuilder
import com.starlight.dot.framework.entity.SLItem
import com.starlight.dot.framework.widget.BasePopupWindow
import com.starlight.dot.framework.widget.PopupBaseBuilder

class ListPopup : BasePopupWindow<PopupListBinding,ListPopup.Builder>{
    companion object{
        private const val TAG = "SL_ListPopup==>";
    }

    private lateinit var adapter : PopupListAdapter;

    private var tag : Int = 0;

    private var onSLItemClickListener : ((item : SLItem<*>,tag : Int)->Unit)? = null;

    private constructor(builder: Builder) : super(builder) {
        adapter = PopupListAdapter(builder.context,builder.itemList as MutableList<SLItem<*>>?);
        adapter.addShowImage { imageView, slitem ->
            builder.showImage?.invoke(imageView,slitem);
        }
        this.onSLItemClickListener = builder.onSLItemClickListener;
        adapter.onSLItemClickListener {
            onSLItemClickListener?.invoke(it,tag());
        }
        dataBinding.adapter = adapter;
        dataBinding.titleText = titleText;

        val lineDivider = DividerItemDecoration(builder.context, DividerItemDecoration.VERTICAL)
        lineDivider.setDrawable(ContextCompat.getDrawable(builder.context, R.drawable.line_default)!!)
        dataBinding.rvPopup.addItemDecoration(lineDivider)
    }


    override fun getResourceId(): Int {
        return R.layout.popup_list;
    }

    fun notifyItemList(itemList: MutableList<ListItem>){
        adapter.dataList = itemList as MutableList<SLItem<*>>;
        adapter.notifyDataSetChanged();
    }

    fun setTag(tag : Int){
        this.tag = tag;
    }

    fun tag(): Int {
        return tag;
    }

    fun onSLItemClickListener(onSLItemClickListener : ((item : SLItem<*>,tag : Int)->Unit)){
        this.onSLItemClickListener = onSLItemClickListener;
    }

    class Builder(context: Context) : PopupBaseBuilder<ListPopup>(context) {
        internal var itemList : MutableList<ListItem>? = null;
        internal var showImage : ((imageView : ImageView, slitem : SLItem<*>)->Unit)? = null;
        internal var onSLItemClickListener : ((item : SLItem<*>,tag : Int)->Unit)? = null;

        fun itemList(itemList : MutableList<ListItem>): Builder {
            this.itemList = itemList;
            return this;
        }

        fun showImage(showImage : ((imageView : ImageView, slitem : SLItem<*>)->Unit)): Builder {
            this.showImage = showImage;
            return this;
        }

        fun onSLItemClickListener(onSLItemClickListener : ((item : SLItem<*>,tag : Int)->Unit)): Builder {
            this.onSLItemClickListener = onSLItemClickListener;
            return this;
        }

        override fun builder(): ListPopup {
            return ListPopup(this);
        }
    }
}

class ListItem : SLItem<ListItem.Builder>{

    private constructor(builder: Builder) : super(builder)

    class Builder(itemText: String) : SLBuilder<ListItem>(itemText) {
        override fun builder(): ListItem {
            return ListItem(this);
        }
    }
}
