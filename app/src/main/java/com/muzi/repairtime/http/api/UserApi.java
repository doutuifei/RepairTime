package com.muzi.repairtime.http.api;

import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.entity.UserEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public interface UserApi {

    /**
     * 获取用户信息
     *
     * @return
     */
    @POST("baseInfo/baseInfo")
    Observable<UserEntity> getUserInfo();

    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("user/changePass")
    Observable<BaseEntity> changePsd(@Field("pass") String oldPsd,
                                     @Field("newPass") String newPsd);

}
