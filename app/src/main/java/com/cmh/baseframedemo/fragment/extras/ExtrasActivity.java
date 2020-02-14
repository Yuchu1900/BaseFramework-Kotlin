package com.cmh.baseframedemo.fragment.extras;

import android.app.Fragment;
import android.os.Bundle;

import com.cmh.base.view.BaseFragmentActivity;

public class ExtrasActivity extends BaseFragmentActivity{
    @Override
    public Fragment buildFragment() {
        return new ExtrasFragment();
    }

    @Override
    public Bundle addExtras(Bundle bundle) {
        bundle.putInt("intValue",9527);
        return bundle;
    }
}
