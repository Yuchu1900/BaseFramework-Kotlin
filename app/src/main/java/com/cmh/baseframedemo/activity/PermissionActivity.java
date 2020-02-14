package com.cmh.baseframedemo.activity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cmh.base.view.BaseActivity;
import com.cmh.baseframedemo.R;

import java.util.List;

public class PermissionActivity extends BaseActivity{

        @Override
    public int getLayoutId() {
        return R.layout.activity_permission;
    }

    @Override
    public void afterOnCreate(Bundle bundle) {
        super.afterOnCreate(bundle);
        //请求用户授权(可以一次请求多个，多个权限中间用逗号分隔)
        requestPermission("蓝牙需要定位权限",Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    //当用户拒绝权限时，会回调这个方法，perms保存了权限列表
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        super.onPermissionsDenied(requestCode, perms);
        Log.i("cmh","onPermissionsDenied()");
        Toast.makeText(this,"已拒绝授权",Toast.LENGTH_LONG).show();
    }
    //当用户接受权限时，会回调这个方法，perms保存了权限列表
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        super.onPermissionsGranted(requestCode, perms);
        Log.i("cmh","onPermissionsGranted()");
        Toast.makeText(this,"已通过授权",Toast.LENGTH_LONG).show();
    }
}
