package com.cmh.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.cmh.base.utils.RxBus.Companion.default
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.RxFragment
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks

abstract class BaseFragment : RxFragment(), PermissionCallbacks {
    val PERMISSION_REQUEST_CODE = 9627
    var rootView: View? = null
    @JvmField
    var baseBinding: ViewDataBinding? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            baseBinding = DataBindingUtil.inflate(inflater!!, layoutId, container, false)
            rootView = baseBinding!!.getRoot()
        }
        if (rootView != null && rootView!!.parent != null) {
            (rootView!!.parent as ViewGroup).removeView(rootView)
        }
        return rootView
    }

    /**
     * 接收其它地方发来的RxBus消息，请调用该方法
     * 默认Rx流开始是在主线程
     * @param clsType 消息类
     * @param <T> 消息对象
     * @return
    </T> */
    fun <T> getRxEvent(clsType: Class<T>?): Flowable<T> {
        return default!!.toFlowable(clsType)
                .compose(bindUntilEvent(FragmentEvent.PAUSE))
                .observeOn(AndroidSchedulers.mainThread())
    }

    abstract val layoutId: Int

    /**
     * 如果要在Fragment处理系统回退键点击事件
     * 继承BaseFragment同时要实现这个接口，
     * 并在接口方法中返回true,如果返回false,
     * 则不处理系统回退键点击事件
     */
    interface OnBackPressedListener {
        fun onFragmentBackPressed(): Boolean
    }

    /**
     * 处理用户权限
     */
    fun requestPermission(whyDescription: String?, vararg perms: String?) {
        if (!EasyPermissions.hasPermissions(this.activity, *perms)) {
            EasyPermissions.requestPermissions(this, whyDescription?: "", PERMISSION_REQUEST_CODE, *perms)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //用户权限的回调事件全部交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) { //TODO 子类覆写该方法处理权限已授权的消息
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) { //TODO 子类覆写该方法处理权限已拒绝的消息
    }
}