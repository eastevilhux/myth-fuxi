package com.starlight.dot.framework.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.ObservableField
import com.starlight.dot.framework.R
import com.starlight.dot.framework.databinding.LayoutSlsetBinding

class SlsetLayout : FrameLayout{
    private lateinit var dataBinding : LayoutSlsetBinding;
    private var itemWeight : Int = 1;
    private var contentWeight : Int = 3;
    private lateinit var itemText : ObservableField<String>;
    private lateinit var contentText : ObservableField<String>;
    private lateinit var haveItemIcon : ObservableField<Boolean>;
    private lateinit var haveItemArrow : ObservableField<Boolean>;
    private lateinit var haveContentIcon : ObservableField<Boolean>;
    private lateinit var haveContentEndIcon : ObservableField<Boolean>;
    private lateinit var haveContentArrow : ObservableField<Boolean>;

    private var itemIcon : Drawable? = null;
    private var itemArrow : Drawable? = null;
    private var contentIcon : Drawable? = null;
    private var contentEndIcon : Drawable? = null;
    private var contentArrow : Drawable? = null;

    private var itemIconWidth : Int = 0;
    private var itemIconHeight : Int = 0;
    private var itemArrowWidth : Int = 0;
    private var itemArrowHeight : Int = 0;
    private var contentIconWidth : Int = 0;
    private var contentIconHeight : Int = 0;
    private var contentArrowWidth : Int = 0;
    private var contentArrowHeight : Int = 0;
    private var contentEndIconWidth : Int = 0;


    constructor(context: Context) : super(context){
        init();
    }

    constructor(context: Context, attrs: AttributeSet) : super(context,attrs){
        initAttr(attrs);
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr : Int) : super(context,attrs,defStyleAttr){
        initAttr(attrs);
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr : Int, defStyleRes : Int) : super(context,attrs,defStyleAttr,defStyleRes){
        initAttr(attrs);
    }

    private fun initAttr(attrs: AttributeSet){
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SlsetLayout);
        haveItemIcon = ObservableField(ta.getBoolean(R.styleable.SlsetLayout_lyn_item_iconFlag,false));
        haveItemArrow = ObservableField(ta.getBoolean(R.styleable.SlsetLayout_lyn_item_arrowFlag,false));
        haveContentArrow = ObservableField(ta.getBoolean(R.styleable.SlsetLayout_lyn_content_arrowFlag,false));
        haveContentIcon = ObservableField(ta.getBoolean(R.styleable.SlsetLayout_lyn_content_iconFlag,false));
        haveContentEndIcon = ObservableField(ta.getBoolean(R.styleable.SlsetLayout_lyn_content_endIconFlag,false));

        itemIcon = ta.getDrawable(R.styleable.SlsetLayout_lyn_item_iconSrc);
        itemArrow = ta.getDrawable(R.styleable.SlsetLayout_lyn_item_arrowSrc);
        contentIcon = ta.getDrawable(R.styleable.SlsetLayout_lyn_content_iconSrc);
        contentEndIcon = ta.getDrawable(R.styleable.SlsetLayout_lyn_content_endIconSrc);
        contentArrow = ta.getDrawable(R.styleable.SlsetLayout_lyn_content_arrowScr);

        itemText = ObservableField(ta.getString(R.styleable.SlsetLayout_lyn_item_text)?:"");
        contentText = ObservableField(ta.getString(R.styleable.SlsetLayout_lyn_content_text)?:"");
        ta.recycle();
        init();
    }

    private fun init(){

    }
}
