package com.muzi.repairtime.http.interceptor;

import android.content.Context;

import com.muzi.repairtime.App;
import com.muzi.repairtime.exception.NoNetworkException;
import com.muzi.repairtime.http.ErrorHandle;
import com.muzi.repairtime.utils.NetWorkUtil;

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

    @Override
    public Response intercept(Chain chain) throws IOException {
        try {
            Context context = App.getInstance().getApplicationContext();
            boolean networkConnected = NetWorkUtil.isNetworkConnected(context);
            if (networkConnected) {
                return chain.proceed(chain.request());
            } else {
                throw new NoNetworkException(ErrorHandle.ERROR_NO_NET);
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
