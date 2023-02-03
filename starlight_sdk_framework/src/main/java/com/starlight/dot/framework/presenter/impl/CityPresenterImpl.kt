package com.starlight.dot.framework.presenter.impl

import com.starlight.dot.framework.entity.SLCity
import com.starlight.dot.framework.presenter.CityPresenter

abstract class CityPresenterImpl : CityPresenter{
    /**
     * 列表Item点击事件监听
     * create by Eastevil at 2022/5/10 15:46
     * @author Eastevil
     * @param entity
     *      点击的item对象
     * @return
     *      void
     */
    override fun onItemClickListener(entity: SLCity, position: Int) {

    }

    /**
     * 列表Item选择事件监听
     * create by Eastevil at 2022/5/10 15:46
     * @author Eastevil
     * @param entity
     *      选择的item对象
     * @return
     *      void
     */
    override fun onItemSelectListener(entity: SLCity) {
    }

    /**
     * 列表Item菜单按钮点击事件监听
     * create by Eastevil at 2022/5/10 15:46
     * @author Eastevil
     * @param entity
     *      点击的Item对象
     * @return
     *      void
     */
    override fun onItemMenuClickListener(entity: SLCity) {
    }

    /**
     * 列表Item编辑事件监听
     * create by Eastevil at 2022/5/10 15:46
     * @author Eastevil
     * @param entity
     *      需要编辑的item对象
     * @return
     *      void
     */
    override fun onEditListener(entity: SLCity) {
    }

}
