package com.zjw.wanandroid_mvp.widget.callback;


import com.kingja.loadsir.callback.Callback;
import com.zjw.wanandroid_mvp.R;

/**
 * 网络错误提示页
 */
public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.network_error;
    }
}
