package com.muzi.repairtime.fragment.apply;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.command.BindingCommand;
import com.muzi.repairtime.command.BindingConsumerAction;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.entity.ProjectItemEntity;
import com.muzi.repairtime.entity.ProjectListEntity;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.RepairApi;
import com.muzi.repairtime.observer.BaseObserver;
import com.muzi.repairtime.observer.EntityObserver;
import com.muzi.repairtime.utils.StringUtils;
import com.muzi.repairtime.utils.ToastUtils;
import com.muzi.repairtime.widget.dialog.ListDialog;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ApplyViewModel extends BaseViewModel {

    /**
     * 维修项
     */
    private List<String> projectList;
    private ListDialog projectDialog;
    private ProjectListEntity.PagesBean projectBean;
    private List<ProjectListEntity.PagesBean> projectBeanList;

    /**
     * 详细项目
     */
    private List<String> itemList;
    private ListDialog itemDialog;
    private List<ProjectItemEntity.PagesBean> itemBeanList;

    public ObservableField<String> itemField = new ObservableField<>("");

    public ObservableField<String> item1Field = new ObservableField<>("");

    public ObservableField<String> describeField = new ObservableField<>("");

    public BindingCommand<View> itemClickCommand = new BindingCommand<View>(new BindingConsumerAction<View>() {
        @Override
        public void call(View view) {
            getProjectList();
        }
    });

    public BindingCommand<View> item1ClickCommand = new BindingCommand<View>(new BindingConsumerAction<View>() {
        @Override
        public void call(View view) {
            getItemList();
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
     * 获取维修项
     */
    private void getProjectList() {
        if (projectList == null) {
            RxHttp.getApi(RepairApi.class)
                    .getProjectList()
                    .doOnNext(new Consumer<ProjectListEntity>() {
                        @Override
                        public void accept(ProjectListEntity projectListEntity) throws Exception {
                            projectBeanList = projectListEntity.getPages();
                        }
                    })
                    .flatMap(new Function<ProjectListEntity, ObservableSource<ProjectListEntity.PagesBean>>() {
                        @Override
                        public ObservableSource<ProjectListEntity.PagesBean> apply(ProjectListEntity projectListEntity) throws Exception {
                            return Observable.fromIterable(projectListEntity.getPages());
                        }
                    })
                    .map(new Function<ProjectListEntity.PagesBean, String>() {
                        @Override
                        public String apply(ProjectListEntity.PagesBean pagesBean) throws Exception {
                            return pagesBean.getRepair_fir();
                        }
                    })
                    .toList()
                    .toObservable()
                    .compose(RxUtils.<List<String>>scheduling())
                    .compose(RxUtils.<List<String>>bindToLifecycle(getLifecycleProvider()))
                    .subscribe(new BaseObserver<List<String>>() {
                        @Override
                        public void onNext(List<String> list) {
                            super.onNext(list);
                            projectList = list;
                            showProgectDialog();
                        }

                        @Override
                        public void onError(String msg) {
                            super.onError(msg);
                            ToastUtils.showToast(msg);
                        }
                    });
        } else {
            showProgectDialog();
        }
    }

    private void showProgectDialog() {
        if (projectDialog == null) {
            projectDialog = new ListDialog(getContext(), "请选择维修项目", projectList) {
                @Override
                public void onSelect(int position) {
                    itemField.set(projectList.get(position));
                    projectBean = projectBeanList.get(position);
                    itemList = null;
                    itemBeanList = null;
                    item1Field.set("");
                }
            };
        }
        projectDialog.show();
    }

    private void getItemList() {
        if (itemList == null) {
            RxHttp.getApi(RepairApi.class)
                    .getProjectItemList(projectBean.getId())
                    .doOnNext(new Consumer<ProjectItemEntity>() {
                        @Override
                        public void accept(ProjectItemEntity projectItemEntity) throws Exception {
                            itemBeanList = projectItemEntity.getPages();
                        }
                    })
                    .flatMap(new Function<ProjectItemEntity, ObservableSource<ProjectItemEntity.PagesBean>>() {
                        @Override
                        public ObservableSource<ProjectItemEntity.PagesBean> apply(ProjectItemEntity projectItemEntity) throws Exception {
                            return Observable.fromIterable(projectItemEntity.getPages());
                        }
                    })
                    .map(new Function<ProjectItemEntity.PagesBean, String>() {
                        @Override
                        public String apply(ProjectItemEntity.PagesBean pagesBean) throws Exception {
                            return pagesBean.getRepair_sec();
                        }
                    })
                    .toList()
                    .toObservable()
                    .compose(RxUtils.<List<String>>scheduling())
                    .compose(RxUtils.<List<String>>bindToLifecycle(getLifecycleProvider()))
                    .subscribe(new BaseObserver<List<String>>(this) {
                        @Override
                        public void onNext(List<String> list) {
                            super.onNext(list);
                            itemList = list;
                            showItemDialog();
                        }

                        @Override
                        public void onError(String msg) {
                            super.onError(msg);
                            ToastUtils.showToast(msg);
                        }
                    });
        } else {
            showItemDialog();
        }
    }

    private void showItemDialog() {
        if (itemDialog == null) {
            itemDialog = new ListDialog(getContext(), "请选择维修细项", itemList) {
                @Override
                public void onSelect(int position) {
                    item1Field.set(itemList.get(position));
                }
            };
        } else {
            itemDialog.setList(itemList);
        }
        itemDialog.show();
    }

    /**
     * 提交
     */
    private void commit() {
        if (StringUtils.isEmpty(itemField.get())) {
            ToastUtils.showToast("请选择维修项目");
            return;
        }
        if (StringUtils.isEmpty(item1Field.get())) {
            ToastUtils.showToast("请选择维修细项");
            return;
        }
        if (StringUtils.isEmpty(describeField.get())) {
            ToastUtils.showToast("请输入对问题的描述");
            return;
        }
        RxHttp.getApi(RepairApi.class)
                .commitRepair(itemField.get(),
                        item1Field.get(),
                        describeField.get())
                .compose(RxUtils.<BaseEntity>scheduling())
                .compose(RxUtils.<BaseEntity>bindToLifecycle(getLifecycleProvider()))
                .subscribe(new EntityObserver<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        ToastUtils.showToast(entity.getMsg());
                    }
                });
    }

}
