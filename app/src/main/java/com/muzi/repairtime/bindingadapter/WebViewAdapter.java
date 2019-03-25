package com.muzi.repairtime.bindingadapter;

import android.databinding.BindingAdapter;
import android.webkit.WebView;

/**
 * 作者: lipeng
 * 时间: 2019/3/25
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class WebViewAdapter {

    /**
     * 加载html片段
     *
     * @param webView
     * @param body
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"loadHtml"}, requireAll = false)
    public static void loadData(final WebView webView, final String body) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>html{padding:15px;} body{word-wrap:break-word;font-size:13px;padding:0px;margin:0px} p{padding:0px;margin:0px;font-size:13px;color:#222222;line-height:1.3;} img{padding:0px,margin:0px;max-width:100%; width:auto; height:auto;}</style>" +
                "</head>";
        String htmlData = "<html>" + head + "<body>" + body + "</body></html>";
        webView.loadData(htmlData, "text/html;charset=utf-8", "utf-8");
    }

}
