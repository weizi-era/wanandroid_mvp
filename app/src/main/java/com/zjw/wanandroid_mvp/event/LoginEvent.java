package com.zjw.wanandroid_mvp.event;

import java.util.List;

public class LoginEvent extends BaseEvent {

    private boolean isLogin;
    private List<Integer> collectIds;

    public List<Integer> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public LoginEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public LoginEvent(boolean isLogin, List<Integer> collectIds) {
        this.isLogin = isLogin;
        this.collectIds = collectIds;
    }
}
