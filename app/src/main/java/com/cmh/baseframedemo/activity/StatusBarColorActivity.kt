package com.cmh.baseframedemo.activity

import android.os.Bundle
import android.view.View
import com.cmh.base.view.BaseActivity
import com.cmh.baseframedemo.R

/**
 * Created by cmh on 2018/1/19.
 * 非沉浸式状态栏
 */
class StatusBarColorActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_status_bar_color


    override fun afterOnCreate(bundle: Bundle?) {
        super.afterOnCreate(bundle)
        setClick()
    }

    private fun setClick() {
        findViewById<View>(R.id.btnDark).setOnClickListener {
            //将状态栏设置为黑底白字
            setStatusBarColor(true,false)
        }
        findViewById<View>(R.id.btnLight).setOnClickListener {
            //将状态栏设置为白底黑字
            setStatusBarColor(false,false)
        }
    }

}