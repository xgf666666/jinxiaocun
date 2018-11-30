package com.micropole.inventorysystem.ui.personal.shopmall

import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.micropole.inventorysystem.R
import com.micropole.inventorysystem.adapter.partnership.IndicatorAdapter
import com.micropole.inventorysystem.util.initMagic
import com.xx.baseuilibrary.mvp.BaseMvpViewActivity
import kotlinx.android.synthetic.main.activity_order_list.*
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

/**
 * @ClassName       OrderListActivity
 * @Description     订单列表
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/23 17:17
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
class OrderListActivity : BaseMvpViewActivity() {

    val data = arrayListOf("全部","代发货","待收货","售后","评价")
    val fragmens = arrayListOf<OrderListFragment>()
    override fun getActivityLayoutId(): Int = R.layout.activity_order_list

    override fun initData() {
        for (i in data.indices){
            fragmens.add(OrderListFragment.newFragment(i))
        }
        initVp()
        initMagic()
    }

    override fun initEvent() {
    }

    fun initVp(){
        vp_order.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragmens[position]
            }

            override fun getCount(): Int {
                return fragmens.size
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

            }

        }
    }

    fun initMagic(){
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.selectedColor = Color.parseColor("#007AFF")
                simplePagerTitleView.normalColor = Color.parseColor("#666666")
                simplePagerTitleView.textSize = 13f
                simplePagerTitleView.text = data[index]
                simplePagerTitleView.setOnClickListener {
                    simplePagerTitleView.textSize = 15f
                    vp_order.currentItem = index
                }
                return simplePagerTitleView
            }

            override fun getCount(): Int {
                return data.size
            }

            override fun getIndicator(p0: Context?): IPagerIndicator? {
                return null
            }

        }
        magic_order.navigator = commonNavigator
        ViewPagerHelper.bind(magic_order,vp_order)
    }
}