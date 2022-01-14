package com.zjw.wanandroid_mvp.event;

import org.greenrobot.eventbus.EventBus;

public class BaseEvent {
    public final void post() {
        EventBus.getDefault().post(this);
    }
}
