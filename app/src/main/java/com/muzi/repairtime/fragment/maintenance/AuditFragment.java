package com.muzi.repairtime.fragment.maintenance;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.adapter.AuditAdapter;
import com.muzi.repairtime.databinding.FragmentAuditBinding;
import com.muzi.repairtime.entity.AuditEntity;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.AdminApi;
import com.muzi.repairtime.manager.ExLinearLayoutManger;
import com.muzi.repairtime.observer.BaseObserver;
import com.muzi.repairtime.observer.EntityObserver;
import com.muzi.repairtime.utils.ToastUtils;
import com.muzi.repairtime.widget.CustomLoadMoreView;
import com.muzi.repairtime.widget.dialog.CommonDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 作者: lipeng
 * 时间: 2019/3/25
 * 邮箱: lipeng@moyi365.com
 * 功能: 人员审核
 */
public class AuditFragment extends BaseFragment<FragmentAuditBinding, BaseViewModel> {

    public static AuditFragment getInstance() {
        AuditFragment fragment = new AuditFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private int currentPage = 1;
    private int totalPage = 1;
    private AuditAdapter adapter;
    private List<AuditEntity.PagesBean.ListBean> list = new ArrayList<>();

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_audit;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initView() {
        super.initView();
        binding.toolbar.setTitle("人员审核");
        initToolbarNav(binding.toolbar);

        binding.refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        binding.recycelView.setLayoutManager(new ExLinearLayoutManger(getContext()));
        adapter = new AuditAdapter(list);
        adapter.bindToRecyclerView(binding.recycelView);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        adapter.setEmptyView(R.layout.layout_recyclerview_empty);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (currentPage < totalPage) {
                    currentPage++;
                    getData();
                } else {
                    adapter.loadMoreEnd();
                }
            }
        }, binding.recycelView);

        binding.recycelView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                final AuditEntity.PagesBean.ListBean listBean = list.get(position);
                switch (view.getId()) {
                    case R.id.btn_pass:
                        new CommonDialog.Builder(getContext())
                                .setTitle("提示")
                                .setContent("确认通过对" + listBean.getName() + "的审核吗?")
                                .build(new CommonDialog.OnDialogClickListener() {
                                    @Override
                                    public void onCancelClick(View v, Dialog dialog) {

                                    }

                                    @Override
                                    public void onConfirmClick(View v, Dialog dialog) {
                                        checkUser(listBean.getId(), true);
                                    }
                                }).show();
                        break;
                    case R.id.btn_nopass:
                        new CommonDialog.Builder(getContext())
                                .setTitle("提示")
                                .setContent("确认不通过对" + listBean.getName() + "的审核吗?")
                                .build(new CommonDialog.OnDialogClickListener() {
                                    @Override
                                    public void onCancelClick(View v, Dialog dialog) {

                                    }

                                    @Override
                                    public void onConfirmClick(View v, Dialog dialog) {
                                        checkUser(listBean.getId(), false);
                                    }
                                }).show();
                        break;
                }
            }
        });
    }

    /**
     * 刷新数据
     */
    private void refresh() {
        if (!list.isEmpty()) {
            currentPage = 1;
            totalPage = 1;
            list.clear();
            adapter.setNewData(list);
        }
        getData();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        binding.refreshLayout.setRefreshing(true);
        getData();
    }

    private void getData() {
        RxHttp.getApi(AdminApi.class)
                .audit(currentPage)
                .doOnNext(new Consumer<AuditEntity>() {
                    @Override
                    public void accept(AuditEntity auditEntity) throws Exception {
                        totalPage = auditEntity.getPages().getTotalPage();
                        currentPage = auditEntity.getPages().getCurrentPage();
                    }
                })
                .map(new Function<AuditEntity, List<AuditEntity.PagesBean.ListBean>>() {
                    @Override
                    public List<AuditEntity.PagesBean.ListBean> apply(AuditEntity auditEntity) throws Exception {
                        return auditEntity.getPages().getList();
                    }
                })
                .compose(RxUtils.<List<AuditEntity.PagesBean.ListBean>>scheduling())
                .compose(RxUtils.<List<AuditEntity.PagesBean.ListBean>>bindToLifecycle(this))
                .subscribe(new BaseObserver<List<AuditEntity.PagesBean.ListBean>>() {
                    @Override
                    public void onNext(List<AuditEntity.PagesBean.ListBean> listBeans) {
                        super.onNext(listBeans);
                        list.addAll(listBeans);
                        adapter.notifyDataSetChanged();
                        if (adapter.isLoading()) {
                            adapter.loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        super.onError(msg);
                        if (adapter.isLoading()) {
                            adapter.loadMoreFail();
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        if (binding.refreshLayout.isRefreshing()) {
                            binding.refreshLayout.setRefreshing(false);
                        }
                    }
                });
    }

    /**
     * 审核用户
     *
     * @param id
     * @param egis
     */
    private void checkUser(int id, boolean egis) {
        RxHttp.getApi(AdminApi.class)
                .checkUser(id, egis)
                .compose(RxUtils.<BaseEntity>scheduling())
                .compose(RxUtils.<BaseEntity>bindToLifecycle(this))
                .subscribe(new EntityObserver<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        ToastUtils.showToast(entity.getMsg());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        refresh();
                    }
                });
    }

}
