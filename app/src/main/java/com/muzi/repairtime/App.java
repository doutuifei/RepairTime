package com.muzi.repairtime;

import android.app.Application;

import com.muzi.repairtime.http.RxUtils;

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
    }

    public static App getInstance() {
        return instance;
    }

}
