package com.muzi.repairtime.http.interceptor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.muzi.repairtime.utils.NetworkChecker;


/**
 * 作者: lipeng
 * 时间: 2019/3/21
 * 邮箱: lipeng@moyi365.com
 * 功能: 网络状态改变
 */
public class NetworkReceiver extends BroadcastReceiver {

    private NetworkChecker mChecker;
    private boolean mAvailable;

    public NetworkReceiver(NetworkChecker checker) {
        this.mChecker = checker;
        this.mAvailable = mChecker.isAvailable();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mAvailable = mChecker.isAvailable();
    }

    public boolean isAvailable() {
        return mAvailable;
    }

}
