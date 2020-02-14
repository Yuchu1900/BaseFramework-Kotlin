package com.cmh.baseframedemo.activity

import android.os.Bundle
import android.view.View
import com.cmh.base.view.BaseActivity
import com.cmh.baseframedemo.R

class StatusBarImmersiveActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_status_bar_immersive

    override fun afterOnCreate(bundle: Bundle?) {
        super.afterOnCreate(bundle)
        setStatusBarColor(true, true)
        setClick()
    }

    private fun setClick() {
        findViewById<View>(R.id.btnDark).setOnClickListener {
            setStatusBarColor(true, true)
        }
        findViewById<View>(R.id.btnLight).setOnClickListener {
            setStatusBarColor(false, true)
        }
    }

}