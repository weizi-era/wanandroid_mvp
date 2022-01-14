package com.zjw.wanandroid_mvp.event;

public class AddEvent extends BaseEvent {

    private int code;

    private static final int TODO_CODE = 1;
    private static final int SHARE_CODE = 2;
    private static final int DELETE_CODE = 3;

    public AddEvent(int code) {
        this.code = code;
    }

    public AddEvent() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static int getTodoCode() {
        return TODO_CODE;
    }

    public static int getShareCode() {
        return SHARE_CODE;
    }

    public static int getDeleteCode() {
        return DELETE_CODE;
    }

}
