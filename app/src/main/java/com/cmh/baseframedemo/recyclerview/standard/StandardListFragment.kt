package com.cmh.baseframedemo.recyclerview.standard

import android.os.Bundle
import android.view.View
import com.cmh.base.view.BaseFragment
import com.cmh.base.list.loader.list.ListLoader
import com.cmh.base.list.loader.list.OnListBinder
import com.cmh.baseframedemo.R
import com.cmh.baseframedemo.databinding.FragmentStandardListBinding
import io.reactivex.Flowable
import java.util.*

class StandardListFragment : BaseFragment(), OnListBinder<StandardData> {

    var binding: FragmentStandardListBinding? = null

    override val layoutId: Int
        get() = R.layout.fragment_standard_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = baseBinding as FragmentStandardListBinding?
        val loader = ListLoader()
        loader.listBinder = this
        binding!!.llvMain.loader = loader
        binding!!.llvMain.loadData()
    }

    override fun bindData(): Flowable<List<StandardData>> {
        val dataList: MutableList<StandardData> = ArrayList()
        for (i in 0..18) {
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