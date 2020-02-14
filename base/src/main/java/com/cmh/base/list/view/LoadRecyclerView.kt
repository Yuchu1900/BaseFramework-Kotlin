package com.cmh.base.list.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmh.base.R
import com.cmh.base.list.divider.HorizontalDividerItemDecoration
import com.cmh.base.list.item.BaseItem
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem

/**
 * Created by cmh on 2017/7/18.
 * 自定义的分页加载的RecyclerView
 */
class LoadRecyclerView : RecyclerView {
    private var myLayoutManager: LayoutManager? = null
    private var myItemDecoration: ItemDecoration? = null
    private var mAdapter: FastAdapter<BaseItem<*>>? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {}
    fun getMyLayoutManager(): LayoutManager? {
        return myLayoutManager
    }

    fun setMyLayoutManager(myLayoutManager: LayoutManager?) {
        this.myLayoutManager = myLayoutManager
        layoutManager = myLayoutManager
    }

    fun getMyItemDecoration(): ItemDecoration? {
        return myItemDecoration
    }

    fun setMyItemDecoration(myItemDecoration: ItemDecoration?) {
        if (this.myItemDecoration != null) {
            removeItemDecoration(this.myItemDecoration!!)
        }
        this.myItemDecoration = myItemDecoration
        myItemDecoration?.let { addItemDecoration(it) }
    }

    fun initRecycleView() {
        setHasFixedSize(true)
        //设置布局管理器
        if (myLayoutManager == null) {
            myLayoutManager = LinearLayoutManager(this.context)
        }
        layoutManager = myLayoutManager
        //设置分隔条
        if (myItemDecoration == null) myItemDecoration = defaultItemDecoration
        addItemDecoration(myItemDecoration!!)
    }

    /**
     * 默认分割条
     *
     * @return
     */
    private val defaultItemDecoration: ItemDecoration
         get() = HorizontalDividerItemDecoration.Builder(this.context)
                .color(resources.getColor(R.color.divider_line))
                .size(1)
                .build()

    //设置数据适配器
    var myAdapter: FastAdapter<BaseItem<*>>
        get() = this.mAdapter!!
        set(mAdapter) {
            this.mAdapter = mAdapter
            adapter = mAdapter
        }
}