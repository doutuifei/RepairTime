package com.muzi.repairtime.command;

/**
 * 作者: lipeng
 * 时间: 2019/1/3
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public interface BindingConsumerAction<T> {
    void call(T t);
}

