package com.muzi.repairtime.fragment.employee;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.adapter.ApplyItemAdapter;
import com.muzi.repairtime.databinding.FragmentItemApplyBinding;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.entity.RepairEntity;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.RepairApi;
import com.muzi.repairtime.manager.ExLinearLayoutManger;
import com.muzi.repairtime.observer.BaseObserver;
import com.muzi.repairtime.observer.EntityObserver;
import com.muzi.repairtime.utils.ToastUtils;
import com.muzi.repairtime.widget.CustomLoadMoreView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 作者: lipeng
 * 时间: 2019/3/13
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ApplyItemFragment extends BaseFragment<FragmentItemApplyBinding, BaseViewModel> {

    private String status;
    private int currentPage = 1;
    private int totalPage = 1;
    private ApplyItemAdapter adapter;
    private List<RepairEntity.PagesBean.ListBean> listBeans = new ArrayList<>();

    public static ApplyItemFragment getInstance(String status) {
        ApplyItemFragment fragment = new ApplyItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("status", status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_item_apply;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initParam(Bundle bundle) {
        super.initParam(bundle);
        status = bundle.getString("status");
    }

    @Override
    public void initView() {
        super.initView();
        binding.refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!listBeans.isEmpty()) {
                    currentPage = 1;
                    totalPage = 1;
                    listBeans.clear();
                    adapter.setNewData(listBeans);
                }
                getData();
            }
        });
        binding.recycelView.setLayoutManager(new ExLinearLayoutManger(getContext()));
        adapter = new ApplyItemAdapter(listBeans);
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

        adapter.setOnRatingBar(new ApplyItemAdapter.onRatingBar() {
            @Override
            public void rating(int position, float rating) {
                evaluateOrder(position, (int) rating);
            }
        });
        binding.recycelView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()) {
                    case R.id.btn_delete:
                        deleteOrder(position);
                        break;
                    case R.id.btn_finished:
                        finishOrder(position, true);
                        break;
                    case R.id.btn_unfinished:
                        finishOrder(position, false);
                        break;
                }
            }
        });

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        binding.refreshLayout.setRefreshing(true);
        getData();
    }

    /**
     * 获取信息
     */
    private void getData() {
        RxHttp.getApi(RepairApi.class)
                .getMyRepair(currentPage, status)
                .doOnNext(new Consumer<RepairEntity>() {
                    @Override
                    public void accept(RepairEntity repairEntity) throws Exception {
                        totalPage = repairEntity.getPages().getTotalPage();
                        currentPage = repairEntity.getPages().getCurrentPage();
                    }
                })
                .map(new Function<RepairEntity, List<RepairEntity.PagesBean.ListBean>>() {
                    @Override
                    public List<RepairEntity.PagesBean.ListBean> apply(RepairEntity repairEntity) throws Exception {
                        return repairEntity.getPages().getList();
                    }
                })
                .compose(RxUtils.<List<RepairEntity.PagesBean.ListBean>>scheduling())
                .subscribe(new BaseObserver<List<RepairEntity.PagesBean.ListBean>>() {
                    @Override
                    public void onNext(List<RepairEntity.PagesBean.ListBean> list) {
                        super.onNext(listBeans);
                        listBeans.addAll(list);
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
     * 删除订单
     *
     * @param position
     */
    private void deleteOrder(final int position) {
        int id = listBeans.get(position).getId();
        RxHttp.getApi(RepairApi.class)
                .deleteOrder(id)
                .compose(RxUtils.<BaseEntity>scheduling())
                .compose(RxUtils.<BaseEntity>bindToLifecycle(this))
                .subscribe(new EntityObserver<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        ToastUtils.showToast(entity.getMsg());
                        listBeans.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 更改订单状态
     *
     * @param position
     * @param status
     */
    private void finishOrder(final int position, boolean status) {
        int id = listBeans.get(position).getId();
        RxHttp.getApi(RepairApi.class)
                .finishOrder(id, status)
                .compose(RxUtils.<BaseEntity>scheduling())
                .compose(RxUtils.<BaseEntity>bindToLifecycle(this))
                .subscribe(new EntityObserver<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        ToastUtils.showToast(entity.getMsg());
                        listBeans.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 评价订单
     *
     * @param position
     */
    private void evaluateOrder(final int position, final int star) {
        int id = listBeans.get(position).getId();
        RxHttp.getApi(RepairApi.class)
                .evaluateOrder(id, star)
                .compose(RxUtils.<BaseEntity>scheduling())
                .compose(RxUtils.<BaseEntity>bindToLifecycle(this))
                .subscribe(new EntityObserver<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        listBeans.get(position).setCs_id(star);
                        ToastUtils.showToast(entity.getMsg());
                        adapter.notifyDataSetChanged();
                    }
                });
    }

}
