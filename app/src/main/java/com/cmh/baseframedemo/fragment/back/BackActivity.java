package com.cmh.baseframedemo.fragment.back;

import android.app.Fragment;
import android.os.Bundle;

import com.cmh.base.view.BaseFragmentActivity;

public class BackActivity extends BaseFragmentActivity{

    @Override
    public Fragment buildFragment() {
        return new BackFragment();
    }

    @Override
    public Bundle addExtras(Bundle bundle) {
        return bundle;
    }
}
