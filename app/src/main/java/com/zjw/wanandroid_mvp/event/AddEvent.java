package com.zjw.wanandroid_mvp.event;

import com.zjw.wanandroid_mvp.bean.TodoBean;

public class AddEvent extends BaseEvent {

    private int code;
//    private int position;
//    private TodoBean bean;
//
//    public TodoBean getBean() {
//        return bean;
//    }
//
//    public void setBean(TodoBean bean) {
//        this.bean = bean;
//    }
//
//    public AddEvent(int code, int position, TodoBean bean) {
//        this.code = code;
//        this.position = position;
//        this.bean = bean;
//    }
//
//    public int getPosition() {
//        return position;
//    }
//
//    public void setPosition(int position) {
//        this.position = position;
//    }
//
//    public AddEvent(int code, int position) {
//        this.code = code;
//        this.position = position;
//    }

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
