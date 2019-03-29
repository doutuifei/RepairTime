package com.muzi.repairtime.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者: lipeng
 * 时间: 2019/3/29
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("os", "android")
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    }

}
