package com.cmh.base.utils

import android.content.Context
import android.os.Environment

/**
 * Storage外部存储器(SD卡)工具类
 */
object StorageSUtils {
    /**
     * 是否存在外部存储器(SD卡)
     */
    fun isExternalStorageExit(): Boolean = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    /**
     * 获取外部存储器ExternalStorage路径
     * 外部存储器文件路径类似这样：/storage/emulated/0/app包名/files/
     * 该方法不需要用户权限，路径下的内容会随着app的删除而删除
     * 外部存储器容量较大，适合存放图片、视频、音乐等文件
     */
    fun getExternalFileDir(context: Context): String?{
        if(isExternalStorageExit()) {
            return context.getExternalFilesDir(null)?.absolutePath
        }else{
            return null
        }
    }

    /**
     * 获取内部存储器InternalStorage路径
     * 路径类似这样：/data/user/0/app包名/files/
     * 内部存储器容量较小，适合存储占有空间小，访问频率较高的内容
     * SharedPreferences和数据库都是存放在内部存储器中
     * SharedPreferences存放路径：/data/data/com.cmh.externalstoragedemo/shared_prefs/
     * 数据库存放路径：/data/data/com.cmh.externalstoragedemo/database/
     */
    fun getInternalFileDir(context: Context): String? {
        return context.filesDir.absolutePath
    }

}