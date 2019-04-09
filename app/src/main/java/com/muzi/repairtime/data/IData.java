package com.muzi.repairtime.data;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 作者: lipeng
 * 时间: 2019/1/29
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public interface IData {

    void set(String key, Object o);

    void set(String key, Object o, long life, TimeUnit unit);

    <T> T get(String key, Class<T> tClass);

    <T> void set(String key, List<T> list);

    <T> void set(String key, List<T> list, long life, TimeUnit unit);

    <T> List<T> getList(String key, Class<T> tClass);

    <T> List<T> getList(String key, List<T> defaultValue, Class<T> tClass);

    void set(String key, String value);

    void set(String key, String value, long life, TimeUnit unit);

    String getString(String key);

    String getString(String key, String defaultValue);

    void set(String key, int value);

    void set(String key, int value, long life, TimeUnit unit);

    int getInt(String key);

    int getInt(String key, int defaultValue);

    void set(String key, boolean value);

    void set(String key, boolean value, long life, TimeUnit unit);

    boolean getBoolean(String key);

    boolean getBoolean(String key, boolean defaultValue);

    void set(String key, float value);

    void set(String key, float value, long life, TimeUnit unit);

    float getFloat(String key);

    float getFloat(String key, float defaultValue);

    void set(String key, long value);

    void set(String key, long value, long life, TimeUnit unit);

    long getLong(String key);

    long getLong(String key, long defaultValue);

    void set(String key, Set<String> value);

    void set(String key, Set<String> value, long life, TimeUnit unit);

    Set<String> getStringSet(String key);

    Set<String> getStringSet(String key, Set<String> defaultValue);

    boolean contains(String key);

    /**
     * 删除数据
     *
     * @param keys
     */
    void remove(String... keys);

    /**
     * 清空数据
     */
    void clear();
}
