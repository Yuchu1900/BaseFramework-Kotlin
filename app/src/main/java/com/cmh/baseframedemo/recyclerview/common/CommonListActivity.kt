package com.cmh.baseframedemo.recyclerview.common

import android.app.Fragment
import android.os.Bundle
import com.cmh.base.view.BaseFragmentActivity

class CommonListActivity: BaseFragmentActivity() {
    override fun addExtras(bundle: Bundle?): Bundle {
        return bundle!!
    }

    override fun buildFragment(): Fragment {
        return OnCommonListFragment()
    }

}