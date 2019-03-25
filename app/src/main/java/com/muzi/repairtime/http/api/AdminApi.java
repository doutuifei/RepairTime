package com.muzi.repairtime.http.api;

import com.muzi.repairtime.entity.AuditEntity;
import com.muzi.repairtime.entity.BaseEntity;

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

}
