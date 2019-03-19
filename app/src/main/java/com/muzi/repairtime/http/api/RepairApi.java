package com.muzi.repairtime.http.api;

import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.entity.ProjectItemEntity;
import com.muzi.repairtime.entity.ProjectListEntity;
import com.muzi.repairtime.entity.RepairEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者: lipeng
 * 时间: 2019/3/13
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public interface RepairApi {

    /**
     * 获取维修项目list
     *
     * @return
     */
    @POST("order/getRepairFirsts")
    Observable<ProjectListEntity> getProjectList();

    /**
     * 获取详细项目list
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("order/getRepairSecond")
    Observable<ProjectItemEntity> getProjectItemList(@Field("repair_fir_id") int id);

    /**
     * 用户提交申请
     *
     * @param project
     * @param item
     * @param problem
     * @return
     */
    @FormUrlEncoded
    @POST("order/submitOrder")
    Observable<BaseEntity> commitRepair(@Field("repair_fir") String project,
                                        @Field("repair_sec") String item,
                                        @Field("problem") String problem);

    /**
     * 我的维修单
     *
     * @param currentPage
     * @param status      :全部
     *                    1：未完成
     *                    3：已完成
     *                    4：未完成
     *                    2：维修中
     * @return
     */
    @FormUrlEncoded
    @POST("order/myOrder")
    Observable<RepairEntity> getMyRepair(@Field("currentPage") int currentPage,
                                         @Field("status") String status);

    /**
     * 用户删除订单
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("order/deleteOrder")
    Observable<BaseEntity> deleteOrder(@Field("id") int id);

    /**
     * 修改订单状态
     *
     * @param id
     * @param egis true:完成
     *             false:未完成
     * @return
     */
    @FormUrlEncoded
    @POST("order/finishOrder")
    Observable<BaseEntity> finishOrder(@Field("id") int id, @Field("egis") boolean egis);

    /**
     * 维修员查看申请列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("order/repairerOrder")
    Observable<RepairEntity> repairerOrder(@Field("currentPage") int currentPage);

    /**
     * 维修员接单
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("order/takeOrder")
    Observable<BaseEntity> takeOrder(@Field("id") int id);

}
