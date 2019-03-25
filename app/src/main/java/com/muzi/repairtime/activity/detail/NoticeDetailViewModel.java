package com.muzi.repairtime.activity.detail;

import android.app.Application;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.command.BindingCommand;
import com.muzi.repairtime.command.BindingConsumerAction;

/**
 * 作者: lipeng
 * 时间: 2019/3/25
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class NoticeDetailViewModel extends BaseViewModel {

    public ObservableField<String> title = new ObservableField<>();

    public ObservableField<String> content = new ObservableField<>();

    public NoticeDetailViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void initParam(Bundle bundle) {
        super.initParam(bundle);
        title.set(bundle.getString("title"));
        content.set(bundle.getString("content"));
    }

    /**
     * 返回
     */
    public BindingCommand<View> backCommand = new BindingCommand(new BindingConsumerAction<View>() {
        @Override
        public void call(View view) {
            finish();
        }
    });

}
