package com.cmh.base.list.loader.list

import io.reactivex.Flowable

interface OnListBinder<T> {
    fun bindData():Flowable<List<T>> //从哪里获取数据
    fun bindItem(datas: List<T>, page: Int, rows: Int) //怎么将每条数据绑定到Item
}