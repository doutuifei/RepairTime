package com.muzi.repairtime.data;

import android.content.Context;

import com.muzi.repairtime.utils.GsonUtils;
import com.muzi.repairtime.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 作者: lipeng
 * 时间: 2019/1/30
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class MmkvModel implements IData {

    public MmkvModel(Context context) {
        MmkvLifeUtils.init(context);
    }

    @Override
    public <T> void set(String key, List<T> list) {
        MmkvLifeUtils.set(key, GsonUtils.toJsonString(list));
    }

    @Override
    public <T> void set(String key, List<T> list, long life, TimeUnit unit) {
        MmkvLifeUtils.set(key, GsonUtils.toJsonString(list), life, unit);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> tClass) {
        return GsonUtils.toObjectArray(MmkvLifeUtils.getString(key), tClass);
    }

    @Override
    public <T> List<T> getList(String key, List<T> defaultValue, Class<T> tClass) {
        String value = MmkvLifeUtils.getString(key);
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
        MmkvLifeUtils.set(key, value);
    }

    @Override
    public void set(String key, String value, long life, TimeUnit unit) {
        MmkvLifeUtils.set(key, value, life, unit);
    }

    @Override
    public String getString(String key) {
        return MmkvLifeUtils.getString(key);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return MmkvLifeUtils.getString(key, defaultValue);
    }

    @Override
    public void set(String key, int value) {
        MmkvLifeUtils.set(key, value);
    }

    @Override
    public void set(String key, int value, long life, TimeUnit unit) {
        MmkvLifeUtils.set(key, value, life, unit);
    }

    @Override
    public int getInt(String key) {
        return MmkvLifeUtils.getInt(key);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return MmkvLifeUtils.getInt(key, defaultValue);
    }

    @Override
    public void set(String key, boolean value) {
        MmkvLifeUtils.set(key, value);
    }

    @Override
    public void set(String key, boolean value, long life, TimeUnit unit) {
        MmkvLifeUtils.set(key, value, life, unit);
    }

    @Override
    public boolean getBoolean(String key) {
        return MmkvLifeUtils.getBoolean(key);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return MmkvLifeUtils.getBoolean(key, defaultValue);
    }

    @Override
    public void set(String key, float value) {
        MmkvLifeUtils.set(key, value);
    }

    @Override
    public void set(String key, float value, long life, TimeUnit unit) {
        MmkvLifeUtils.set(key, value, life, unit);
    }

    @Override
    public float getFloat(String key) {
        return MmkvLifeUtils.getFloat(key);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return MmkvLifeUtils.getFloat(key, defaultValue);
    }

    @Override
    public void set(String key, long value) {
        MmkvLifeUtils.set(key, value);
    }

    @Override
    public void set(String key, long value, long life, TimeUnit unit) {
        MmkvLifeUtils.set(key, value, life, unit);
    }

    @Override
    public long getLong(String key) {
        return MmkvLifeUtils.getLong(key);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return MmkvLifeUtils.getLong(key, defaultValue);
    }

    @Override
    public void set(String key, Set<String> value) {
        MmkvLifeUtils.set(key, value);
    }

    @Override
    public void set(String key, Set<String> value, long life, TimeUnit unit) {
        MmkvLifeUtils.set(key, value, life, unit);
    }

    @Override
    public Set<String> getStringSet(String key) {
        return MmkvLifeUtils.getStringSet(key);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return MmkvLifeUtils.getStringSet(key, defaultValue);
    }

    @Override
    public boolean contains(String key) {
        return MmkvLifeUtils.contains(key);
    }

    @Override
    public void remove(String... keys) {
        MmkvLifeUtils.remove(keys);
    }

    @Override
    public void clear() {
        MmkvLifeUtils.clear();
    }

}
