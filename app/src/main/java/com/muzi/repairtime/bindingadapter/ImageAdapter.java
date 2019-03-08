package com.muzi.repairtime.bindingadapter;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * 作者: lipeng
 * 时间: 2019/3/8
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ImageAdapter {

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     * @param placeholder
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"url", "placeholder"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, Drawable placeholder) {
        //使用Glide框架加载图片
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions().placeholder(placeholder))
                .into(imageView);
    }


}
