package com.muzi.repairtime.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.muzi.repairtime.R;
import com.muzi.repairtime.databinding.LayoutDialogFilterBinding;
import com.muzi.repairtime.entity.FilterEntity;
import com.muzi.repairtime.entity.GroupEntity;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.LoginApi;
import com.muzi.repairtime.observer.BaseObserver;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 作者: lipeng
 * 时间: 2019/3/26
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public abstract class FilterDialog extends Dialog {

    private Context context;
    private FilterEntity filterEntity = new FilterEntity();
    private LayoutDialogFilterBinding binding;
    private CalendarDialog calendarDialog;

    //科室信息
    private List<String> groupList;
    private ListDialog groupDialog;

    public FilterDialog(Context context) {
        super(context, R.style.CustomDialog);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_dialog_filter, null, false);
        setContentView(binding.getRoot());
        initAttribute();
        initView();
        initData();
    }

    private void initView() {
        binding.tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarDialog(1);
            }
        });
        binding.tvEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarDialog(2);
            }
        });
        binding.tvGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.tvPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.tvPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.tvBoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFilter(filterEntity);
                dismiss();
            }
        });
    }

    /**
     * 显示时间dialog
     *
     * @param requestCode
     */
    private void showCalendarDialog(int requestCode) {
        if (calendarDialog == null) {
            calendarDialog = new CalendarDialog(context) {
                @Override
                public void onSelect(int requestCode, String date) {
                    switch (requestCode) {
                        case 1:
                            binding.tvStart.setText(date);
                            filterEntity.setFinishStartTime(date);
                            break;
                        case 2:
                            binding.tvEnd.setText(date);
                            filterEntity.setFinishEndTime(date);
                            break;
                    }
                }
            };
        }
        calendarDialog.show(requestCode);
    }

    private void initData() {
        if (groupList == null) {
            RxHttp.getApi(LoginApi.class)
                    .getGroups()
                    .flatMap(new Function<GroupEntity, ObservableSource<GroupEntity.PagesBean>>() {
                        @Override
                        public ObservableSource<GroupEntity.PagesBean> apply(GroupEntity entity) throws Exception {
                            return Observable.fromIterable(entity.getPages());
                        }
                    })
                    .map(new Function<GroupEntity.PagesBean, String>() {
                        @Override
                        public String apply(GroupEntity.PagesBean pagesBean) throws Exception {
                            return pagesBean.getName();
                        }
                    })
                    .toList()
                    .toObservable()
                    .compose(RxUtils.<List<String>>scheduling())
                    .subscribe(new BaseObserver<List<String>>() {
                        @Override
                        public void onNext(List<String> list) {
                            super.onNext(list);
                            groupList = list;
                        }
                    });
        }
    }

    /**
     * 选择科室dialog
     */
    private void showGroupDialog() {
        if (groupDialog == null) {
            groupDialog = new ListDialog(getContext(), "选择科室", groupList) {
                @Override
                public void onSelect(int position) {
                    binding.tvGroup.setText(groupList.get(position));
                }
            };
        }
        groupDialog.show();
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
            if (context != null) {
                super.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            if (context != null) {
                super.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void onFilter(FilterEntity filterEntity);

}
