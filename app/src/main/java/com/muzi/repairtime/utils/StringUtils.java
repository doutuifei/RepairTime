package com.muzi.repairtime.utils;

/**
 * 作者: lipeng
 * 时间: 2018/12/18
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class StringUtils {

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0 || "".equals(s) || "null".equals(s);
    }

    /**
     * @param s
     * @return
     */
    public static boolean isNotEmpty(CharSequence s) {
        return !isEmpty(s);
    }

    /**
     * string转int
     *
     * @param target
     * @param defaultValue
     * @return
     */
    public static int string2int(String target, int defaultValue) {
        int value = defaultValue;
        try {
            value = Integer.parseInt(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

}
