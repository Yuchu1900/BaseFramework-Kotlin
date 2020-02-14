package com.cmh.baseframedemo.fragment.extras;

import android.os.Bundle;
import android.view.View;

import com.cmh.base.view.BaseFragment;
import com.cmh.baseframedemo.R;
import com.cmh.baseframedemo.databinding.FragmentExtrasBinding;

/**
 * Fragment中获取从activity传递过来的参数
 */
public class ExtrasFragment extends BaseFragment{
    FragmentExtrasBinding binding;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_extras;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=(FragmentExtrasBinding)baseBinding;
        Bundle bundle=getArguments();
        if(bundle.containsKey("intValue")) {
            binding.btnExtras.setText(bundle.getInt("intValue")+"");
        }else{
            binding.btnExtras.setText("null");
        }
    }
}
