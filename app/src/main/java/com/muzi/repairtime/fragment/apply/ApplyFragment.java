package com.muzi.repairtime.fragment.apply;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.muzi.repairtime.BR;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.ImagePreviewActivity;
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.adapter.ImagePickerAdapter;
import com.muzi.repairtime.databinding.FragmentApplyBinding;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.entity.CommitEntity;
import com.muzi.repairtime.event.EventConstan;
import com.muzi.repairtime.event.LiveEventBus;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.RepairApi;
import com.muzi.repairtime.manager.ExGridLayoutManager;
import com.muzi.repairtime.observer.EntityObserver;
import com.muzi.repairtime.utils.ImgPartUtils;
import com.muzi.repairtime.utils.StringUtils;
import com.muzi.repairtime.utils.ToastUtils;
import com.muzi.repairtime.widget.dialog.SelectDialog;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import okhttp3.MultipartBody;
import top.zibin.luban.Luban;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能: 维修申请
 */
public class ApplyFragment extends BaseFragment<FragmentApplyBinding, ApplyViewModel> {

    public static ApplyFragment getInstance() {
        ApplyFragment fragment = new ApplyFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private int maxImgCount = 5;
    private SelectDialog selectDialog;
    private ImagePickerAdapter adapter;
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_apply;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initView() {
        super.initView();
        binding.toolbar.setTitle("维修申请");
        initToolbarNav(binding.toolbar);

        initRecyclerView();
        binding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
    }

    private void initRecyclerView() {
        mAlbumFiles.add(new AlbumFile());
        binding.recyclerView.setLayoutManager(new ExGridLayoutManager(getContext(), 3));
        binding.recyclerView.setHasFixedSize(true);
        adapter = new ImagePickerAdapter(R.layout.item_image, mAlbumFiles);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                AlbumFile albumFile = mAlbumFiles.get(position);
                if (StringUtils.isEmpty(albumFile.getPath())) {
                    initDialog();
                } else {
                    previewImage(0);
                }
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                if (view.getId() == R.id.iv_del) {
                    removeImage(position);
                }
            }
        });
    }

    private void selectImage() {
        Album.image(this)
                .multipleChoice()
                .camera(true)
                .columnCount(3)
                .selectCount(maxImgCount)
                .checkedList(mAlbumFiles)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        notifyDataSetChanged(result);
                    }
                })
                .start();
    }

    private void takePhoto() {
        Album.camera(this) // Camera function.
                .image() // Take Picture.
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        notifyDataSetChanged(result);
                    }
                })
                .start();
    }

    private void previewImage(final int position) {
        Observable.fromIterable(mAlbumFiles)
                .filter(new Predicate<AlbumFile>() {
                    @Override
                    public boolean test(AlbumFile albumFile) throws Exception {
                        return StringUtils.isNotEmpty(albumFile.getPath());
                    }
                })
                .map(new Function<AlbumFile, String>() {
                    @Override
                    public String apply(AlbumFile albumFile) throws Exception {
                        return albumFile.getPath();
                    }
                })
                .toList()
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> list) throws Exception {
                        ImagePreviewActivity.startIntent(getContext(), list, position);
                    }
                });
//        Album.galleryAlbum(this)
//                .checkable(true)
//                .checkedList(mAlbumFiles)
//                .currentPosition(position)
//                .onResult(new Action<ArrayList<AlbumFile>>() {
//                    @Override
//                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
//                        notifyDataSetChanged(result);
//                    }
//                })
//                .start();
    }

    private void initDialog() {
        if (selectDialog == null) {
            selectDialog = new SelectDialog(getActivity(), new SelectDialog.SelectDialogListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            takePhoto();
                            break;
                        case 1:
                            selectImage();
                            break;

                    }
                }
            }, Arrays.asList("拍照", "相册"));
        }
        if (!getActivity().isFinishing()) {
            selectDialog.show();
        }
    }

    private void commit() {
        if (StringUtils.isEmpty(viewModel.itemField.get())) {
            ToastUtils.showToast("请选择维修项目");
            return;
        }
        if (StringUtils.isEmpty(viewModel.item1Field.get())) {
            ToastUtils.showToast("请选择维修细项");
            return;
        }
        if (StringUtils.isEmpty(viewModel.describeField.get())) {
            ToastUtils.showToast("请输入对问题的描述");
            return;
        }

        imageCommit();
    }

    private void imageCommit() {
        RxHttp.getApi(RepairApi.class)
                .commitRepair(viewModel.itemField.get(),
                        viewModel.item1Field.get(),
                        viewModel.describeField.get())
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
                .compose(this.<BaseEntity>bindUntilEvent())
                .subscribe(new EntityObserver<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        clean();
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
        if (mAlbumFiles.size() < 1) {
            return;
        }
        Observable.fromIterable(mAlbumFiles)
                .filter(new Predicate<AlbumFile>() {
                    @Override
                    public boolean test(AlbumFile albumFile) throws Exception {
                        return StringUtils.isNotEmpty(albumFile.getPath());
                    }
                })
                .map(new Function<AlbumFile, String>() {
                    @Override
                    public String apply(AlbumFile albumFile) throws Exception {
                        return albumFile.getPath();
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

    private void notifyDataSetChanged(ArrayList<AlbumFile> arrayList) {
        if (arrayList.size() < maxImgCount) {
            arrayList.add(new AlbumFile());
        }
        mAlbumFiles.clear();
        mAlbumFiles.addAll(arrayList);
        adapter.notifyDataSetChanged();
    }

    private void notifyDataSetChanged(String path) {
        if (StringUtils.isEmpty(path)) {
            return;
        }
        AlbumFile albumFile = new AlbumFile();
        albumFile.setPath(path);
        AlbumFile lastFile = mAlbumFiles.get(mAlbumFiles.size() - 1);
        if (StringUtils.isEmpty(lastFile.getPath())) {
            mAlbumFiles.add(mAlbumFiles.size() - 1, albumFile);
        }
        if (mAlbumFiles.size() > maxImgCount) {
            mAlbumFiles.remove(mAlbumFiles.size() - 1);
        }
        adapter.notifyDataSetChanged();
    }

    private void removeImage(int position) {
        AlbumFile albumFile = mAlbumFiles.get(mAlbumFiles.size() - 1);
        if (StringUtils.isNotEmpty(albumFile.getPath())) {
            mAlbumFiles.remove(position);
            mAlbumFiles.add(new AlbumFile());
            adapter.notifyDataSetChanged();
        } else {
            mAlbumFiles.remove(position);
            adapter.notifyItemRemoved(position);
        }
    }

    private void clean() {
        viewModel.itemField.set("");
        viewModel.item1Field.set("");
        viewModel.describeField.set("");
        mAlbumFiles.clear();
        mAlbumFiles.add(new AlbumFile());
        adapter.notifyDataSetChanged();
    }

}
