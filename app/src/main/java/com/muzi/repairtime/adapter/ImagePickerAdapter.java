package com.muzi.repairtime.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.muzi.repairtime.R;
import com.muzi.repairtime.utils.StringUtils;
import com.yanzhenjie.album.AlbumFile;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/5/4
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ImagePickerAdapter extends BaseQuickAdapter<AlbumFile, BaseViewHolder> {

    public void notifyDataSetChanged(List<AlbumFile> imagePathList) {
        setNewData(imagePathList);
    }

    public ImagePickerAdapter(int layoutResId, @Nullable List<AlbumFile> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlbumFile item) {
        ImageView view = helper.getView(R.id.iv_img);
        if (StringUtils.isEmpty(item.getPath())) {
            helper.setVisible(R.id.iv_del, false);
            Glide.with(view).load(R.drawable.selector_image_add).into(view);
            helper.addOnClickListener(R.id.iv_del);
        } else {
            helper.setVisible(R.id.iv_del, true);
            Glide.with(view).load(item.getPath()).into(view);
            helper.addOnClickListener(R.id.iv_del);
        }
    }

}
