package com.zjw.wanandroid_mvp.event;

public class SettingEvent extends BaseEvent {

    boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public SettingEvent(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
