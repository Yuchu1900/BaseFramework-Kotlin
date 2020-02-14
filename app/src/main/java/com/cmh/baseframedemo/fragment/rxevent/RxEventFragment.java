package com.cmh.baseframedemo.fragment.rxevent;

import android.os.Bundle;
import android.view.View;

import com.cmh.base.view.BaseFragment;
import com.cmh.base.utils.RxBus;
import com.cmh.baseframedemo.R;
import com.cmh.baseframedemo.databinding.FragmentRxEventBinding;

import io.reactivex.disposables.Disposable;

public class RxEventFragment extends BaseFragment {
    FragmentRxEventBinding binding;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_rx_event;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=(FragmentRxEventBinding)baseBinding;
        binding.btnSendMsg.setOnClickListener(v -> RxBus.getDefault().post("这是通过RxBus发送的消息"));
        getEvent();
    }

    public void getEvent() {
        Disposable subscribe = getRxEvent(String.class)
                .subscribe(s -> binding.tvMessageContent.setText(s));
    }
}
