package com.muzi.repairtime.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.muzi.repairtime.R;
import com.muzi.repairtime.adapter.GroupAdapter;
import com.muzi.repairtime.entity.GroupEntity;
import com.muzi.repairtime.manager.ExLinearLayoutManger;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/3/7
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public abstract class GroupDialog extends Dialog {

    private Context context;
    private List<GroupEntity.PagesBean> list;

    private RecyclerView recyclerView;
    private GroupAdapter adapter;

    public GroupDialog(@NonNull Context context, GroupEntity entity) {
        super(context, R.style.CustomDialog);
        this.context = context;
        setContentView(R.layout.layout_dialog_group);
        list = entity.getPages();

        initAttribute();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycelView);
        recyclerView.setLayoutManager(new ExLinearLayoutManger(context));
        adapter = new GroupAdapter(R.layout.layout_item_group, list);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                onSelect(list.get(position));
                dismiss();
            }
        });
    }

    private void initAttribute() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
    }

    @Override
    public void show() {
        try {
            if (context != null && !((Activity) context).isFinishing()) {
                super.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            if (context != null && !((Activity) context).isFinishing()) {
                super.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void onSelect(GroupEntity.PagesBean pagesBean);

}
