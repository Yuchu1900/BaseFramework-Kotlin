package com.cmh.base.list.loader.common

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.cmh.base.list.loader.ILoader
import com.cmh.base.list.loader.OnLoadState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CommonLoader: ILoader {
    private var isLoading = false
    private val hasMore = false
    override var page = 1 //页(只能有一页）
    override var rows = 50 //一页包含的条数
    private var onLoadState: OnLoadState? = null
    lateinit var onCommonBinder: OnCommonBinder<*>
    private var disposable: Disposable? = null
    @Synchronized
    override fun loadMore() {
        if (isLoading || !hasMore) {
            return
        }
        startLoad()
    }

    override fun setLoadStateListener(onLoadState: OnLoadState) {
        this.onLoadState=onLoadState
    }

    override fun cancelNowRequest() {
        disposable?.dispose()
    }

    override fun refresh() {
        page = 1
        startLoad()
    }

    override fun startLoad() {
        loadData(onCommonBinder)
    }

    fun <T> loadData(commonBinder: OnCommonBinder<T>) {
        isLoading = true
        onLoadState?.onLoadState(OnLoadState.State.START, "start")
        disposable = commonBinder.bindData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data: T? ->
                    Log.i("cmh", "ListLoader_loadData():onNext")
                    isLoading = false
                        Handler(Looper.getMainLooper()).postDelayed({ onLoadState?.onLoadState(OnLoadState.State.END, "end") }, 200)
                    if (data != null) {
                        if (page == 1) {
                            onLoadState?.onLoadState(OnLoadState.State.CLEAR, "clear")
                        }
                        commonBinder.bindItem(data, page, 0)
                    } else {
                        onLoadState?.onLoadState(OnLoadState.State.ERROR, "empty")
                    }
                }, { throwable: Throwable? ->
                    isLoading = false
                    Log.i("cmh", "ListLoader_loadData():error=" + if (throwable != null) throwable.message else "error")
                    onLoadState?.onLoadState(OnLoadState.State.ERROR, throwable?.message ?: "error")
                }) {
                    if (disposable != null && !disposable!!.isDisposed) {
                        disposable!!.dispose()
                        disposable = null
                    }
                }
    }

    fun setCommonBinder(onCommonBinder: OnCommonBinder<*>) {
        this.onCommonBinder = onCommonBinder
    }
}