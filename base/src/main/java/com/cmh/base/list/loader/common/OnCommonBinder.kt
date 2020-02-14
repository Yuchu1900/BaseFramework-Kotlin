package com.cmh.base.list.loader.common

import io.reactivex.Flowable

interface OnCommonBinder<T> {
    fun bindData(): Flowable<T>
    fun bindItem(datas: T, page: Int, rows: Int)
}