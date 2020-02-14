package com.cmh.base.list.loader

interface ILoader {
    fun startLoad() //开始加载
    fun refresh() //刷新
    fun loadMore() //加载更多
    fun setLoadStateListener(onLoadState: OnLoadState) //监听加载状态
    fun cancelNowRequest() //停止当前正在加载的http请求

    var page: Int //列表当前页号
    var rows: Int //列表每页面的条数
}