package com.cmh.baseframedemo.fragment.rxevent;

import android.app.Fragment;
import android.os.Bundle;

import com.cmh.base.view.BaseFragmentActivity;

public class RxEventActivity extends BaseFragmentActivity{
    @Override
    public Fragment buildFragment() {
        return new RxEventFragment();
    }

    @Override
    public Bundle addExtras(Bundle bundle) {
        return bundle;
    }
}
