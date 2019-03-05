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
public class DataProxy implements IData {

    private IData iData;

    private static volatile DataProxy dataProxy;

    public static DataProxy getInstance() {
        if (dataProxy == null) {
            synchronized (DataProxy.class) {
                if (dataProxy == null) {
                    dataProxy = new DataProxy();
                }
            }
        }
        return dataProxy;
    }

    private DataProxy() {
    }

    public void init(IData iData) {
        this.iData = iData;
    }

    private void checkNotNull() {
        if (iData == null) {
            throw new IllegalArgumentException("iData must not be null!");
        }
    }

    @Override
    public <T> void set(String key, List<T> list, long life, TimeUnit unit) {
        checkNotNull();
        iData.set(key, list, life, unit);
    }

    @Override
    public <T> void set(String key, List<T> list) {
        checkNotNull();
        iData.set(key, list);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> tClass) {
        checkNotNull();
        return iData.getList(key, tClass);
    }

    @Override
    public <T> List<T> getList(String key, List<T> defaultValue, Class<T> tClass) {
        checkNotNull();
        iData.getList(key, defaultValue, tClass);
        return null;
    }

    @Override
    public void set(String key, String value) {
        checkNotNull();
        iData.set(key, value);
    }

    @Override
    public void set(String key, String value, long life, TimeUnit unit) {
        checkNotNull();
        iData.set(key, value, life, unit);
    }

    @Override
    public String getString(String key) {
        checkNotNull();
        return iData.getString(key);
    }

    @Override
    public String getString(String key, String defaultValue) {
        checkNotNull();
        return iData.getString(key, defaultValue);
    }

    @Override
    public void set(String key, int value) {
        checkNotNull();
        iData.set(key, value);
    }

    @Override
    public void set(String key, int value, long life, TimeUnit unit) {
        checkNotNull();
        iData.set(key, value, life, unit);
    }

    @Override
    public int getInt(String key) {
        checkNotNull();
        return iData.getInt(key);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        checkNotNull();
        return iData.getInt(key, defaultValue);
    }

    @Override
    public void set(String key, boolean value) {
        checkNotNull();
        iData.set(key, value);
    }

    @Override
    public void set(String key, boolean value, long life, TimeUnit unit) {
        checkNotNull();
        iData.set(key, value, life, unit);
    }

    @Override
    public boolean getBoolean(String key) {
        checkNotNull();
        return iData.getBoolean(key);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        checkNotNull();
        return iData.getBoolean(key, defaultValue);
    }

    @Override
    public void set(String key, float value) {
        checkNotNull();
        iData.set(key, value);
    }

    @Override
    public void set(String key, float value, long life, TimeUnit unit) {
        checkNotNull();
        iData.set(key, value, life, unit);
    }

    @Override
    public float getFloat(String key) {
        checkNotNull();
        return iData.getFloat(key);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        checkNotNull();
        return iData.getFloat(key, defaultValue);
    }

    @Override
    public void set(String key, long value) {
        checkNotNull();
        iData.set(key, value);
    }

    @Override
    public void set(String key, long value, long life, TimeUnit unit) {
        checkNotNull();
        iData.set(key, value, life, unit);
    }

    @Override
    public long getLong(String key) {
        checkNotNull();
        return iData.getLong(key);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        checkNotNull();
        return iData.getLong(key, defaultValue);
    }

    @Override
    public void set(String key, Set<String> value) {
        checkNotNull();
        iData.set(key, value);
    }

    @Override
    public void set(String key, Set<String> value, long life, TimeUnit unit) {
        checkNotNull();
        iData.set(key, value, life, unit);
    }

    @Override
    public Set<String> getStringSet(String key) {
        checkNotNull();
        return iData.getStringSet(key);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        checkNotNull();
        return iData.getStringSet(key, defaultValue);
    }

    @Override
    public boolean contains(String key) {
        checkNotNull();
        return iData.contains(key);
    }

    @Override
    public void remove(String... keys) {
        checkNotNull();
        iData.remove(keys);
    }

    @Override
    public void clear() {
        checkNotNull();
        iData.clear();
    }

}
