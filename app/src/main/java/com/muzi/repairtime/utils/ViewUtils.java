package com.muzi.repairtime.utils;

import android.widget.EditText;
import android.widget.TextView;

/**
 * 作者: lipeng
 * 时间: 2019/3/5
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ViewUtils {

    /**
     * getText
     *
     * @param editText
     * @return
     */
    public static String getText(EditText editText) {
        try {
            return editText.getText().toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 移动光标到末尾
     *
     * @param editText
     */
    public static void setSelection(EditText editText) {
        if (StringUtils.isNotEmpty(editText.getText())) {
            editText.setSelection(editText.getText().length());
        }
    }

    /**
     * setText
     *
     * @param textView
     * @param text
     */
    public static void setText(TextView textView, String text) {
        if (textView != null && StringUtils.isEmpty(text)) {
            return;
        }
        textView.setText(text);
        if (textView instanceof EditText) {
            setSelection((EditText) textView);
        }
    }

}
