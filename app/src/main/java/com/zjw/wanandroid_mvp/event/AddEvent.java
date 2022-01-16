package com.zjw.wanandroid_mvp.event;

public class AddEvent extends BaseEvent {

    private int code;

    public AddEvent(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
