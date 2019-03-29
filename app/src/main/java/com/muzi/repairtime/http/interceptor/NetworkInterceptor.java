package com.muzi.repairtime.http.interceptor;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import com.muzi.repairtime.App;
import com.muzi.repairtime.exception.NoNetworkException;
import com.muzi.repairtime.http.ErrorHandle;
import com.muzi.repairtime.utils.NetworkChecker;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 作者: lipeng
 * 时间: 2019/3/4
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class NetworkInterceptor implements Interceptor {

    private Context mContext;
    private final NetworkReceiver mReceiver;

    public NetworkInterceptor() {
        mContext = App.getInstance().getApplicationContext();
        mReceiver = new NetworkReceiver(new NetworkChecker(mContext));

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction("android.net.ethernet.STATE_CHANGE");
        filter.addAction("android.net.ethernet.ETHERNET_STATE_CHANGED");
        mContext.registerReceiver(mReceiver, filter);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        try {
            if (mReceiver.isAvailable()) {
                return chain.proceed(chain.request());
            } else {
                throw new NoNetworkException(ErrorHandle.ERROR_NO_NET);
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
