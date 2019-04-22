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

    //基本信息
    public static final String ITEM_INFO = "info";
    //公告
    public static final String ITEM_NOTICE = "notice";
    //普通用户：维修申请、维修员：维修列表
    public static final String ITEM_APPLYING = "applying";
    //普通用户：我的申请
    public static final String ITEM_APPLIED = "applied";
    //人员审核
    public static final String ITEM_AUDIT = "audit";
    //修改密码
    public static final String ITEM_PSD = "psd";


    public static final String PATH_ROOT = Environment.getExternalStorageDirectory().getPath() + "/RepairTime";

    public static final String PATH_RING = PATH_ROOT + "/ring";

    /**
     * 拍照
     */
    public static final String PATH_IMAGE = PATH_ROOT + "/image";

    /**
     * 推送声音
     */
    public static final String RING = PATH_RING + "/ring.mp3";


}
