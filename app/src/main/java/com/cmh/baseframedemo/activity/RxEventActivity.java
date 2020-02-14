package com.cmh.baseframedemo.activity;

import android.os.Bundle;

import com.cmh.base.view.BaseActivity;
import com.cmh.base.utils.RxBus;
import com.cmh.baseframedemo.R;
import com.cmh.baseframedemo.databinding.ActivityRxEventBinding;

import io.reactivex.disposables.Disposable;

public class RxEventActivity extends BaseActivity{
    ActivityRxEventBinding binding;
    @Override
    public int getLayoutId() {
        return R.layout.activity_rx_event;
    }

    @Override
    public void afterOnCreate(Bundle bundle){
        super.afterOnCreate(bundle);
        binding=(ActivityRxEventBinding)baseBinding;
        binding.btnSendMsg.setOnClickListener(v -> sendRxMessage());
        receiveRxMessage();
    }

    //接收消息(这个一般放在接收消息的Activity或者Fragment里）
    protected void receiveRxMessage(){
        //因为发送的是String类型的消息，这里要用String.class,
        //如果是自定义的消息类，就要用消息类的类型(RxBus是通过这个来区分不同的消息
        Disposable disposable = getRxEvent(String.class)
                .subscribe(s -> binding.tvMessageContent.setText(s));
    }

    //发送消息(这个可以放在任意需要发送消息的地方）
    //这里为了方便演示，放在同一个Activity里
    protected void sendRxMessage(){
        RxBus.getDefault().post("我是通过RxBus发送的一段消息");//可以自定义一个消息类，这里就要传入消息类，接收处要指明类型
    }
}
