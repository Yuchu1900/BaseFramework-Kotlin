package com.cmh.baseframedemo.fragment.back;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cmh.base.view.BaseFragment;
import com.cmh.baseframedemo.R;

/**
 *在Fragment中获取并处理点击系统回退键的事件
 *
 */
public class BackFragment extends BaseFragment implements BaseFragment.OnBackPressedListener{
    boolean needDealBackClick=true;//是否需要处理回退键事件，不处理将返回给activity处理
    @Override
    public int getLayoutId() {
        return R.layout.fragment_back;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            this.getActivity().finish();//直接调用容器Activity结束页面
        });
    }

    @Override
    public boolean onFragmentBackPressed() {
        if(needDealBackClick){
            Toast.makeText(this.getActivity(),"Fragment自己处理回退键",Toast.LENGTH_LONG).show();
            return true;
        }else {
            return false;
        }
    }
}
