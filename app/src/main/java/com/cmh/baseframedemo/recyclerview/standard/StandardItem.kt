package com.cmh.baseframedemo.recyclerview.standard

import com.cmh.base.list.item.BaseItem
import com.cmh.baseframedemo.BR
import com.cmh.baseframedemo.R
import com.cmh.baseframedemo.databinding.ItemStandardBinding

class StandardItem(var data: StandardData) : BaseItem<ItemStandardBinding>() {
    override val layoutRes: Int
        get() = R.layout.item_standard

    override fun bindView(holder: ViewHolder, payloads: MutableList<Any>) {
        super.bindView(holder, payloads)
        val binding = holder.binding!!
        binding.setVariable(BR.id, "${data.id}")
        binding.setVariable(BR.name, data.name)
        binding.executePendingBindings()
    }

}