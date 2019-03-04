package com.muzi.repairtime.http;

import android.accounts.NetworkErrorException;
import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import com.muzi.repairtime.exception.BaseException;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.SSLException;

import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.exceptions.ProtocolViolationException;
import io.reactivex.exceptions.UndeliverableException;

/**
 * 作者: lipeng
 * 时间: 2018/12/17
 * 邮箱: lipeng@moyi365.com
 * 功能: 错误处理
 */
public class ErrorHandle {

    public static final String ERROR_DEFAULT = "发生未知异常，请稍后重试";
    public static final String ERROR_UNTREATED = "未处理的异常";
    public static final String ERROR_JSON = "数据解析失败，请稍后重试";
    public static final String ERROR_NO_NET = "当前没有网络，请检查过网络后重试";
    public static final String ERROR_CONNECT = "网络连接失败，请检查网络后重试";
    public static final String ERROR_SSL = "证书验证失败，请关闭代理或升级后重试";
    public static final String ERROR_TIMEOUT = "连接超时，请检查网络后重试";


    /**
     * 异常分发
     *
     * @param throwable
     * @return
     */
    public static String dispatchException(Throwable throwable) {
        if (throwable == null) {
            return ERROR_UNTREATED;
        }
        Log.e("ErrorHandle", "Throwable->" + throwable.getClass().getSimpleName());

        /**
         * 自定义异常
         */
        if (throwable instanceof BaseException) {
            return handleCustomException(throwable);
        } else {
            /**
             * 根据异常类型，返回错误信息
             */
            return handleSystemThrowable(throwable);
        }
    }

    /**
     * 处理自定义异常，直接返回错误信息
     *
     * @param throwable
     * @return
     */
    public static String handleCustomException(Throwable throwable) {
        return throwable.getMessage();
    }

    /**
     * 处理系统异常
     *
     * @param throwable
     * @return
     */
    public static String handleSystemThrowable(Throwable throwable) {

        /**
         * 数据转换/Json解析发生异常
         */
        if (throwable instanceof MalformedJsonException
                || throwable instanceof JsonIOException
                || throwable instanceof JsonParseException
                || throwable instanceof JsonSyntaxException
                || throwable instanceof ParseException
                || throwable instanceof ClassCastException) {
            return ERROR_JSON;
        }
        /**
         * https请求配置代理
         */
        else if (throwable instanceof SSLException) {
            return ERROR_SSL;
        }
        /**
         * 连接超时
         */
        else if (throwable instanceof InterruptedIOException
                || throwable instanceof TimeoutException
                || throwable instanceof SocketTimeoutException) {
            return ERROR_TIMEOUT;
        }
        /**
         * 网络发生错误
         */
        else if (throwable instanceof IOException
                || throwable instanceof SocketException
                || throwable instanceof ConnectException
                || throwable instanceof HttpRetryException
                || throwable instanceof UnknownHostException
                || throwable instanceof NetworkErrorException
                || throwable instanceof retrofit2.HttpException) {
            return ERROR_CONNECT;
        }
        /**
         * rxjava多次onError异常
         * 暂不处理
         */
        else if (throwable instanceof UndeliverableException
                || throwable instanceof OnErrorNotImplementedException
                || throwable instanceof CompositeException
                || throwable instanceof ProtocolViolationException
                || throwable instanceof MissingBackpressureException) {
            return ERROR_DEFAULT;
        } else {
            throwable.printStackTrace();
            return ERROR_UNTREATED;
        }

    }

}
