package com.muzi.repairtime.http.api;

import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.entity.ProjectItemEntity;
import com.muzi.repairtime.entity.ProjectListEntity;

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
     * 提交申请
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

}
