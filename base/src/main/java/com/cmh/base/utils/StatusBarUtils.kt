package com.cmh.base.utils

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.flyco.systembar.SystemBarHelper

/**
 * Created by cmh on 2018/1/19.
 * 状态栏的颜色配置（含沉浸式状态栏）
 *
 * 1. 下面的参数isDark都是针对状态栏上的文字颜色而言的：
 * isDark为true时，表示文字为深色，相应为了达到可视清楚效果，状态栏背景色一般配置为浅色的
 * isDark为false时，表示文字为浅色，相应为了达到可视清楚效果，状态栏背景色一般配置为深色的
 * 2. 状态栏文字的颜色只能设置为深色或者浅色，不能设置具体值，而背景色却可以设置
 *
 */
object StatusBarUtils {
    /**
     * 普通状态栏背景颜色配置（注意：是背景颜色）
     */
    var BG_LIGHT = Color.parseColor("#FFFFFFFF")
    var BG_DARK = Color.parseColor("#7FFF0000")
    /**
     * 沉浸式状态栏背景颜色配置（注意：是背景颜色）
     */
    var BG_IMMERSIVE_DARK = Color.parseColor("#7FFF0000") //注意：如果制定了颜色，BG_IMMERSIVE_ALPHA值即使设置了也是无效的
    var BG_IMMERSIVE_LIGHT = Color.parseColor("#7FFFFFFF") //注意：如果制定了颜色，BG_IMMERSIVE_ALPHA值即使设置了也是无效的
    var BG_IMMERSIVE_ALPHA = 0f //只在沉浸式状态栏使用默认背景颜色时，设置该值才有效，有效范围：0f-1.0f
    /**
     * 状态栏色彩配置
     * @param activity 状态栏所属的activity
     * @param isImmersive 是否使用沉浸式状态栏
     * @param isDrak 是否使用黑字白底色彩配置（反之白字黑底）
     */
    @JvmStatic
    fun setStatusBarMode(activity: Activity, isImmersive: Boolean, isDrak: Boolean) {
        if (isImmersive) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //沉浸式状态栏（只在4.4及其以上系统有效）
                Log.i("cmh", "setStatusBarMode()")
                //为沉浸式状态栏的文字设置颜色
                setStatusBarTextColor(activity, isDrak)
                //沉浸式状态栏设置背景色(不设置会使用默认背景色）
                setStatusBarBgColor(activity, if (isDrak) BG_IMMERSIVE_LIGHT else BG_IMMERSIVE_DARK)
                //设置沉浸式状态栏（这里设置的透明度只在使用默认背景色时才能生效）
                setImmersiveStatusBar(activity, BG_IMMERSIVE_ALPHA)
            } else {
                setStatusBarTextColor(activity, isDrak)
                setStatusBarBgColor(activity, if (isDrak) BG_LIGHT else BG_DARK)
            }
        } else {
            setStatusBarTextColor(activity, isDrak)
            setStatusBarBgColor(activity, if (isDrak) BG_LIGHT else BG_DARK)
        }
    }

    /**
     * 状态栏设置为沉浸式
     * @param activity
     * @param alpha 背景的透明度(只在不设置背景色时可以生效）
     */
    fun setImmersiveStatusBar(activity: Activity?, alpha: Float) {
        SystemBarHelper.immersiveStatusBar(activity, alpha)
    }

    /**
     * 设置状态栏上文字的颜色
     * @param activity
     * @param isDark 是否为深色文字
     */
    fun setStatusBarTextColor(activity: Activity, isDark: Boolean) {
        if (SystemBarHelper.isFlyme4Later()) {
            SystemBarHelper.setStatusBarDarkModeForFlyme4(activity.window, isDark)
        } else if (SystemBarHelper.isMIUI6Later()) {
            setStatusBarDarkModeForM(activity.window, isDark)
            SystemBarHelper.setStatusBarDarkModeForMIUI6(activity.window, isDark)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setStatusBarDarkModeForM(activity.window, isDark)
        }
    }

    /**
     * 设置状态栏上背景的颜色
     * @param activity
     * @param color 颜色值
     * 这里的isDark统一以文字作为标准，
     * isDark为true时, 文字为深色，相应要配置浅色的背景；
     * isDark为false时, 文字为浅色时，相应要配置深色的背景.
     */
    fun setStatusBarBgColor(activity: Activity?, color: Int) {
        SystemBarHelper.tintStatusBar(activity, color, 0f)
    }

    /**
     * android 6.0设置字体颜色 可控制深色或浅色
     */
    @TargetApi(Build.VERSION_CODES.M)
    private fun setStatusBarDarkModeForM(window: Window, dark: Boolean) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        var systemUiVisibility = window.decorView.systemUiVisibility
        systemUiVisibility = if (dark) {
            systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        window.decorView.systemUiVisibility = systemUiVisibility
    }
}