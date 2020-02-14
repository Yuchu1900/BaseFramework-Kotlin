package com.cmh.base.view

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.cmh.base.utils.RxBus.Companion.default
import com.cmh.base.utils.StatusBarUtils.setStatusBarMode
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks

/**
 * 基类
 */
abstract class BaseActivity : RxAppCompatActivity(), PermissionCallbacks {
    val PERMISSION_REQUEST_CODE = 9527
    @JvmField
    var baseBinding: ViewDataBinding? = null
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        baseBinding = DataBindingUtil.setContentView(this, layoutId)
        afterOnCreate(bundle)
    }

    abstract val layoutId: Int
    /**
     * 设置状态栏的配色方案:
     * isDarkStatusBar: true是选择黑底白字状态栏； false是白底黑字状态栏
     * isImmersiveStatusBar：true是选择沉浸式状态栏，false是选择非沉浸式状态栏
     * （高版本android系统还可设置是否沉浸式及其透明度）
     */
    open fun setStatusBarColor(isDarkStatusBar:Boolean = false, isImmersiveStatusBar: Boolean = false ) {
        if (isStatusBarSetEnable) setStatusBarMode(this, isImmersiveStatusBar, isDarkStatusBar)
    }

    /**
     * 是否可设置状态栏
     */
    var isStatusBarSetEnable: Boolean = true

    open fun afterOnCreate(bundle: Bundle?) {}
    /**
     * 接收其它地方发来的RxBus消息，请调用该方法
     * 默认Rx流开始是在主线程
     * @param clsType 消息类
     * @param <T> 消息对象
     * @return
    </T> */
    fun <T> getRxEvent(clsType: Class<T>?): Flowable<T> {
        return default!!.toFlowable(clsType)
                .compose(bindUntilEvent(ActivityEvent.PAUSE))
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyBoard()
    }

    /**
     * 关闭输入法软件盘
     */
    private fun hideSoftKeyBoard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    /**
     * 处理用户权限
     */
    fun requestPermission(whyDescription: String, vararg perms: String) {
        if (!EasyPermissions.hasPermissions(this, *perms)) {
            EasyPermissions.requestPermissions(this, whyDescription, PERMISSION_REQUEST_CODE, *perms)
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