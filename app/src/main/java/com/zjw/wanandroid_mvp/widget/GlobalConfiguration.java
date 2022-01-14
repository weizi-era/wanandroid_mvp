package com.zjw.wanandroid_mvp.widget;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.google.gson.GsonBuilder;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.AppModule;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.http.imageloader.glide.GlideImageLoaderStrategy;
import com.jess.arms.integration.ConfigModule;
import com.zjw.wanandroid_mvp.model.constant.Constant;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.rx_cache2.internal.RxCache;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.OkHttpClient;

public class GlobalConfiguration implements ConfigModule {

    @Override
    public void applyOptions(@NonNull @NotNull Context context, @NonNull @NotNull GlobalConfigModule.Builder builder) {
        builder.baseurl(Constant.BASE_URL)
                .imageLoaderStrategy(new GlideImageLoaderStrategy())
                .globalHttpHandler(new HttpHandler())
                .gsonConfiguration(new AppModule.GsonConfiguration() {
                    @Override
                    public void configGson(@NonNull @NotNull Context context, @NonNull @NotNull GsonBuilder builder) {
                        builder.enableComplexMapKeySerialization();
                        builder.serializeNulls();
                    }
                })
                .okhttpConfiguration(new ClientModule.OkhttpConfiguration() {
                    @Override
                    public void configOkhttp(@NonNull @NotNull Context context, @NonNull @NotNull OkHttpClient.Builder builder) {
                        builder.writeTimeout(5, TimeUnit.SECONDS);//全局的读取超时时间
                        builder.connectTimeout(10,TimeUnit.SECONDS);//全局的连接超时时间
                        builder.readTimeout(5,TimeUnit.SECONDS);//全局的读取超时时间
                        //使用一行代码监听 Retrofit／Okhttp 上传下载进度监听,以及 Glide 加载进度监听, 详细使用方法请查看 https://github.com/JessYanCoding/ProgressManager
                        ProgressManager.getInstance().with(builder);
                        //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl, 详细使用方法请查看 https://github.com/JessYanCoding/RetrofitUrlManager
                        RetrofitUrlManager.getInstance().with(builder);
                    }
                })
                .rxCacheConfiguration(new ClientModule.RxCacheConfiguration() {
                    @Override
                    public RxCache configRxCache(@NonNull @NotNull Context context, @NonNull @NotNull RxCache.Builder builder) {
                        builder.useExpiredDataIfLoaderNotAvailable(true);
                        return null;
                    }
                });
    }

    @Override
    public void injectAppLifecycle(@NonNull @NotNull Context context, @NonNull @NotNull List<AppLifecycles> lifecycles) {
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(@NonNull @NotNull Context context, @NonNull @NotNull List<Application.ActivityLifecycleCallbacks> lifecycles) {

    }

    @Override
    public void injectFragmentLifecycle(@NonNull @NotNull Context context, @NonNull @NotNull List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {

    }
}
