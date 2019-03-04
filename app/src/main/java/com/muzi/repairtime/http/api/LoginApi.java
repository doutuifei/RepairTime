package com.muzi.repairtime.http.api;

import com.muzi.repairtime.Entity.BaseEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者: lipeng
 * 时间: 2019/3/4
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public interface LoginApi {

    /**
     * 登录接口
     *
     * @param phone
     * @param psd
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseEntity> login(@Field("phone") String phone,
                                 @Field("pass") String psd);


}
