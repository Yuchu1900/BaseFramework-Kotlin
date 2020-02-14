package com.cmh.base.utils

import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.subscribers.SerializedSubscriber

/**
 * 发消息：RxBus.getDefault().post(消息对象)
 * 接收消息：RxBus.getDefault().toFlowable(消息对象类).subscribe(...)
 * 注意：接收消息时，接收的是一个类的对象，也就是
 */
class RxBus private constructor() {
    //相当于Rxjava1.x中的Subject
    private val mBus: FlowableProcessor<Any>

    /**
     * 发送消息
     * @param o
     */
    fun post(o: Any) {
        SerializedSubscriber(mBus).onNext(o)
    }

    /**
     * 接收消息
     * @param aClass
     * @param <T>
     * @return
    </T> */
    fun <T> toFlowable(aClass: Class<T>?): Flowable<T> {
        return mBus.ofType(aClass)
    }

    /**
     * 判断是否有订阅者
     * @return
     */
    fun hasSubscribers(): Boolean {
        return mBus.hasSubscribers()
    }

    companion object {//单例
        @Volatile
        private var sRxBus: RxBus? = null

        @JvmStatic
        @get:Synchronized
        val default: RxBus?
            get() {
                if (sRxBus == null) {
                    synchronized(RxBus::class.java) {
                        if (sRxBus == null) {
                            sRxBus = RxBus()
                        }
                    }
                }
                return sRxBus
            }
    }

    init { //调用toSerialized()方法，保证线程安全
        mBus = PublishProcessor.create<Any>().toSerialized()
    }
}