package com.muzi.repairtime.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者: lipeng
 * 时间: 2019/3/14
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class DateUtils {

    public static String long2String(long lt){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

}
