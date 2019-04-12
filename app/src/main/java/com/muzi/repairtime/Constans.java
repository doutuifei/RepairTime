package com.muzi.repairtime;

import android.os.Environment;

/**
 * 作者: lipeng
 * 时间: 2019/3/5
 * 邮箱: lipeng@moyi365.com
 * 功能: 常量
 */
public class Constans {

    /**
     * 存储键值
     */
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PSD = "psd";
    public static final String KEY_TYPE = "type";
    public static final String KEY_USER = "user";

    /**
     * 推送声音
     */
    public static final String RING = Environment.getExternalStorageDirectory().getPath() + "/Ring/ring.mp3";


}
