package com.cmh.base.list.item

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.items.AbstractItem

abstract class BaseItem<V : ViewDataBinding> : AbstractItem<BaseItem<V>.ViewHolder>() {
    override var type = layoutRes

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    inner class ViewHolder internal constructor(view: View?) : RecyclerView.ViewHolder(view!!) {
        var binding: V? = DataBindingUtil.bind(view!!)

    }
}