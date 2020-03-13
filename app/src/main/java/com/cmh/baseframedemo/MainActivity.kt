package com.cmh.baseframedemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cmh.baseframedemo.activity.PermissionActivity
import com.cmh.baseframedemo.activity.RxEventActivity
import com.cmh.baseframedemo.activity.StatusBarColorActivity
import com.cmh.baseframedemo.activity.StatusBarImmersiveActivity
import com.cmh.baseframedemo.fragment.back.BackActivity
import com.cmh.baseframedemo.fragment.extras.ExtrasActivity
import com.cmh.baseframedemo.recyclerview.common.CommonListActivity
import com.cmh.baseframedemo.recyclerview.standard.StandardListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setClick()
    }

    private fun setClick() {
        findViewById<View>(R.id.btnDarkStatusBar).setOnClickListener {
            val intent = Intent(this@MainActivity, StatusBarColorActivity::class.java)
            startActivity(intent)
        }
        
        findViewById<View>(R.id.btnImmersiveStatusBar).setOnClickListener {
            val intent = Intent(this@MainActivity, StatusBarImmersiveActivity::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.btnRxBusEvent).setOnClickListener {
            val intent = Intent(this@MainActivity, RxEventActivity::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.btnPermission).setOnClickListener {
            val intent = Intent(this@MainActivity, PermissionActivity::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.btnBack).setOnClickListener {
            val intent = Intent(this@MainActivity, BackActivity::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.btnExtras).setOnClickListener {
            val intent = Intent(this@MainActivity, ExtrasActivity::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.btnFragmentRxEvent).setOnClickListener {
            val intent = Intent(this@MainActivity, com.cmh.baseframedemo.fragment.rxevent.RxEventActivity::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.btnFragmentPermission).setOnClickListener {
            val intent = Intent(this@MainActivity, com.cmh.baseframedemo.fragment.permission.PermissionActivity::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.btnFragmentStandardList).setOnClickListener {
//            val intent = Intent(this@MainActivity, StandardListActivity::class.java)
//            startActivity(intent)
            val intent = Intent(this@MainActivity, CommonListActivity::class.java)
            startActivity(intent)
        }
    }
}