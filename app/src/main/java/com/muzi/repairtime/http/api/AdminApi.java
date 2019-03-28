package com.muzi.repairtime.http.api;

import com.muzi.repairtime.entity.AuditEntity;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.entity.RepairEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者: lipeng
 * 时间: 2019/3/25
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public interface AdminApi {

    /**
     * 获取审核用户信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("user/listCheckUser")
    Observable<AuditEntity> audit(@Field("currentPage") int currentPage);

    /**
     * 审核用户
     *
     * @param id
     * @param egis true:通过
     *             false:不通过
     * @return
     */
    @FormUrlEncoded
    @POST("user/checkUser")
    Observable<BaseEntity> checkUser(@Field("id") int id,
                                     @Field("egis") boolean egis);

    /**
     * 发布新公告
     *
     * @param title
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST("announcement/addAnnouncement")
    Observable<BaseEntity> pubNotice(@Field("title") String title,
                                     @Field("content") String content);

    /**
     * 删除公告
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("announcement/deleteAnnouncement")
    Observable<BaseEntity> delNotice(@Field("id") int id);

    /**
     * 统计订单
     *
     * @param currentPage
     * @param finishStartTime
     * @param finishEndTime
     * @param reportgroup
     * @param repairer
     * @param orderstatus
     * @param repair_sec
     * @param pic
     * @return
     */
    @FormUrlEncoded
    @POST("order/allOrder")
    Observable<RepairEntity> allOrder(@Field("currentPage") int currentPage,
                                      @Field("finishStartTime") String finishStartTime,
                                      @Field("finishEndTime") String finishEndTime,
                                      @Field("reportgroup") String reportgroup,
                                      @Field("repairer") String repairer,
                                      @Field("orderstatus") String orderstatus,
                                      @Field("repair_sec") String repair_sec,
                                      @Field("pic") String pic);

}
