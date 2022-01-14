package com.zjw.wanandroid_mvp.event;

public class CollectEvent extends BaseEvent {

    private boolean collect;
    private int id;
    private String tag;

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public CollectEvent(boolean collect, int id, String tag) {
        this.collect = collect;
        this.id = id;
        this.tag = tag;
    }

    public CollectEvent(boolean collect, int id) {
        this.collect = collect;
        this.id = id;
    }
}
