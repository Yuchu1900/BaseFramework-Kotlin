package com.cmh.baseframedemo.recyclerview.common

import android.os.Bundle
import android.view.View
import com.cmh.base.view.BaseFragment
import com.cmh.base.list.loader.common.OnCommonBinder
import com.cmh.base.list.loader.common.CommonLoader
import com.cmh.baseframedemo.R
import com.cmh.baseframedemo.databinding.FragmentCommonListBinding
import com.cmh.baseframedemo.recyclerview.standard.StandardData
import com.cmh.baseframedemo.recyclerview.standard.StandardItem
import io.reactivex.Flowable
import java.util.ArrayList

class OnCommonListFragment: BaseFragment(), OnCommonBinder<List<StandardData>> {

    var binding: FragmentCommonListBinding? = null

    override val layoutId: Int
        get() = R.layout.fragment_common_list

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = baseBinding as FragmentCommonListBinding?
        val loader = CommonLoader()
        loader.onCommonBinder = this
        binding!!.llvMain.loader = loader
        binding!!.llvMain.loadData()
    }

    override fun bindData(): Flowable<List<StandardData>> {
        val dataList: MutableList<StandardData> = ArrayList()
        for (i in 0..50) {
            val data = StandardData()
            data.id = i
            data.name = "Jim$i"
            dataList.add(data)
        }
        return Flowable.just(dataList)
    }

    override fun bindItem(datas: List<StandardData>, page: Int, rows: Int) {
        if (datas.isNotEmpty()) {
            for (data in datas) {
                binding!!.llvMain.addItem(StandardItem(data))
            }
        }
    }

}