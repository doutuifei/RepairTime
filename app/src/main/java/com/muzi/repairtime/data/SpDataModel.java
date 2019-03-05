package com.muzi.repairtime.data;


import com.muzi.repairtime.utils.GsonUtils;
import com.muzi.repairtime.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 作者: lipeng
 * 时间: 2019/1/29
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class SpDataModel implements IData {

    @Override
    public <T> void set(String key, List<T> list) {
        SPLifeUtils.set(key, GsonUtils.toJsonString(list));
    }

    @Override
    public <T> void set(String key, List<T> list, long life, TimeUnit unit) {
        SPLifeUtils.set(key, GsonUtils.toJsonString(list), life, unit);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> tClass) {
        return GsonUtils.toObjectArray(SPLifeUtils.getString(key), tClass);
    }

    @Override
    public <T> List<T> getList(String key, List<T> defaultValue, Class<T> tClass) {
        String value = SPLifeUtils.getString(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        ArrayList<T> valueList = GsonUtils.toObjectArray(value, tClass);
        if (valueList == null || valueList.isEmpty()) {
            return defaultValue;
        }
        return valueList;
    }

    @Override
    public void set(String key, String value) {
        SPLifeUtils.set(key, value);
    }

    @Override
    public void set(String key, String value, long life, TimeUnit unit) {
        SPLifeUtils.set(key, value, life, unit);
    }

    @Override
    public String getString(String key) {
        return SPLifeUtils.getString(key);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return SPLifeUtils.getString(key, defaultValue);
    }

    @Override
    public void set(String key, int value) {
        SPLifeUtils.set(key, value);
    }

    @Override
    public void set(String key, int value, long life, TimeUnit unit) {
        SPLifeUtils.set(key, value, life, unit);
    }

    @Override
    public int getInt(String key) {
        return SPLifeUtils.getInt(key);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return SPLifeUtils.getInt(key, defaultValue);
    }

    @Override
    public void set(String key, boolean value) {
        SPLifeUtils.set(key, value);
    }

    @Override
    public void set(String key, boolean value, long life, TimeUnit unit) {
        SPLifeUtils.set(key, value, life, unit);
    }

    @Override
    public boolean getBoolean(String key) {
        return SPLifeUtils.getBoolean(key);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return SPLifeUtils.getBoolean(key, defaultValue);
    }

    @Override
    public void set(String key, float value) {
        SPLifeUtils.set(key, value);
    }

    @Override
    public void set(String key, float value, long life, TimeUnit unit) {
        SPLifeUtils.set(key, value, life, unit);
    }

    @Override
    public float getFloat(String key) {
        return SPLifeUtils.getFloat(key);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return SPLifeUtils.getFloat(key, defaultValue);
    }

    @Override
    public void set(String key, long value) {
        SPLifeUtils.set(key, value);
    }

    @Override
    public void set(String key, long value, long life, TimeUnit unit) {
        SPLifeUtils.set(key, value, life, unit);
    }

    @Override
    public long getLong(String key) {
        return SPLifeUtils.getLong(key);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return SPLifeUtils.getLong(key, defaultValue);
    }

    @Override
    public void set(String key, Set<String> value) {
        SPLifeUtils.set(key, value);
    }

    @Override
    public void set(String key, Set<String> value, long life, TimeUnit unit) {
        SPLifeUtils.set(key, value, life, unit);
    }

    @Override
    public Set<String> getStringSet(String key) {
        return SPLifeUtils.getStringSet(key);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return SPLifeUtils.getStringSet(key, defaultValue);
    }

    @Override
    public boolean contains(String key) {
        return SPLifeUtils.contains(key);
    }

    @Override
    public void remove(String... keys) {
        SPLifeUtils.remove(keys);
    }

    @Override
    public void clear() {
        SPLifeUtils.clear();
    }

}
