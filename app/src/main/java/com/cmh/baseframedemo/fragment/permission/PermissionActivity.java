package com.cmh.baseframedemo.fragment.permission;

import android.app.Fragment;
import android.os.Bundle;

import com.cmh.base.view.BaseFragmentActivity;

public class PermissionActivity extends BaseFragmentActivity{
    @Override
    public Fragment buildFragment() {
        return new PermissionFragment();
    }

    @Override
    public Bundle addExtras(Bundle bundle) {
        return bundle;
    }
}
