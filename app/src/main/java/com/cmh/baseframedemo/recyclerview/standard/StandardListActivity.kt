package com.cmh.baseframedemo.recyclerview.standard

import android.app.Fragment
import android.os.Bundle
import com.cmh.base.view.BaseFragmentActivity

class StandardListActivity : BaseFragmentActivity() {
    override fun buildFragment(): Fragment {
        return StandardListFragment()
    }

    override fun addExtras(bundle: Bundle): Bundle {
        return bundle
    }
}