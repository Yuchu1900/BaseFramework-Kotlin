package com.cmh.base.list.loader.list

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.cmh.base.list.loader.ILoader
import com.cmh.base.list.loader.OnLoadState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ListLoader: ILoader {
    override var page = 1 //页（按页加载，一次加载一页）
    override var rows = 20 //一页包含的条数
    private var isLoading = false
    private var hasMore = true
    private lateinit var loadState: OnLoadState
    lateinit var listBinder: OnListBinder<*>
    private var disposable: Disposable? = null

    @Synchronized
    override fun loadMore() {
        if (isLoading || !hasMore) {
            return
        }
        startLoad()
    }

    override fun setLoadStateListener(onLoadState: OnLoadState) {
        this.loadState = onLoadState
    }

    override fun refresh() {
        cancelNowRequest()
        page = 1
        hasMore = true
        startLoad()
    }

    override fun cancelNowRequest() {
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
            disposable = null
        }
    }

    override fun startLoad() {
        loadData(listBinder)
    }


    fun <T> loadData(listBinder: OnListBinder<T>) {
        Log.i("cmh", "ListLoader_loadData()")
        isLoading = true
        loadState.onLoadState(OnLoadState.State.START, "start")
        disposable = listBinder.bindData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data: List<T>? ->
                    Log.i("cmh", "ListLoader_loadData():onNext page=$page")
                    isLoading = false
                    Handler(Looper.getMainLooper()).postDelayed({ loadState.onLoadState(OnLoadState.State.END, "end") }, 200)
                    if (data != null) {
                        Log.i("cmh", "list.size=" + data.size)
                        if (page == 1) {
                            loadState.onLoadState(OnLoadState.State.CLEAR, "clear")
                        }
                        listBinder.bindItem(data, page, data.size)
                        if (data.size < rows) {
                            hasMore = false
                        } else {
                            page += 1
                        }
                    }
                }, { throwable: Throwable? ->
                    isLoading = false
                    Log.i("cmh", "ListLoader_loadData():error=" + if (throwable != null) throwable.message else "error")
                    loadState.onLoadState(OnLoadState.State.ERROR, throwable?.message ?: "error")
                }) { cancelNowRequest() }
    }
}