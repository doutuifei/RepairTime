package com.muzi.repairtime.http.api;

import com.muzi.repairtime.entity.UserEntity;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public interface UserApi {

    @POST("baseInfo/baseInfo")
    Observable<UserEntity> getUserInfo();

}
