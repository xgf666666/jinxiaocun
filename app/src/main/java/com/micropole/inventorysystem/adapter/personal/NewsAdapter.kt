package com.micropole.inventorysystem.adapter.personal

import android.content.Context
import android.support.v4.widget.SlidingPaneLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.baseapplibrary.adapter.DataBindAdapter
import com.micropole.inventorysystem.R
import com.micropole.inventorysystem.entity.NewsBean

/**
 * @ClassName       NewsAdapter
 * @Description     侧滑菜单
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/23 11:31
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
class NewsAdapter(val r : RecyclerView) : DataBindAdapter<NewsBean>(1, R.layout.item_news_view) {
    var a : BaseQuickAdapter<NewsBean,BaseViewHolder>

    init {
        r.layoutManager = Manager(r.context)
        a = object : BaseQuickAdapter<NewsBean,BaseViewHolder>(R.layout.item_news_delete) {
            override fun convert(helper: BaseViewHolder?, item: NewsBean?) {

            }
        }
        r.adapter = a

        a.setOnItemClickListener { adapter, view, position ->
            if (view.tag != "delete")
            onItemChildClickListener?.onItemChildClick(this,view,position)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder.itemViewType == LOADING_VIEW){
            val view = View(mContext)
            view.tag = "delete"
            view.layoutParams = holder.itemView.layoutParams
            a.addFooterView(view)
        }
    }

    override fun bindToRecyclerView(recyclerView: RecyclerView?) {
        super.bindToRecyclerView(recyclerView)
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                r.scrollBy(dx,dy)
            }
        })
    }

    override fun loadMoreEnd() {
        super.loadMoreEnd()
        a.removeAllFooterView()
    }

    override fun loadMoreComplete() {
        super.loadMoreComplete()
        a.removeAllFooterView()
    }

    override fun setNewData(data: MutableList<NewsBean>?) {
        super.setNewData(data)
        a.setNewData(data)
    }

    override fun addData(newData: MutableCollection<out NewsBean>) {
        super.addData(newData)
        a.addData(newData)
    }

    class Manager(context: Context) : LinearLayoutManager(context){
        var isscroll = true
        override fun canScrollVertically(): Boolean {
            return isscroll && super.canScrollVertically()
        }
    }
}