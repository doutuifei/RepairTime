package com.muzi.repairtime.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.muzi.repairtime.R;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/4/22
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ImageAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.iv_img);
        com.muzi.repairtime.bindingadapter.ImageAdapter.setImageUri(imageView, item, R.drawable.selector_image_add);
    }

}
