package com.zjw.wanandroid_mvp.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.http.GlobalHttpHandler;
import com.zjw.wanandroid_mvp.utils.CacheUtil;
import com.zjw.wanandroid_mvp.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHandler implements GlobalHttpHandler {

    @NonNull
    @NotNull
    @Override
    public Response onHttpResultResponse(@Nullable String httpResult, @NonNull @NotNull Interceptor.Chain chain, @NonNull @NotNull Response response) {
        //如果已经登录，需要保存cookie
        if (chain.request().url().encodedPath().contains("user/login")) {
            if (!response.headers("set-cookie").isEmpty()) {
                //保存Cookie做持久化操作 set-cookie可能为多个
                List<String> cookies = response.headers("set-cookie");
                CacheUtil.setCookie(HttpUtils.INSTANCE.encodeCookie(cookies));
            }
        }

        return response;
    }

    @NonNull
    @NotNull
    @Override
    public Request onHttpRequestBefore(@NonNull @NotNull Interceptor.Chain chain, @NonNull @NotNull Request request) {
        if (CacheUtil.isLogin()) {
//            chain.request().url().newBuilder().addEncodedPathSegment()
            String cookies = CacheUtil.getCookie();
            //如果已经登录过了，那么请求的时候可以带上cookie 参数
            return chain.request().newBuilder()
                    .addHeader("Cookie", cookies)
                    /* .post(FormBody.Builder().apply {
                         addEncoded("token","xxx")
                     }.build())*/
                    .build();
        }

        return request;
    }
}
