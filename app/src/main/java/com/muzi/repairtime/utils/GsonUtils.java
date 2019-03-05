package com.muzi.repairtime.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 作者: lipeng
 * 时间: 2019/1/4
 * 邮箱: lipeng@moyi365.com
 * 功能: Gson数据转换
 */
public class GsonUtils {

    private static Gson sJsonUtils = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static String toJsonString(Object obj) {
        return sJsonUtils.toJson(obj);
    }

    public static <T> T toObject(String json, Class<T> c) {
        try {
            return sJsonUtils.fromJson(json, c);
        } catch (Exception e) {
            e.printStackTrace();
            return toObjectByReflect(json, c);
        }
    }

    public static <T> ArrayList<T> toObjectArray(String json, Class<T> c) {
        try {
            if (StringUtils.isEmpty(json)) {
                return new ArrayList<T>();
            }
            JSONArray ja = new JSONArray(json);
            if (ja == null || ja.length() < 1) {
                return new ArrayList<T>();
            }
            int length = ja.length();
            ArrayList<T> ret = new ArrayList<T>(length);
            for (int i = 0; i < length; ++i) {
                ret.add(toObject(ja.getString(i), c));
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }

    public static <T> LinkedList<T> toObjectLinked(String json, Class<T> c) {
        try {
            JSONArray ja = new JSONArray(json);
            int length = ja.length();
            LinkedList<T> ret = new LinkedList<T>();
            for (int i = 0; i < length; ++i) {
                ret.add(toObject(ja.getString(i), c));
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new LinkedList<T>();
    }

    private static <T> T toObjectByReflect(String json, Class<T> c) {
        Field[] flds = c.getDeclaredFields();
        try {
            JSONObject jo = new JSONObject(json);
            T ret = c.newInstance();
            for (Field f : flds) {
                if (jo.optString(f.getName()).length() > 0) {
                    f.setAccessible(true);
                    f.set(ret, transfer(jo, f.getName(), f.getType()));
                }
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static <T> T transfer(JSONObject jo, String name, Class<T> c) {
        if (c == String.class) {
            return (T) jo.optString(name);
        } else if (c == int.class) {
            return (T) (Integer) jo.optInt(name);
        } else if (c == long.class) {
            return (T) (Long) jo.optLong(name);
        } else if (c == boolean.class) {
            return (T) (Boolean) jo.optBoolean(name);
        } else if (c == float.class) {
            return (T) (Float) (float) jo.optDouble(name);
        } else if (c == double.class) {
            return (T) (Double) jo.optDouble(name);
        } else {
            return toObjectByReflect(jo.optString(name), c);
        }
    }

}
