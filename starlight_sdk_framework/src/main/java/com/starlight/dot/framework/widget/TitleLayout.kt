package com.starlight.dot.framework.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.starlight.dot.framework.R
import com.starlight.dot.framework.databinding.LayoutStarlighTitleBinding
import com.starlight.dot.framework.utils.dip2px

class TitleLayout : FrameLayout{
    private lateinit var dataBinding : LayoutStarlighTitleBinding;
    private val haveBackText = ObservableField<Boolean>();
    private val haveBackImage = ObservableField<Boolean>();
    private val haveTitle = ObservableField<Boolean>();
    private val haveSubTitle = ObservableField<Boolean>();
    private val haveMenuImage = ObservableField<Boolean>();
    private val haveMenuText = ObservableField<Boolean>();
    private val backText = ObservableField<String>();
    private val titleText = ObservableField<String>();
    private val subTitleText = ObservableField<String>();
    private val menuText = ObservableField<String>();

    private var backDrawable : Drawable? = null;
    private var backImageWidth : Int = 0;
    private var backImageHeight : Int = 0;

    private var backTextColor : Int = 0;
    private var backTextSize : Int = 0;
    private var backTextStyle : Int = 0;

    private var titleTextSize : Int = 0;
    private var titleTextColor : Int = 0;
    private var titleTextStyle : Int = 0;

    private var subTitleTextSize : Int = 0;
    private var subTitleTextColor : Int = 0;
    private var subTitleTextStyle : Int = 0;

    private var menuDrawable : Drawable? = null;
    private var menuImageWidth : Int = 0;
    private var menuImageHeight : Int = 0;

    private var menuTextSize : Int = 0;
    private var menuTextColor : Int = 0;
    private var menuTextStyle : Int = 0;

    private var backImageMarginLeft : Int = 0;
    private var backImagePadding : Int = 0;
    private var backImagePaddingLeft : Int = 0
    private var backImagePaddingRight : Int = 0
    private var backImagePaddingTop : Int = 0
    private var backImagePaddingBottom : Int = 0


    private var backTextMarginLeft : Int = 0;
    private var menuImageMarginRight : Int = 0;
    private var menuTextMarginRight : Int = 0;

    private var onTitleLayoutListener : OnTitleLayoutListener? = null;

    constructor(context: Context) : super(context)

    constructor(context: Context,attrs: AttributeSet) : super(context,attrs){
        initAttr(attrs);
    }

    constructor(context: Context,attrs: AttributeSet,defStyleAttr : Int) : super(context,attrs,defStyleAttr){
        initAttr(attrs);
    }

    constructor(context: Context,attrs: AttributeSet,defStyleAttr : Int,defStyleRes : Int) : super(context,attrs,defStyleAttr,defStyleRes){
        initAttr(attrs);
    }

    private fun initAttr(attrs: AttributeSet){
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleLayout)
        val backType = ta.getInt(R.styleable.TitleLayout_lyn_back_type,0);
        when(backType){
            0-> {
                haveBackText.set(false)
                haveBackImage.set(false);
            }
            1->{
                haveBackImage.set(true);
                haveBackText.set(false);
            }
            2->{
                haveBackImage.set(false);
                haveBackText.set(true);
            }
            3->{
                haveBackImage.set(true);
                haveBackText.set(true);
            }
            else-> {
                haveBackText.set(false)
                haveBackImage.set(false);
            }
        }
        //获取返回图片资源id
        backDrawable = ta.getDrawable(R.styleable.TitleLayout_lyn_back_src);
        backImageWidth = ta.getDimensionPixelOffset(R.styleable.TitleLayout_lyn_back_imageWidth,0);
        backImageHeight = ta.getDimensionPixelOffset(R.styleable.TitleLayout_lyn_back_imageHeight,0);
        backImagePadding = ta.getDimensionPixelOffset(R.styleable.TitleLayout_lyn_back_image_padding,0);
        backImagePaddingLeft = ta.getDimensionPixelOffset(R.styleable.TitleLayout_lyn_back_image_paddingLeft,0);
        backImagePaddingRight = ta.getDimensionPixelOffset(R.styleable.TitleLayout_lyn_back_image_paddingRight,0);
        backImagePaddingTop = ta.getDimensionPixelOffset(R.styleable.TitleLayout_lyn_back_image_paddingTop,0);
        backImagePaddingBottom = ta.getDimensionPixelOffset(R.styleable.TitleLayout_lyn_back_image_paddingBottom,0);
        backText.set(ta.getString(R.styleable.TitleLayout_lyn_back_text));
        backTextColor = ta.getColor(R.styleable.TitleLayout_lyn_back_textColor,context.getColor(R.color.colorAppText));
        backTextSize = ta.getDimensionPixelOffset(R.styleable.TitleLayout_lyn_back_textSize,13);
        backTextStyle = ta.getInt(R.styleable.TitleLayout_lyn_back_textStyle,3);
        haveTitle.set(ta.getBoolean(R.styleable.TitleLayout_lyn_have_title,true));
        titleTextSize = ta.getDimensionPixelOffset(R.styleable.TitleLayout_lyn_title_size,15);
        titleText.set(ta.getString(R.styleable.TitleLayout_lyn_title_text));
        titleTextColor = ta.getColor(R.styleable.TitleLayout_lyn_title_color,context.getColor(R.color.colorAppText));
        titleTextStyle = ta.getInt(R.styleable.TitleLayout_lyn_title_textStyle,0);
        haveSubTitle.set(ta.getBoolean(R.styleable.TitleLayout_lyn_have_subtitle,false));
        subTitleTextSize = ta.getDimensionPixelOffset(R.styleable.TitleLayout_lyn_subTitle_size,15);
        subTitleText.set(ta.getString(R.styleable.TitleLayout_lyn_subTitle_text));
        subTitleTextColor = ta.getColor(R.styleable.TitleLayout_lyn_subTitle_color,context.getColor(R.color.colorAppText));
        subTitleTextStyle = ta.getInt(R.styleable.TitleLayout_lyn_subTitle_textStyle,0);
        val menuType = ta.getInt(R.styleable.TitleLayout_lyn_menu_type,0);
        when(menuType){
            0->{
                haveMenuText.set(false);
                haveMenuImage.set(false);
            }
            1->{
                haveMenuText.set(false);
                haveMenuImage.set(true);
            }
            2->{
                haveMenuText.set(true);
                haveMenuImage.set(false);
            }
            3->{
                haveMenuText.set(true);
                haveMenuImage.set(true);
            }
            else->{
                haveMenuText.set(false);
                haveMenuImage.set(false);
            }
        }
        menuDrawable = ta.getDrawable(R.styleable.TitleLayout_lyn_menu_src);
        menuImageWidth = ta.getInt(R.styleable.TitleLayout_lyn_menu_imageWidth,0);
        menuImageHeight = ta.getInt(R.styleable.TitleLayout_lyn_menu_imageHeight,0);
        menuText.set(ta.getString(R.styleable.TitleLayout_lyn_menu_text));
        menuTextSize = ta.getDimensionPixelOffset(R.styleable.TitleLayout_lyn_menu_textSize,15);
        menuTextColor = ta.getColor(R.styleable.TitleLayout_lyn_menu_textColor,context.getColor(R.color.colorAppText));
        menuTextStyle = ta.getInt(R.styleable.TitleLayout_lyn_menu_menuTextStyle,0);
        backImageMarginLeft = ta.getDimensionPixelOffset(R.styleable.TitleLayout_lyn_backImage_marginLeft,0);
        backTextMarginLeft = ta.getDimensionPixelOffset(R.styleable.TitleLayout_lyn_bankText_marginLeft,0);
        menuImageMarginRight = ta.getDimensionPixelOffset(R.styleable.TitleLayout_lyn_menuImage_marginRight,0);
        menuTextMarginRight = ta.getDimensionPixelOffset(R.styleable.TitleLayout_lyn_menuText_marginRight,0);
        ta.recycle();
        init();
    }

    protected fun init(){
        dataBinding = DataBindingUtil.inflate<LayoutStarlighTitleBinding>(LayoutInflater.from(context),
            R.layout.layout_starligh_title, this, true);
        dataBinding.haveBackImage = haveBackImage;
        dataBinding.haveBackText = haveBackText;
        dataBinding.haveTitle = haveTitle;
        dataBinding.haveSubTitle = haveSubTitle;
        dataBinding.haveMenuImage = haveMenuImage;
        dataBinding.haveMenuText = haveMenuText;
        dataBinding.menuText = menuText;
        dataBinding.titleText = titleText;
        dataBinding.backText = backText;
        dataBinding.titleLayout = this;
        initBack();
        initTitle();
        initMenu();
    }

    protected fun initBack(){
        val params = dataBinding.ivSlBack.layoutParams as ConstraintLayout.LayoutParams;
        if(backImageWidth > 0){
            params.width = backImageWidth;
        }
        if(backImageHeight > 0){
            params.height = backImageHeight;
        }
        if(backDrawable != null){
            dataBinding.ivSlBack.setImageDrawable(backDrawable);
        }
        if(backImageMarginLeft > 0){
            params.marginStart = backImageMarginLeft;
        }
        val pLeft = if(backImagePaddingLeft > 0){
            backImagePaddingLeft
        }else{
            backImagePadding;
        }
        val pRight = if(backImagePaddingRight > 0){
            backImagePaddingRight;
        }else{
            backImagePadding;
        }
        val pTop = if(backImagePaddingTop > 0){
            backImagePaddingTop
        }else{
            backImagePadding;
        }
        val pBottom = if(backImagePaddingBottom > 0){
            backImagePaddingBottom;
        }else{
            backImagePadding;
        }
        if(pLeft > 0 && pRight > 0 && pTop > 0 && pBottom > 0){
            dataBinding.ivSlBack.setPadding(pLeft,pTop,pRight,pBottom);
        }
        dataBinding.ivSlBack.layoutParams = params;

        val backTextParams = dataBinding.tvSlBack.layoutParams as ConstraintLayout.LayoutParams;
        if(backTextColor != 0){
            dataBinding.tvSlBack.setTextColor(backTextColor);
        }
        backTextParams.width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        backTextParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        if(backTextSize != 0){
            dataBinding.tvSlBack.setTextSize(backTextSize.toFloat());
        }
        if(backTextMarginLeft > 0){
            backTextParams.leftMargin = backTextMarginLeft;
        }
        when(backTextStyle){
            1-> dataBinding.tvSlBack.setTypeface(Typeface.DEFAULT_BOLD)
            2-> dataBinding.tvSlBack.setTypeface(null,Typeface.ITALIC);
            3-> dataBinding.tvSlBack.setTypeface(null,Typeface.NORMAL);
        }
        dataBinding.tvSlBack.layoutParams = backTextParams;
    }

    protected fun initTitle(){
        if(haveTitle.get() == true){
            if(titleTextSize > 0){
                dataBinding.tvSlTitle.setTextSize(titleTextSize.toFloat());
            }
            if(titleTextColor != 0){
                dataBinding.tvSlTitle.setTextColor(titleTextColor);
            }
            when(titleTextStyle){
                1-> dataBinding.tvSlTitle.setTypeface(Typeface.DEFAULT_BOLD)
                2-> dataBinding.tvSlTitle.setTypeface(null,Typeface.ITALIC);
                3-> dataBinding.tvSlTitle.setTypeface(null,Typeface.NORMAL);
            }
        }
        if(haveSubTitle.get() == true){
            if(subTitleTextSize > 0){
                dataBinding.tvSlSubtitle.setTextSize(subTitleTextSize.toFloat());
            }
            if(titleTextColor != 0){
                dataBinding.tvSlSubtitle.setTextColor(subTitleTextColor);
            }
            when(subTitleTextStyle){
                1-> dataBinding.tvSlSubtitle.setTypeface(Typeface.DEFAULT_BOLD)
                2-> dataBinding.tvSlSubtitle.setTypeface(null,Typeface.ITALIC);
                3-> dataBinding.tvSlSubtitle.setTypeface(null,Typeface.NORMAL);
            }
        }
        dataBinding.haveSubTitle = haveSubTitle
        dataBinding.subTitleText = subTitleText;
    }

    protected fun initMenu(){
        var params : ConstraintLayout.LayoutParams?  = dataBinding.ivSlMenuimage.layoutParams as ConstraintLayout.LayoutParams?;
        if(params == null){
            params = if(menuImageWidth != 0 || menuImageHeight != 0){
                ConstraintLayout.LayoutParams(menuImageWidth,menuImageHeight);
            }else{
                ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
            }
        }else{
            if(menuImageWidth != 0){
                params.width = menuImageWidth;
            }
            if(menuImageHeight != 0){
                params.height = menuImageHeight;
            }
        }
        if(menuImageMarginRight > 0){
            params.rightMargin = menuImageMarginRight;
        }
        if(menuDrawable != null){
            dataBinding.ivSlMenuimage.setImageDrawable(menuDrawable);
        }
        dataBinding.ivSlMenuimage.layoutParams = params;

        if(menuTextSize != 0){
            dataBinding.tvSlMenutext.setTextSize(menuTextSize.toFloat());
        }
        if(menuTextColor != 0){
            dataBinding.tvSlMenutext.setTextColor(menuTextColor);
        }
        when(menuTextStyle){
            1-> dataBinding.tvSlMenutext.setTypeface(Typeface.DEFAULT_BOLD)
            2-> dataBinding.tvSlMenutext.setTypeface(null,Typeface.ITALIC);
            3-> dataBinding.tvSlMenutext.setTypeface(null,Typeface.NORMAL);
        }
        val menuTextParams = dataBinding.tvSlMenutext.layoutParams as ConstraintLayout.LayoutParams;
        if(menuTextParams != null){
            menuTextParams.marginEnd = menuTextMarginRight;
        }
    }

    fun onViewClick(view : View){
        when(view.id){
            R.id.iv_sl_back->{
                onTitleLayoutListener?.onBackIconClickListener(dataBinding.ivSlBack)
            }
            R.id.tv_sl_back->{
                onTitleLayoutListener?.onBackTextClickListener(dataBinding.tvSlBack);
            }
            R.id.tv_sl_title->{
                onTitleLayoutListener?.onTitleClickListener(dataBinding.tvSlTitle);
            }
            R.id.tv_sl_subtitle->{
                onTitleLayoutListener?.onSubTitleClickListener(dataBinding.tvSlSubtitle);
            }
            R.id.iv_sl_menuimage->{
                onTitleLayoutListener?.onMenuIconClickListener(dataBinding.ivSlMenuimage);
            }
            R.id.tv_sl_menutext->{
                onTitleLayoutListener?.onMenuTextClickListener(dataBinding.tvSlMenutext);
            }
        }
    }

    fun setTitle(titleText : String){
        this.titleText.set(titleText);
    }

    fun setTitle(titleResId : Int){
        val s = context.getString(titleResId);
        setTitle(s);
    }

    fun getTitle(): String {
        return this.titleText.get()?:"";
    }

    fun setTitleTextSize(size : Int){
        titleTextSize = size;
        dataBinding.tvSlTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,size.dip2px().toFloat());
    }

    fun setTitleTextColor(titleTextColor : Int){
        this.titleTextColor = titleTextColor;
        dataBinding.tvSlTitle.setTextColor(titleTextColor);
    }

    fun setBackType(backType : Int){
        when(backType){
            0-> {
                haveBackText.set(false)
                haveBackImage.set(false);
            }
            1->{
                haveBackImage.set(true);
                haveBackText.set(false);
            }
            2->{
                haveBackImage.set(false);
                haveBackText.set(true);
            }
            3->{
                haveBackImage.set(true);
                haveBackText.set(true);
            }
            else-> {
                haveBackText.set(false)
                haveBackImage.set(false);
            }
        }
    }

    fun setBackIcon(backResId : Int){
        if(haveBackImage.get() == true){
            backDrawable = ContextCompat.getDrawable(context,backResId);
            dataBinding.ivSlBack.setImageDrawable(backDrawable);
        }
    }

    fun setBackText(backText : String){
        if(haveBackText.get() == true){
            this.backText.set(backText);
        }
    }

    fun setBackTextSize(backTextSize : Int){
        if(haveBackText.get() == true){
            this.backTextSize = backTextSize;
            dataBinding.tvSlBack.setTextSize(TypedValue.COMPLEX_UNIT_PX,backTextSize.dip2px().toFloat());
        }
    }

    fun setBackTextStyle(backStyle : Int){
        if(haveBackText.get() == true){
            this.backTextStyle = backStyle;
            when(backTextStyle){
                1-> dataBinding.tvSlBack.setTypeface(Typeface.DEFAULT_BOLD)
                2-> dataBinding.tvSlBack.setTypeface(null,Typeface.ITALIC);
                3-> dataBinding.tvSlBack.setTypeface(null,Typeface.NORMAL);
            }
        }
    }

    fun setBackTextColor(backTextColor : Int){
        if(haveBackText.get() == true){
            this.backTextColor = backTextColor;
            dataBinding.tvSlBack.setTextColor(backTextColor);
        }
    }

    fun setMenuType(menuType : Int){
        when(menuType){
            0->{
                haveMenuImage.set(false);
                haveMenuText.set(false);
            }
            1->{
                haveMenuImage.set(true);
                haveMenuText.set(false);
            }
            2->{
                haveMenuImage.set(false);
                haveMenuText.set(true);
            }
            3->{
                haveMenuImage.set(true);
                haveMenuText.set(true);
            }
        }
    }

    fun setMenuText(text : String){
        if(haveMenuText.get() == true){
            menuText.set(text);
        }
    }

    fun setMenuIcon(menuIconResId : Int){
        if(haveMenuImage.get() == true){
            menuDrawable = ContextCompat.getDrawable(context,menuIconResId);
            dataBinding.ivSlMenuimage.setImageDrawable(menuDrawable);
        }
    }

    fun setMenuIconSize(width : Int,height : Int){
        if(haveMenuImage.get() == true){
            val params = dataBinding.ivSlMenuimage.layoutParams;
            params.width = width;
            params.height = height;
            dataBinding.ivSlMenuimage.layoutParams = params;
        }
    }

    fun setMenuTextSize(menuTextSize : Int){
        if(menuTextSize != 0){
            this.menuTextSize = menuTextSize;
            dataBinding.tvSlMenutext.setTextSize(TypedValue.COMPLEX_UNIT_PX,menuTextSize.dip2px().toFloat());
        }
    }

    fun setMenuTextSyle(textStyle : Int){
        this.menuTextStyle = textStyle;
        when(menuTextStyle){
            1-> dataBinding.tvSlMenutext.setTypeface(Typeface.DEFAULT_BOLD)
            2-> dataBinding.tvSlMenutext.setTypeface(null,Typeface.ITALIC);
            3-> dataBinding.tvSlMenutext.setTypeface(null,Typeface.NORMAL);
        }
    }

    fun setMenuTextColor(menuTextColor : Int){
        if(haveMenuText.get() == true){
            this.menuTextColor = menuTextColor;
            dataBinding.tvSlMenutext.setTextColor(menuTextColor);
        }
    }

    fun setOnTitleLayoutListener(onTitleLayoutListener : OnTitleLayoutListener){
        this.onTitleLayoutListener = onTitleLayoutListener;
    }

    fun getOnTitleLayoutListener(): OnTitleLayoutListener? {
        return onTitleLayoutListener;
    }

    object MenuType{
        const val TYPE_NONE = 0;
        const val TYPE_IMAGE = 1;
        const val TYPE_TEXT = 2;
        const val TYPE_IMAGE_TEXT = 3;
    }

    object BackType{
        const val TYPE_NONE = 0;
        const val TYPE_IMAGE = 1;
        const val TYPE_TEXT = 2;
        const val TYPE_IMAGE_TEXT = 3;
    }

    interface OnTitleLayoutListener{
        fun onBackTextClickListener(backTextView: TextView);

        fun onBackIconClickListener(menuImageView : ImageView);

        fun onTitleClickListener(titleTextView : TextView);

        fun onSubTitleClickListener(subTitleView : TextView);

        fun onMenuTextClickListener(menuTextView : TextView);

        fun onMenuIconClickListener(menuImageView : ImageView);
    }
}
