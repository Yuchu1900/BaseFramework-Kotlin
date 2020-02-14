package com.cmh.base.list.view

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.AnyThread
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cmh.base.R
import com.cmh.base.databinding.BaseViewRecyclerviewBinding
import com.cmh.base.list.item.BaseItem
import com.cmh.base.list.loader.*
import com.kennyc.view.MultiStateView
import com.kennyc.view.MultiStateView.ViewState
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter

class LoadListView : ConstraintLayout, OnLoadState {
    var recyclerView: LoadRecyclerView? = null //列表页面
    protected var swipeRefreshLayout: SwipeRefreshLayout? = null //加载页面
    var multiStateView: MultiStateView? = null //列表页面和空白页面的容器（控制列表页面/空白页切换）
        protected set
    var binding: BaseViewRecyclerviewBinding? = null
    var mItemAdapter: ItemAdapter<BaseItem<*>>? = null //列表适配器
    var mFastAdapter: FastAdapter<BaseItem<*>>? = null
    protected var mDataObserver: AdapterDataObserver? = null
    var loader: ILoader? = null
        get(){return field}
        set(value){
            field = value
            field?.setLoadStateListener(this)
        }

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(this.context), R.layout.base_view_recyclerview, this, false)
        this.addView(binding!!.getRoot())
        multiStateView = binding!!.multiStateView
        swipeRefreshLayout = binding!!.refreshFrame
        recyclerView = binding!!.recyclerview
        initMultiView()
        initRecyclerView()
        initSwipeRefreshLayout()
    }

    fun initRecyclerView() {
        recyclerView!!.initRecycleView()
        recyclerView!!.myAdapter = myAdapter
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                var lastVisibleItem = 0
                val linearManager: LinearLayoutManager?
                if (this@LoadListView.recyclerView!!.getMyLayoutManager() is LinearLayoutManager) {
                    linearManager = this@LoadListView.recyclerView!!.getMyLayoutManager() as LinearLayoutManager?
                    lastVisibleItem = linearManager!!.findLastVisibleItemPosition()
                } else {
                    return
                }
                val totalItemCount = linearManager.itemCount
                if (lastVisibleItem >= totalItemCount - 3 && dy > 0) {
                    loader?.loadMore()
                }
            }
        })
        //注册数据变化监听器
        mDataObserver = object : AdapterDataObserver() {
            override fun onChanged() {
                updateViewShowState()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                updateViewShowState()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                updateViewShowState()
            }

            private fun updateViewShowState() {
                if (recyclerView!!.myAdapter.itemCount > 0) {
                    showContent()
                } else {
                    showEmptyPage()
                }
            }
        }
    }

    private fun initMultiView() {
        addEmptyView()
        showContent()
    }

    private fun addEmptyView() {
        val emptyView = multiStateView!!.getView(ViewState.EMPTY)
        val containerView = emptyView!!.findViewById<RelativeLayout>(R.id.rl_empty)
        containerView.removeAllViews()
        val txtView = TextView(this.context)
        txtView.text = ""
        txtView.textSize = 18f
        txtView.setTextColor(resources.getColor(R.color.empty_text))
        val params = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        containerView.addView(txtView, params)
    }

    fun initSwipeRefreshLayout() {
        swipeRefreshLayout!!.setColorSchemeColors(resources.getColor(R.color.refresh_scheme))
        swipeRefreshLayout!!.setOnRefreshListener { loader?.refresh() }
    }

    override fun onAttachedToWindow() {
        recyclerView!!.myAdapter.registerAdapterDataObserver(mDataObserver!!)
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        recyclerView!!.myAdapter.unregisterAdapterDataObserver(mDataObserver!!)
        super.onDetachedFromWindow()
    }

    /**
     * 加载过程监听
     * @param state 数据加载状态
     * @param message 附带信息
     */
    override fun onLoadState(state: OnLoadState.State, message: String) {
        Log.i("cmh", "LoadListView_onLoad():state=$state; message=$message")
        when (state) {
            OnLoadState.State.START -> swipeRefreshLayout!!.isRefreshing = true
            OnLoadState.State.END -> swipeRefreshLayout!!.isRefreshing = false
            OnLoadState.State.ERROR -> {
                swipeRefreshLayout!!.isRefreshing = false
                showErrorPage()
                Toast.makeText(context, R.string.load_fail, Toast.LENGTH_LONG).show()
            }
            OnLoadState.State.CLEAR -> mItemAdapter!!.clear()
        }
    }

    val myAdapter: FastAdapter<BaseItem<*>>
        get() {
            mFastAdapter = FastAdapter()
            mItemAdapter = ItemAdapter()
            return mFastAdapter!!.addAdapter(0, mItemAdapter!!)
        }

    fun notifyDataSetChanged() {
        mFastAdapter!!.notifyDataSetChanged()
    }

    fun addItem(item: BaseItem<*>) {
        mItemAdapter!!.add(item)
    }

    fun withSavedInstanceStateForAdapter(bundle: Bundle?) {
        mFastAdapter!!.withSavedInstanceState(bundle)
    }

    fun saveInstanceStateForAdapter(outState: Bundle?): Bundle {
        return mFastAdapter!!.saveInstanceState(outState!!)
    }

    /**
     * 显示列表页面
     */
    fun showContent() {
        multiStateView?.viewState = ViewState.CONTENT
    }

    /**
     * 显示空白页
     */
    fun showEmptyPage() {
        multiStateView?.viewState = ViewState.EMPTY
    }

    /**
     * 显示错误页面
     */
    fun showErrorPage() {
        multiStateView?.viewState = ViewState.ERROR
    }

    /**
     * 加载列表数据
     */
    fun loadData() {
        loader?.startLoad()
    }

    /**
     * 重新加载列表数据
     */
    fun refresh() {
        loader?.refresh()
    }

    /**
     * 获取页数
     * @return
     */
    /**
     * 设置页数
     * @return
     */
    var page: Int
        get() = loader?.page!!
        set(page) {
            loader?.page = page
        }

    /**
     * 获取每页的行数
     * @return
     */
    /**
     * 设置每页的行数
     * @return
     */
    var rows: Int
        get() = loader?.rows!!
        set(rows) {
            loader?.rows = rows
        }

    /**
     * 是否允许下拉刷新
     * @param enable
     */
    fun setRefreshEnable(enable: Boolean) {
        swipeRefreshLayout!!.isEnabled = enable
    }

    /**
     * 布局管理器
     * @param mLayoutManager
     */
    fun setLayoutManager(mLayoutManager: RecyclerView.LayoutManager?) {
        recyclerView!!.setMyLayoutManager(mLayoutManager)
    }

    /**
     * 分割条
     * @param itemDecoration
     */
    fun setItemDecoration(itemDecoration: ItemDecoration?) {
        recyclerView!!.setMyItemDecoration(itemDecoration)
    }
}