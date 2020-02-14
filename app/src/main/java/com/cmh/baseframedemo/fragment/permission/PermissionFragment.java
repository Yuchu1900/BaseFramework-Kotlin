package com.cmh.baseframedemo.fragment.permission;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cmh.base.view.BaseFragment;
import com.cmh.baseframedemo.R;

import java.util.List;

public class PermissionFragment extends BaseFragment{
    @Override
    public int getLayoutId() {
        return R.layout.fragment_permission;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestPermission("蓝牙需要定位权限", Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        super.onPermissionsGranted(requestCode, perms);
        Toast.makeText(this.getActivity(),"已授权",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        super.onPermissionsDenied(requestCode, perms);
        Toast.makeText(this.getActivity(),"已拒绝",Toast.LENGTH_LONG).show();
    }
}
