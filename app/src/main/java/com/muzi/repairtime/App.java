package com.muzi.repairtime;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.muzi.repairtime.data.DataProxy;
import com.muzi.repairtime.data.MmkvModel;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.manager.AppManager;

/**
 * 作者: lipeng
 * 时间: 2019/3/4
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RxUtils.initErrorHandler();
        initRegisterActivityLifecycle();
        DataProxy.getInstance().init(new MmkvModel(this));
    }

    public static App getInstance() {
        return instance;
    }

    private void initRegisterActivityLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                AppManager.getAppManager().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                AppManager.getAppManager().removeActivity(activity);
            }
        });
    }

}
