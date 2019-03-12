package com.muzi.repairtime.fragment.apply;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.command.BindingCommand;
import com.muzi.repairtime.command.BindingConsumerAction;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ApplyViewModel extends BaseViewModel {

    public ObservableField<String> itemField = new ObservableField<>("");

    public ObservableField<String> item1Field = new ObservableField<>("");

    public ObservableField<String> describeField = new ObservableField<>("");

    public BindingCommand<View> itemClickCommand = new BindingCommand<View>(new BindingConsumerAction<View>() {
        @Override
        public void call(View view) {

        }
    });

    public BindingCommand<View> item1ClickCommand = new BindingCommand<View>(new BindingConsumerAction<View>() {
        @Override
        public void call(View view) {
        }
    });

    public BindingCommand<View> commitClickCommand = new BindingCommand<View>(new BindingConsumerAction<View>() {
        @Override
        public void call(View view) {
            commit();
        }
    });

    public ApplyViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 提交
     */
    private void commit() {

    }

}
