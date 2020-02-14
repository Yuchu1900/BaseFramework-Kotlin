package com.cmh.base.list.loader

interface OnLoadState {
    enum class State {
        //加载过程状态：开始加载、加载完成、加载失败、需要清空
        START,
        END, ERROR, CLEAR
    }

    fun onLoadState(state: State, message: String) //加载过程状态回调
}