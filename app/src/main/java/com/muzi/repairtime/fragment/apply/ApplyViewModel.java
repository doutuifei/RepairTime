package com.muzi.repairtime.fragment.apply;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.command.BindingCommand;
import com.muzi.repairtime.command.BindingConsumerAction;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.entity.CommitEntity;
import com.muzi.repairtime.entity.ProjectItemEntity;
import com.muzi.repairtime.entity.ProjectListEntity;
import com.muzi.repairtime.event.EventConstan;
import com.muzi.repairtime.event.LiveEventBus;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.RepairApi;
import com.muzi.repairtime.observer.EntityObserver;
import com.muzi.repairtime.utils.ImgPartUtils;
import com.muzi.repairtime.utils.StringUtils;
import com.muzi.repairtime.utils.ToastUtils;
import com.muzi.repairtime.widget.dialog.ListDialog;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.MultipartBody;
import top.zibin.luban.Luban;

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
                    .compose(RxUtils.<ProjectListEntity>scheduling())
                    .compose(RxUtils.<ProjectListEntity>exceptionTransformer())
                    .compose(getLifecycleProvider().<ProjectListEntity>bindUntilEvent(FragmentEvent.DESTROY))
                    .subscribe(new EntityObserver<ProjectListEntity>() {

                        @Override
                        public void onSuccess(ProjectListEntity projectListEntity) {
                            projectBeanList = projectListEntity.getPages();
                            Observable.just(projectListEntity)
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
                                    .subscribe(new Consumer<List<String>>() {
                                        @Override
                                        public void accept(List<String> list) throws Exception {
                                            projectList = list;
                                            showProgectDialog();
                                        }
                                    });
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
                    item1Field.set("");
                }
            };
        }
        projectDialog.show();
    }

    private void getItemList() {
        if (projectBean == null) {
            ToastUtils.showToast("请先选择维修项目");
            return;
        }
        if (itemList == null) {
            RxHttp.getApi(RepairApi.class)
                    .getProjectItemList(projectBean.getId())
                    .compose(RxUtils.<ProjectItemEntity>scheduling())
                    .compose(RxUtils.<ProjectItemEntity>exceptionTransformer())
                    .compose(getLifecycleProvider().<ProjectItemEntity>bindUntilEvent(FragmentEvent.DESTROY))
                    .subscribe(new EntityObserver<ProjectItemEntity>(this) {
                        @Override
                        public void onSuccess(ProjectItemEntity projectItemEntity) {
                            Observable.just(projectItemEntity)
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
                                    .subscribe(new Consumer<List<String>>() {
                                        @Override
                                        public void accept(List<String> list) throws Exception {
                                            itemList = list;
                                            showItemDialog();
                                        }
                                    });
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

        imageCommit();
    }

    private void imageCommit() {
        RxHttp.getApi(RepairApi.class)
                .commitRepair(itemField.get(),
                        item1Field.get(),
                        describeField.get())
                .flatMap(new Function<CommitEntity, Observable<BaseEntity>>() {
                    @Override
                    public Observable<BaseEntity> apply(CommitEntity entity) throws Exception {
                        uploadImage(entity);
                        return RxHttp.getApi(RepairApi.class)
                                .afterSubmitOrder(entity.getObj().getId());
                    }
                })
                .compose(RxUtils.<BaseEntity>exceptionTransformer())
                .compose(RxUtils.<BaseEntity>scheduling())
                .compose(getLifecycleProvider().<BaseEntity>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new EntityObserver<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        ToastUtils.showToast(baseEntity.getMsg());
                        LiveEventBus.get().with(EventConstan.CHECK_ITEM).postValue(3);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        e.printStackTrace();
                    }
                });

    }

    /**
     * 上传图片
     *
     * @param entity
     */
    private void uploadImage(final CommitEntity entity) {
        ArrayList<ImageItem> selectedImages = ImagePicker.getInstance().getSelectedImages();
        if (selectedImages.isEmpty()) {
            return;
        }
        Observable.fromIterable(selectedImages)
                .map(new Function<ImageItem, String>() {
                    @Override
                    public String apply(ImageItem imageItem) throws Exception {
                        return imageItem.path;
                    }
                })
                .toList()
                .map(new Function<List<String>, List<File>>() {
                    @Override
                    public List<File> apply(List<String> list) throws Exception {
                        return Luban.with(getContext()).load(list).get();
                    }
                })
                .toObservable()
                .map(new Function<List<File>, MultipartBody.Part[]>() {
                    @Override
                    public MultipartBody.Part[] apply(List<File> fileList) throws Exception {
                        return ImgPartUtils.files2Part(fileList);
                    }
                })
                .flatMap(new Function<MultipartBody.Part[], Observable<BaseEntity>>() {
                    @Override
                    public Observable<BaseEntity> apply(MultipartBody.Part[] parts) throws Exception {
                        CommitEntity.ObjBean objBean = entity.getObj();
                        return RxHttp.getApi(RepairApi.class)
                                .upload(objBean.getId(), objBean.getReportgroup(), parts);
                    }
                })
                .compose(RxUtils.<BaseEntity>scheduling())
                .subscribe(new Observer<BaseEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
